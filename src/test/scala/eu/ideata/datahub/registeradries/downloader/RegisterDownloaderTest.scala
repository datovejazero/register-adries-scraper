package eu.ideata.datahub.registeradries.downloader

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by mbarak on 30/09/16.
  */
class RegisterDownloaderTest extends WordSpec with Matchers with ScalaFutures {

  val downloader = new ContentDownloader
  val url = "https://data.gov.sk/api/3/action/package_show?id=register-adries"

  "RegisterAdriesDownloader" should {
    "return donwloaded json" in {
      val result = io.Source.fromInputStream( downloader.download(url)).getLines().mkString("")
      result should not be empty

    }
  }
}
