package eu.ideata.datahub.registeradries.scraper

import eu.ideata.datahub.registeradries.domain.{InitialLoad, ChangeLoad}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by mbarak on 30/09/16.
  */
class ContentExtractorTest extends WordSpec with Matchers with ScalaFutures {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(10, Seconds), interval = Span(100, Millis))

  val f = io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("register_adries.json")).getLines().mkString("")
  val extractor = new ContentExtractor


  "ContentExtractor" should {
    "return all the extractedUrl and ids" in {
      val result = extractor.content(f)


      result should not be empty
      result.foreach(d => d.url should include(d.id))
      val initial = result.collect{ case d: InitialLoad => d }
      val zmenova = result.collect{ case d: ChangeLoad => d}
      initial should have size 1
      zmenova should not be empty
    }
  }
}
