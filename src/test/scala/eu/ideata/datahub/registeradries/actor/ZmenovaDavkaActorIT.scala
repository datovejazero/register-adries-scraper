package eu.ideata.datahub.registeradries.actor

import java.io.File
import java.sql.{Connection, DriverManager}

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import eu.ideata.datahub.registeradries.domain.ChangeLoad
import eu.ideata.datahub.registeradries.downloader.ContentDownloader
import org.scalatest.{BeforeAndAfter, Matchers, WordSpecLike}

import scala.concurrent.duration._
/**
  * Created by mbarak on 14/10/2016.
  */
class ZmenovaDavkaActorIT extends TestKit(ActorSystem("ChangeLoadActorTest")) with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfter {


  var actor: ActorRef = _
  var connection: Connection = _

  "InitialDavkaActor" should {
    "Extract all the data from the zip file" in {

      actor ! ChangeLoad("1535cde3-6046-48ab-9111-07189ebdcc38", "https://data.gov.sk/dataset/de3dd18f-9124-4acb-ae00-705555332256/resource/1535cde3-6046-48ab-9111-07189ebdcc38/download/zmenovadavka5068396.xml")
      expectMsg(5 minutes, ChangeLoadDone)
    }
  }

  before {
    val c = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
    val conf = ConfigFactory.parseFile(new File(getClass.getClassLoader.getResource("application.conf").getFile))
    connection = DriverManager.getConnection(conf.getString("db.url"))
    actor = system.actorOf(ChangeLoadActor.props(new ContentDownloader ,connection))
  }

  after {
    connection.close()
  }
}
