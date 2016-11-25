package eu.ideata.datahub.registeradries.util

/**
  * Created by mbarak on 07/10/16.
  */
case class RegisterAdriesScraperConfig(configPath: String = "/etc/var/register_adries_scraper/application.conf")

object RegisterAdriesScraperConfigParser {
  val parser = new scopt.OptionParser[RegisterAdriesScraperConfig]("scopt"){
    head("scopt", "3.x")

    opt[String]("config").required().valueName("<file>").action{ case (path,config) => config.copy(configPath = path)}
  }

  def apply(args: Array[String]): RegisterAdriesScraperConfig = parser.parse(args, RegisterAdriesScraperConfig()).get
}