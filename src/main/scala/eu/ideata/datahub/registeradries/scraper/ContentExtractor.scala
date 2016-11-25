package eu.ideata.datahub.registeradries.scraper
import eu.ideata.datahub.registeradries.domain.{ChangeLoad, Davka, InitialLoad}
import eu.ideata.datahub.registeradries.util.Constants
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

class ContentExtractor {

  val ActualDomainName = "https://data.gov.sk"

  def liftedContent(s: String)(implicit ec: ExecutionContext): Future[Iterable[Davka]] = Future { content(s) }

  def content(s: String): Iterable[Davka] = {
    val json = Json.parse(s)

    val ids = (json \ "result" \ "resources" \\ "id").toList.map(_.toString.replace("\"", ""))
    val urls = (json \ "result" \ "resources" \\ "url").toList.map(_.toString.replace("\"", ""))

    ids.zip(urls).flatMap{ case (id, url) => {
      val normalisedUrl = url.replaceFirst(Constants.DomainNameRegex, ActualDomainName)

      if (url.contains("davkainit")) {
        Some(InitialLoad(id, normalisedUrl))
      } else if (url.contains("zmenovadavka")) {
        Some(new ChangeLoad(id, normalisedUrl))
      } else None
    }}
  }
}
