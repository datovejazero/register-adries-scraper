package eu.ideata.datahub.registeradries.extractor
import eu.ideata.datahub.registeradries.domain.Municipality

import scala.xml.Node

/**
  * Created by mbarak on 03/10/16.
  */

class MunicipalityExtractor(val Base: String = "municipalityChange" ) extends BaseExtractor[Municipality] with BaseInformationExtractor {

  override def specific(n: Node)(base: BaseInformation): Municipality = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base

    val codeListCode = (n \ "Municipality" \ "Codelist" \ "CodelistCode").text
    val itemCode = Option((n \ "Municipality" \ "Codelist" \ "CodelistItem" \ "ItemCode").text).filter(_.nonEmpty)
    val itemName = (n \ "Municipality" \ "Codelist" \ "CodelistItem" \ "ItemName").text
    val countryIdentifier = Option((n \ "countyIdentifier").text).filter(_.nonEmpty).map(_.toInt)
    val status = (n \ "status").text

    Municipality(databaseOperation, objectId, versionId, createdReason, validFrom, validTo, effectiveDate, codeListCode, itemCode, itemName, countryIdentifier, status)
  }
}