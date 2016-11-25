package eu.ideata.datahub.registeradries.extractor
import eu.ideata.datahub.registeradries.domain.BuildingUnit

import scala.xml.Node

/**
  * Created by mbarak on 04/10/16.
  */

class BuildingUnitExtractor(val Base: String = "buildingUnitChange") extends BaseExtractor[BuildingUnit] {

  override def specific(n: Node)(base: BaseInformation): BuildingUnit = {
    val BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate) = base
    val buildingUnit = (n \ "BuildingUnit").text
    val buildingNumberIdentifier = Option((n \ "buildingNumberIdentifier").text).filter(_.nonEmpty).map(_.toInt)

    BuildingUnit(databaseOperation, objectId, versionId, createdReason, validFrom, validTo, effectiveDate, buildingUnit, buildingNumberIdentifier)
  }
}
