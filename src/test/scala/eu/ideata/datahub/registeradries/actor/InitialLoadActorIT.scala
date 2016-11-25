package eu.ideata.datahub.registeradries.actor

import java.io.File
import java.sql.{Connection, DriverManager}

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import eu.ideata.datahub.registeradries.domain.InitialLoad
import eu.ideata.datahub.registeradries.downloader.ContentDownloader
import eu.ideata.datahub.registeradries.util.TestHelper
import org.scalatest.{BeforeAndAfter, Matchers, WordSpecLike}

import scala.concurrent.duration._

/**
  * Created by mbarak on 14/10/2016.
  */
class InitialLoadActorTestIT extends TestKit(ActorSystem("InitialTest")) with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfter {


  var actor: ActorRef = _
  var connection: Connection = _

  "InitialDavkaActor" should {
    "Extract all the data from the zip file" in {

      actor ! InitialLoad("", "should use cached")
      expectMsg(10 minutes, InitialDone)
    }
  }

  before {
    val c = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
    val conf = ConfigFactory.parseFile(new File(getClass.getClassLoader.getResource("application.conf").getFile))
    connection = DriverManager.getConnection(conf.getString("db.url"))
    val f = TestHelper.getMockZipFile
    actor = system.actorOf(InitialLoadActor.props(f, new ContentDownloader ,connection))
  }

  after {
    connection.close()
  }
}
