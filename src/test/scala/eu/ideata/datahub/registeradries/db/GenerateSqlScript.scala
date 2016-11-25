package eu.ideata.datahub.registeradries.db

import org.scalatest.{Matchers, WordSpec}

/**
  * Created by mbarak on 17/10/2016.
  */
class GenerateSqlScriptTest extends WordSpec with Matchers {

  "Sql script" should {
    "read the file " in {
      val content = ViewUtils.flow()
      content should have size (5)
    }
  }
}
