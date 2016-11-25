package eu.ideata.datahub.registeradries.util

import java.io.File

/**
  * Created by mbarak on 14/10/2016.
  */
object TestHelper {

  def getMockZipFile = new File(getClass.getClassLoader.getResource("davky/davky.zip").getFile)

}
