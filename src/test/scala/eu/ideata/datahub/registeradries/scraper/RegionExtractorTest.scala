package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.RegionExtractor
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import scala.xml.XML

/**
  * Created by mbarak on 03/10/16.
  */
class RegionExtractorTest extends WordSpec with Matchers {


  val file = getClass.getClassLoader.getResourceAsStream("davky/DavkaInit_20151009-01.xml")
  val xml = XML.load(file)


  val extractor = new RegionExtractor


  "InitialRegionExtractor" should {
    "extract all the regions from the initial xml" in {

      val result = extractor.extract(xml)

      result should not be empty
      result should have size(19)
      result.foreach(println)
    }
  }

}
