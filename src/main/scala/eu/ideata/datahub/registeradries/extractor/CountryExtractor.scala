package eu.ideata.datahub.registeradries.extractor

import eu.ideata.datahub.registeradries.domain.Country

import scala.xml.Node

/**
  * Created by mbarak on 03/10/16.
  */
class CountryExtractor(val Base: String = "countyChange") extends BaseExtractor[Country] {

  override def specific(n: Node)(base: BaseInformation): Country = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base
    val codeListCode = (n \ "County" \ "Codelist" \ "CodelistCode").text
    val itemCode = Option((n \ "County" \ "Codelist" \ "CodelistItem" \ "ItemCode").text).filter(_.nonEmpty)
    val itemName = (n \ "County" \ "Codelist" \ "CodelistItem" \ "ItemName").text
    val regionId = Option((n \ "regionIdentifier").text).filter(_.nonEmpty).map(_.toInt)
    Country(databaseOperation, objectId, versionId, createdReason, validFrom, validTo, effectiveDate, codeListCode, itemCode, itemName, regionId)
  }
}
