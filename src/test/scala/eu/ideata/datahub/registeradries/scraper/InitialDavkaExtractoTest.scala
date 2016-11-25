package eu.ideata.datahub.registeradries.scraper

import java.io.File

import org.scalatest.{Matchers, WordSpec}

/**
  * Created by mbarak on 30/09/16.
  */
class InitialDavkaExtractoTest extends WordSpec with Matchers {


  val file = new File(getClass.getClassLoader.getResource("davky/davky.zip").getPath)

  "InitialDavkaZipExtractor" should {
    "return pairs of item name and inputstream" in {

      val files = new IntialDavkaZipExtractor(file).items

      files should not be empty

      files.foreach(println)
    }
  }
}
