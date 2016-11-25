package eu.ideata.datahub.registeradries.util

import java.io.{File, FileOutputStream, InputStream}

/**
  * Created by mbarak on 30/09/16.
  */
object ZipUtil {

  def flushZipFile(inputStream: InputStream, destination: File): File = {
    val outputStream = new FileOutputStream(destination)

    val bytes = new Array[Byte](1024)
    var read = inputStream.read(bytes)

    while (read != -1) {

      outputStream.write(bytes, 0, read)
      read = inputStream.read(bytes)
    }

    outputStream.flush()
    outputStream.close()

    destination
  }
}
