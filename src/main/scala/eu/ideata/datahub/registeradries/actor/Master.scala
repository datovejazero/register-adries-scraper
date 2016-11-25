package eu.ideata.datahub.registeradries.actor

import java.io.File
import java.sql.Connection

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.routing.RoundRobinPool
import com.typesafe.config.Config
import eu.ideata.datahub.registeradries.domain.{ChangeLoad, Davka, InitialLoad, LoadsRepository}
import eu.ideata.datahub.registeradries.downloader.ContentDownloader
import eu.ideata.datahub.registeradries.scraper.ContentExtractor

import scala.io.Source

/**
  * Created by mbarak on 14/10/2016.
  */
class Master(conf: Config, connection: Connection) extends Actor with ActorLogging {

  import context.dispatcher
  var initialDone = false
  var loadsToConsume = -1
  var changeLoad: Iterable[Davka] = List.empty[Davka]

  val f = new File(conf.getString("files.tmp-path") + File.separator + "tmp.zip")

  val loadsRepository = new LoadsRepository(connection)
  val downloader = new ContentDownloader

  lazy val initial: ActorRef = context.system.actorOf(InitialLoadActor.props(f, downloader, connection))
  lazy val zmenova: ActorRef = context.system.actorOf(RoundRobinPool(3).props(ChangeLoadActor.props(downloader, connection)))

  def receive = {
    case Init(url) =>
      log.info(s"INIT $url")
      val is = downloader.download(url)
      val content = Source.fromInputStream(is).getLines().mkString("")
      val contentExtractor = new ContentExtractor
      val davkyToProcess = contentExtractor
        .content(content)
        .filter(d => !loadsRepository.isProcessed(d.url))

      davkyToProcess.foreach(d => log.info(s"URL to process: ${d.url}"))

      loadsToConsume = davkyToProcess.size

      log.info(s"Loads to process $loadsToConsume")
      changeLoad = davkyToProcess.filter(_.isInstanceOf[ChangeLoad])
      if(changeLoad.size == 0){
        log.info("Nothing to load")
        context.system.terminate()
      }
      val initial = davkyToProcess.find(_.isInstanceOf[InitialLoad])
      initial.map(d => self ! d).getOrElse {
        log.info("Initial already loaded staring change!")
        initialDone = true
        changeLoad.foreach(d => self ! d)
      }

    case d: InitialLoad => {
      log.info("Launching initial load")
      initial ! d
    }

    case InitialDone => {
      log.info("Initial load done")
      initialDone = true
      decrementDavkyToConsume
      changeLoad.foreach(d => self ! d)
    }

    case d: ChangeLoad if initialDone => {
      log.info("Launching change load")
      zmenova ! d
    }

    case ChangeLoadDone => {
      log.info("Change load done")
      decrementDavkyToConsume
    }

    case d => {
      decrementDavkyToConsume
      log.info(s"Skipping $d")
    }
  }

  def decrementDavkyToConsume = {
    loadsToConsume -= 1
    log.info(s"Loads to consume $loadsToConsume")
    if(loadsToConsume == 0){
      context.system.terminate()
    }
  }
}

object Master {
  def props(conf: Config, con: Connection) = Props(new Master(conf, con))
}
