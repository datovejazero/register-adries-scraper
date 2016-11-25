package eu.ideata.datahub.registeradries.scraper

import java.io.{File, InputStream}
import java.util.zip.ZipFile

import scala.collection.JavaConverters._



/**
  * Created by mbarak on 30/09/16.
  */
class IntialDavkaZipExtractor(file: File) {

  val items: Map[String, InputStream] = {

    val zipFile = new ZipFile(file)
    val entries = zipFile.entries.asScala

    entries.map(e => e.getName -> zipFile.getInputStream(e)).toMap
  }
}



