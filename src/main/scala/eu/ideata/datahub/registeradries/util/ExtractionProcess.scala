package eu.ideata.datahub.registeradries.util

import java.sql.Connection

import eu.ideata.datahub.registeradries.db.GenericInsert
import eu.ideata.datahub.registeradries.domain.{BuildingNumbers, BuildingUnits, PropertyRegistrationNumbers, _}
import eu.ideata.datahub.registeradries.extractor.{BuildingNumberExtractor, BuildingUnitExtractor, PropertyRegistrationNumberExtractor, StreetNameExtractor, _}
import scala.xml.Node


/**
  * Created by mbarak on 14/10/2016.
  */

trait ExtractionProcess {
  val connection: Connection

  import shapeless._
  import syntax.singleton._
  import ExtractionProcess._


  private lazy val regions = (new RegionExtractor, new Regions(connection))
  private lazy val countries = (new CountryExtractor,  new Countries(connection))
  private lazy val municipalities = (new MunicipalityExtractor, new Municipalities(connection))
  private lazy val districts = (new DistrictExtractor, new Districts(connection))
  private lazy val streetNames =  (new StreetNameExtractor, new StreetNames(connection))
  private lazy val propRegNunbers = ( new PropertyRegistrationNumberExtractor, new PropertyRegistrationNumbers(connection))
  private lazy val buildingNumbers = (new BuildingNumberExtractor,  new BuildingNumbers(connection))
  private lazy val buildingUnits = (new BuildingUnitExtractor, new BuildingUnits(connection))


  val records = (
    (Region ->> regions) ::
      (Country ->> countries) ::
      (Municipality ->> municipalities) ::
      (District ->> districts) ::
      (StreetName ->> streetNames) ::
      (PropertyRegistrationNumber ->> propRegNunbers) ::
      (BuildingNumber ->> buildingNumbers) ::
      (BuildingUnit ->> buildingUnits) ::
      HNil
    )

  def process[T <: Product](xml: Node, baseExtractor: BaseExtractor[T], gn: GenericInsert[T]): Option[Int] = {
    val items = baseExtractor.extract(xml)
    if(items.nonEmpty) {
      gn.groupedInsert(items)
    } else {
      None
    }
  }
}

object ExtractionProcess {
  val Region = "REGION"
  val Country = "COUNTY"
  val Municipality = "MUNICIPALITY"
  val District = "DISTRICT"
  val StreetName = "STREET_NAME"
  val PropertyRegistrationNumber = "PROPERTY_REGISTRATION_NUMBER"
  val BuildingNumber = "BUILDING_NUMBER"
  val BuildingUnit = "BUILDING_UNIT"
}
