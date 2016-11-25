package eu.ideata.datahub.registeradries.db

import java.io.File
import java.sql.{Connection, DriverManager}

import com.typesafe.config.ConfigFactory
import eu.ideata.datahub.registeradries.domain._
import eu.ideata.datahub.registeradries.extractor._
import org.joda.time.DateTime
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

import scala.xml.XML


/**
  * Created by mbarak on 11/10/16.
  */
class DBInsertionIT extends WordSpec with Matchers with ScalaFutures with BeforeAndAfter {

  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(120, Seconds), interval = Span(100, Millis))

  var con: Connection = _

  "Loaders" should {
    "Load all the regions" in {
      loadTest[Region]("davky/DavkaInit_20151009-01.xml", new RegionExtractor(), new Regions(con)) should be (19)
    }
    "Load all the countries" in {
      loadTest[Country]("davky/DavkaInit_20151009-02.xml", new CountryExtractor(), new Countries(con)) should be (82)
    }
    "Load all the municipalities" in {
      loadTest[Municipality]("davky/DavkaInit_20151009-03.xml", new MunicipalityExtractor(), new Municipalities(con)) should be (3011)
    }
    "Load all the districts" in {
      loadTest[District]("davky/DavkaInit_20151009-04.xml", new DistrictExtractor(), new Districts(con)) should be (2616)
    }
    "Load all the street names" in {
      loadTest[StreetName]("davky/DavkaInit_20151009-05.xml", new StreetNameExtractor(), new StreetNames(con)) should be (37317)
    }
    "Load all the propety registration numbers" in {
      loadTest[PropertyRegistrationNumber]("davky/Davka6.xml", new PropertyRegistrationNumberExtractor(), new PropertyRegistrationNumbers(con)) should be (40)
    }
    "Load all the building numbers" in {
      loadTest[BuildingNumber]("davky/Davka7.xml", new BuildingNumberExtractor(), new BuildingNumbers(con)) should be (118)
    }
    "Load all the building units" in {
      loadTest[BuildingUnit]("davky/DavkaInit_20151009-08.xml", new BuildingUnitExtractor(), new BuildingUnits(con)) should be (8071)
    }
    "Insert processed davky" in {
      val davky = Iterable(new ProcessedDavka("", "", DateTime.now()))
      val d = new LoadsRepository(con)
      val j = d.groupedInsert(davky)
      j should not be empty

    }
  }

  before {
      val conf = ConfigFactory.parseFile(new File(getClass.getClassLoader.getResource("application.conf").getFile))
      val c = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
      con = DriverManager.getConnection(conf.getString("db.url"))
  }

  after {
    con.close()
  }


  def loadTest[T <: Product](path: String, baseExtractor: BaseExtractor[T], gn: GenericInsert[T]): Int = {
    val items = baseExtractor.extract(XML.load(getClass.getClassLoader.getResourceAsStream(path)))
    gn.groupedInsert(items).get
  }
}
