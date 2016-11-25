package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.BuildingNumberExtractor
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{Matchers, WordSpec}

import scala.xml.XML

/**
  * Created by mbarak on 04/10/16.
  */
class BuildingNumberExtractorTest extends WordSpec with Matchers {

  val file = getClass.getClassLoader.getResourceAsStream("davky/Davka7.xml")
  val xml = XML.load(file)

  val extractor = new BuildingNumberExtractor

  "InitialBuildingNumberExtractor" should {
    "extract all the building numbers" in {

      val result = extractor.extract(xml)

      result should not be empty
      result should have size(118)

    }
  }
}
