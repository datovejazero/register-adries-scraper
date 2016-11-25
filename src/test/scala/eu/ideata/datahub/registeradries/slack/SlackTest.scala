package eu.ideata.datahub.registeradries.slack



import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.entity.{ContentType, StringEntity}
import org.apache.http.impl.client.HttpClientBuilder
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by mbarak on 07/10/16.
  */
class SlackTest extends WordSpec with Matchers {


  "Slack session" should {
     "post to register adries notif" ignore {

      val url = "https://data.gov.sk/api/3/action/package_show?id=register-adries-zmenove-davky"
      val post = new HttpPost("https://hooks.slack.com/services/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")
      val e = new StringEntity(s"""{ "text": "This is just an integration test message"}""", ContentType.APPLICATION_JSON)
      post.setEntity(e)

      val r: CloseableHttpResponse = HttpClientBuilder.create().build().execute(post)

    }
  }

}
