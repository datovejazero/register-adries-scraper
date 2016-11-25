package eu.ideata.datahub.registeradries.domain

import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert

/**
  * Created by mbarak on 04/10/16.
  */
case class BuildingUnit(databaseOperation: String, buildingUnitId: Int, versionId: Int, createdReason: String, validFrom: Option[Date], validTo: Option[Date], effectiveDate: Option[Date], buildingUnit: String, propertyRegistrationId: Option[Int])

class BuildingUnits(val con: Connection) extends GenericInsert[BuildingUnit] {
  override val sqlString: String =
    """
      |insert into building_unit (database_operation, building_unit_id, version_id, created_reason, valid_from, valid_to, effective_date,  bulding_unit, property_reg_number_id)
      |VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  override def f(item: BuildingUnit, ps: PreparedStatement): Unit = {

    ps.setString(1, item.databaseOperation)
    ps.setInt(2, item.buildingUnitId)
    ps.setInt(3, item.versionId)
    ps.setString(4, item.createdReason)
    ps.setDate(5, item.validFrom.getOrElse(null))
    ps.setDate(6, item.validTo.getOrElse(null))
    ps.setDate(7, item.effectiveDate.getOrElse(null))
    ps.setString(8, item.buildingUnit)
    ps.setInt(9, item.propertyRegistrationId.getOrElse(null.asInstanceOf[Int]))
  }
}