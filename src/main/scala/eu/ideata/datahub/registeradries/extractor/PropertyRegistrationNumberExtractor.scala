package eu.ideata.datahub.registeradries.extractor

import eu.ideata.datahub.registeradries.domain.PropertyRegistrationNumber

import scala.xml.Node

/**
  * Created by mbarak on 04/10/16.
  */

class PropertyRegistrationNumberExtractor(val Base: String = "propertyRegistrationNumberChange") extends BaseExtractor[PropertyRegistrationNumber] {

  override def specific(n: Node)(base: BaseInformation): PropertyRegistrationNumber = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base
    val buildingPropertyRegistrationNumber = Option((n \ "PropertyRegistrationNumber").text).filter(_.nonEmpty).map(_.toInt)
    val buildingPurposeCodeListCode = (n \ "Building" \ "BuildingPurpose" \ "Codelist" \ "CodelistCode").text
    val buildingTypeListItemCode  = (n \ "Building" \ "BuildingTypeCode" \ "Codelist" \ "ItemCode").text
    val buildingTypeItemName = Option((n \ "Building" \ "BuildingTypeCode" \ "CodelistItem" \ "ItemName").text).filter(_.nonEmpty)

    val municipalityIdentifier = Option((n \ "municipalityIdentifier").text).filter(_.nonEmpty).map(_.toInt)
    val districtIdentifier = Option((n \ "districtIdentifier").text).filter(_.nonEmpty).map(_.toInt)

    PropertyRegistrationNumber(databaseOperation, objectId, versionId, createdReason, validFrom, validTo, effectiveDate, buildingPropertyRegistrationNumber,
      buildingPurposeCodeListCode, buildingTypeListItemCode, buildingTypeItemName, municipalityIdentifier, districtIdentifier)
  }
}
