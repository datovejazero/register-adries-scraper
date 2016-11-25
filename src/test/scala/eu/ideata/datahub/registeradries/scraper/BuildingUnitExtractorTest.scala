package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.BuildingUnitExtractor
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{Matchers, WordSpec}

import scala.xml.XML

/**
  * Created by mbarak on 04/10/16.
  */
class BuildingUnitExtractorTest extends WordSpec with Matchers {

  val file = getClass.getClassLoader.getResourceAsStream("davky/DavkaInit_20151009-08.xml")
  val xml = XML.load(file)

  val extractor = new BuildingUnitExtractor

  "InitialBuildingUnitExtractor" should {
    "extract all the countries form the initial xml" in {

      val result = extractor.extract(xml)
      result should not be empty
      result should have size(8071)
    }
  }
}
