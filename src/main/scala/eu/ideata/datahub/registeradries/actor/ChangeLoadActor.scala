package eu.ideata.datahub.registeradries.actor

import java.sql.Connection

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.pipe
import eu.ideata.datahub.registeradries.domain._
import eu.ideata.datahub.registeradries.downloader.ContentDownloader
import eu.ideata.datahub.registeradries.util.ExtractionProcess
import org.joda.time.DateTime

import scala.xml.XML
import shapeless.record._

import scala.concurrent.Future
/**
  * Created by mbarak on 14/10/2016.
  */
class ChangeLoadActor(downloader: ContentDownloader, val connection: Connection) extends Actor with ExtractionProcess with ActorLogging {

  import eu.ideata.datahub.registeradries.util.ExtractionProcess._
  val davky = new LoadsRepository(connection)
  import context.dispatcher

  def receive = {
    case ChangeLoad(id, url) => {
      log.info(s"Processing zmenova davka $url")
      proccessInitialDavka(id, url)
        .pipeTo(sender())
        .map(_ => log.info(s"Zmenova davka loaded $url"))
        .onFailure{
          case e: Exception => {
            self ! ChangeLoad(id, url)
          }
        }
      }
    }

  def proccessInitialDavka(id: String, url: String): Future[DatahubMessage] = Future {
    val xml = XML.load(downloader.download(url))

    val load = List(
      {
        val (g,i) = records(Region)
        process(xml, g, i)
      },
      {
        val (g,i) = records(Country)
        process(xml, g, i)
      },
      {
        val (g, i) = records(Municipality)
        process(xml, g, i)
      },
      {
        val (g,i) = records(District)
        process(xml, g, i)
      },
      {
        val (g,i) = records(StreetName)
        process(xml, g, i)
      },
      {
        val (g, i) = records(PropertyRegistrationNumber)
        process(xml, g, i)
      },
      {
        val (g, i) = records(BuildingNumber)
        process(xml, g, i)
      },
      {
        val (g, i) = records(BuildingUnit)
        process(xml, g, i)
      }
    )

    load.flatten.sum
  }
    .map(_ => {
      val d = new ProcessedDavka(id, url, DateTime.now())
      davky.groupedInsert(Iterable(d))
      ChangeLoadDone
    })
}

object ChangeLoadActor {
  def props(downloader: ContentDownloader, connection: Connection) = Props(new ChangeLoadActor(downloader, connection))
}
