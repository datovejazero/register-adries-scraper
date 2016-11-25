package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.PropertyRegistrationNumberExtractor
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import scala.xml.XML

/**
  * Created by mbarak on 04/10/16.
  */
class PropertyRegistratioNumberExtractorTest extends WordSpec with Matchers {

  val file = getClass.getClassLoader.getResourceAsStream("davky/Davka6.xml")
  val xml = XML.load(file)

  val extractor = new PropertyRegistrationNumberExtractor

  "InitialPropertyRegistrationNumbeExtractor" should {
    "extract all the registration numbers" in {
      val result = extractor.extract(xml)

      result should not be empty
      result should have size(40)

    }
  }
}
