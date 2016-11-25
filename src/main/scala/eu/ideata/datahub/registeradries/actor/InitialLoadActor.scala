package eu.ideata.datahub.registeradries.actor

import java.io.{File, InputStream}
import java.sql.Connection

import akka.actor.{Actor, ActorLogging, Props}
import eu.ideata.datahub.registeradries.domain.{InitialLoad, LoadsRepository, ProcessedDavka}
import eu.ideata.datahub.registeradries.downloader.ContentDownloader
import eu.ideata.datahub.registeradries.scraper.IntialDavkaZipExtractor
import eu.ideata.datahub.registeradries.util.{ExtractionProcess, ZipUtil}
import org.joda.time.DateTime
import akka.pattern.pipe

import scala.concurrent.Future
import scala.xml.XML
import shapeless._
import record._

/**
  * Created by mbarak on 14/10/2016.
  */
class InitialLoadActor(tmpZip: File, downloader: ContentDownloader, val connection: Connection) extends Actor with ExtractionProcess with ActorLogging {

  import context.dispatcher

  val davky = new LoadsRepository(connection)

  def receive = {
    case InitialLoad(id, url) => {
      log.info(s"Processing inital $url ")
      processInitialDavka(id, url)
        .pipeTo(sender())
        .map { _ => log.info(s"Initial davka loaded $url") }
        .onFailure {
          case e: Exception => {
            self ! InitialLoad(id, url)
          }
        }
    }
  }

  def processInitialDavka(id: String, url: String): Future[DatahubMessage] = Future {
    import ExtractionProcess._

    val zipFile = if (!tmpZip.exists()) {
      log.info(s"Donwloading Initial Zip from $url")
      val is = downloader.download(url)
      ZipUtil.flushZipFile(is, tmpZip)
    } else {
      log.info(s"File already exists ${tmpZip.getPath}")
      tmpZip
    }

    val davky: Iterator[(String, InputStream)] = new IntialDavkaZipExtractor(zipFile).items.iterator.filterNot { case (url, _) => url.contains("00.xml") }

    davky.flatMap { case (name, is) => {
      val xml = XML.load(is)

      (xml \ "type").text match {
        case "REGION" => {
          log.info("REGIONS")
          val (g, i) = records(Region)
          process(xml, g, i)
        }
        case "COUNTY" => {
          log.info("COUNTRY")
          val (g, i) = records(Country)
          process(xml, g, i)
        }
        case "MUNICIPALITY" => {
          log.info("MUNICIPALITY")
          val (g, i) = records(Municipality)
          process(xml, g, i)
        }
        case "DISTRICT" => {
          log.info("DISTRICT")
          val (g, i) = records(District)
          process(xml, g, i)
        }
        case "STREET_NAME" => {
          log.info("STREET_NAME")
          val (g, i) = records(StreetName)
          process(xml, g, i)
        }
        case "PROPERTY_REGISTRATION_NUMBER" => {
          log.info("PROPERTY_REGISTRATION_NUMBER")
          val (g, i) = records(PropertyRegistrationNumber)
          process(xml, g, i)
        }
        case "BUILDING_NUMBER" => {
          log.info("BUILDING_NUMBER")
          val (g, i) = records(BuildingNumber)
          process(xml, g, i)
        }
        case "BUILDING_UNIT" => {
          log.info("BUILDING_UNIT")
          val (g, i) = records(BuildingUnit)
          process(xml, g, i)
          }
        }
      }
    }.sum
  }.map(i => {
      val d = new ProcessedDavka(id, url, DateTime.now())
      davky.groupedInsert(Iterable(d))
      InitialDone
    }
  )
}

object InitialLoadActor {
  def props(tmpZip: File, downloader: ContentDownloader, connection: Connection) = Props(new InitialLoadActor(tmpZip, downloader, connection))
}
