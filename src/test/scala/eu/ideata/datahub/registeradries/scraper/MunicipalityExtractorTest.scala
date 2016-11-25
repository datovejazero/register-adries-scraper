package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.MunicipalityExtractor
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{Matchers, WordSpec}

import scala.xml.XML

/**
  * Created by mbarak on 03/10/16.
  */
class MunicipalityExtractorTest extends WordSpec with Matchers {

  val file = getClass.getClassLoader.getResourceAsStream("davky/DavkaInit_20151009-03.xml")
  val xml = XML.load(file)

  val extractor = new MunicipalityExtractor

  "InitialMunicipalityExtractor" should {
    "get all municipalities from xml" in {

      val result = extractor.extract(xml)

      result should not be empty
      result should have size(3011)

    }
  }
}
