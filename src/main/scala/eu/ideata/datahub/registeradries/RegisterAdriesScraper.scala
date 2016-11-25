package eu.ideata.datahub.registeradries

import java.io.File
import java.sql.DriverManager

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import eu.ideata.datahub.registeradries.actor.{Init, Master}
import eu.ideata.datahub.registeradries.db.ViewUtils
import eu.ideata.datahub.registeradries.util.RegisterAdriesScraperConfigParser

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Try

/**
  * Created by mbarak on 14/10/2016.
  */
object RegisterAdriesScraper {
  implicit val system = ActorSystem("datahub-Scraper")

  def main(args: Array[String]) {
    val appConfig = RegisterAdriesScraperConfigParser(args)

    val conf = ConfigFactory.parseFile(new File(appConfig.configPath))

    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")

    val connection = DriverManager.getConnection(conf.getString("db.url"))

    val master = system.actorOf(Master.props(conf, connection))
    master ! Init(conf.getString("data-gov.url"))

    Await.ready(system.whenTerminated, 12 hours)

    println("Strating DB migration")

    ViewUtils.flow().map(s =>{
      println(s)
      Try(connection.prepareStatement(s).execute())
        .recover {
          case e: Exception => println(s"A wild exception appeared: $e")
        }
      }
    )
  }
}
