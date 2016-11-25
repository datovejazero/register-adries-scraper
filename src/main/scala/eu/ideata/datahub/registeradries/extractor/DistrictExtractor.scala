package eu.ideata.datahub.registeradries.extractor

import eu.ideata.datahub.registeradries.domain.District

import scala.xml.Node

/**
  * Created by mbarak on 03/10/16.
  */

class DistrictExtractor(val Base: String =  "districtChange") extends BaseExtractor[District] {

  override def specific(n: Node)(base: BaseInformation): District = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base
    val codeListCode = (n \ "District" \ "Codelist" \ "CodelistCode").text
    val itemCode = Option((n \ "District" \ "Codelist" \ "CodelistItem" \ "ItemCode").text).filter(_.isEmpty)
    val itemName = (n \ "District" \ "Codelist" \ "CodelistItem" \ "ItemName").text
    val municipalityId =  Option((n \ "municipalityIdentifier").text).filter(_.nonEmpty).map(_.toInt)

    District(databaseOperation, objectId.toInt, versionId.toInt, createdReason, validFrom, validTo, effectiveDate, codeListCode, itemCode, itemName, municipalityId)
  }
}
