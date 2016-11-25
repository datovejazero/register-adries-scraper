package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.StreetNameExtractor
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{Matchers, WordSpec}

import scala.xml.XML

/**
  * Created by mbarak on 04/10/16.
  */
class StreetNameExtractorTest extends WordSpec with Matchers {

  val file = getClass.getClassLoader.getResourceAsStream("davky/DavkaInit_20151009-05.xml")
  val xml = XML.load(file)

  val extractor = new StreetNameExtractor

  "InitialStreetNameExtractor" should {
    "extract all the Street Names " in {

      val result = extractor.extract(xml)

      result should not be empty
      result should have size(37317)
    }
  }
}
