package eu.ideata.datahub.registeradries.extractor

import eu.ideata.datahub.registeradries.domain.StreetName

import scala.xml.Node

/**
  * Created by mbarak on 03/10/16.
  */

class StreetNameExtractor(val Base: String = "streetNameChange") extends BaseExtractor[StreetName] with BaseInformationExtractor {
  override def specific(n: Node)(base: BaseInformation): StreetName = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base
    val streetName = Option((n \ "StreetName").text).filter(_.nonEmpty)
    val municipalityIdentifier = Option((n \ "municipalityIdentifier").text).filter(_.nonEmpty).map(_.toInt)
    val districtIdentifier = Option((n \ "districtIdentifier").text).filter(_.nonEmpty).map(_.toInt)

    StreetName(databaseOperation, objectId, versionId, createdReason, validFrom, validTo, effectiveDate ,streetName, municipalityIdentifier, districtIdentifier)
  }
}

