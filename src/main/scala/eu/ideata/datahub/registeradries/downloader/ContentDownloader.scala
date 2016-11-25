package eu.ideata.datahub.registeradries.downloader

import java.io.InputStream

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by mbarak on 30/09/16.
  */
class ContentDownloader {

  val client = HttpClientBuilder.create().build()

  def liftedDownload(url: String)(implicit ec: ExecutionContext): Future[InputStream] =  Future {
    download(url)
  }

  def download(url: String) = client.execute(new HttpGet(url)).getEntity.getContent

  def close() = client.close()
}
