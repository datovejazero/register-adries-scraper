package eu.ideata.datahub.registeradries.ziptest

import java.io.File

import eu.ideata.datahub.registeradries.downloader.ContentDownloader
import eu.ideata.datahub.registeradries.util.ZipUtil
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

import scala.concurrent.Future

/**
  * Created by mbarak on 30/09/16.
  */


class ZipUtilTestIT extends WordSpec with Matchers with ScalaFutures with BeforeAndAfter {
  import scala.concurrent.ExecutionContext.Implicits.global


  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(360, Seconds), interval = Span(100, Millis))

  val url = "https://data.gov.sk/dataset/de3dd18f-9124-4acb-ae00-705555332256/resource/a904effb-7c5c-43ce-8c59-270932e6a19a/download/bbpradavkainit20151009prod.zip"

  val donwloader = new ContentDownloader

  val tmpFile = new File("file.zip")

  "InitialDavkaScraper" should {
    "scrape from an inputStream" in {
      val futureStream = Future { donwloader.download(url) }

      whenReady(futureStream.map(s => ZipUtil.flushZipFile(s, tmpFile))){
        _ should exist
      }
    }
  }

  after {
    donwloader.close()
    tmpFile.delete()
  }

}
