package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.extractor.{PropertyRegistrationNumberExtractor, RegionExtractor}
import org.scalatest.{Matchers, WordSpec}

import scala.xml.XML

/**
  * Created by mbarak on 06/10/16.
  */
class ChangeLoadExtrationTest extends WordSpec with Matchers {

  "ZmenovaDavkaExtraction" should {
    "extract all the zmenova davky" in {
      val xml = XML.load(getClass.getClassLoader.getResourceAsStream("davky/zmenovadavka5156536.xml"))


      val regChange = new RegionExtractor()
      val propRegChange = new PropertyRegistrationNumberExtractor()

      val regs = regChange.extract(xml)
      val props = propRegChange.extract(xml)

      regs shouldBe empty
      props should not be empty

      props.foreach(println)
    }
  }

}
