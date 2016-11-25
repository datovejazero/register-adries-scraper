package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.DistrictExtractor
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{Matchers, WordSpec}

import scala.xml.XML

/**
  * Created by mbarak on 03/10/16.
  */
class DistrictExtractorTest extends WordSpec with Matchers {

  val file = getClass.getClassLoader.getResourceAsStream("davky/DavkaInit_20151009-04.xml")
  val xml = XML.load(file)

  val extractor = new DistrictExtractor


  "InitialDistrictExtractor" should {
    "extract all the districst from the initial xml" in {

      val result = extractor.extract(xml)

      result should not be empty
      result should have size(2616)

    }
  }
}
