package eu.ideata.datahub.registeradries.extractor

import eu.ideata.datahub.registeradries.domain.BuildingNumber

import scala.xml.Node

/**
  * Created by mbarak on 04/10/16.
  */

class BuildingNumberExtractor(val Base: String = "buildingNumberChange" ) extends BaseExtractor[BuildingNumber] {

  override def specific(n: Node)(base: BaseInformation): BuildingNumber = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base
    val buildingIndex = (n \ "BuildingIndex").text
    val propertyRegistrationNumberId = Option((n \ "propertyRegistrationNumberIdentifier").text).filter(_.nonEmpty).map(_.toInt)
    val streetNameId = Option((n \ "streetNameIdentifier").text).filter(_.nonEmpty).map(_.toInt)

    BuildingNumber(databaseOperation, objectId, versionId, createdReason, validFrom, validTo, effectiveDate, buildingIndex, propertyRegistrationNumberId, streetNameId)
  }
}