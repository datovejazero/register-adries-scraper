package eu.ideata.datahub.registeradries.extractor


import eu.ideata.datahub.registeradries.domain.Region

import scala.xml.Node


class RegionExtractor(val Base: String = "regionChange") extends BaseExtractor[Region] with BaseInformationExtractor {
  override def specific(n: Node)(base: BaseInformation): Region = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base
    val codeListCode = (n \ "Region" \ "Codelist" \ "CodelistCode").text
    val itemCode = Option((n \ "Region" \ "Codelist" \ "CodelistItem" \ "ItemCode").text).filter(_.nonEmpty)
    val itemName = (n \ "Region" \ "Codelist" \ "CodelistItem" \ "ItemName").text
    val regionIdentifier = Option((n \ "regionIdentifier").text).filter(_.nonEmpty).map(_.toInt)

    Region(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate, codeListCode, itemCode, itemName, regionIdentifier)
  }
}




