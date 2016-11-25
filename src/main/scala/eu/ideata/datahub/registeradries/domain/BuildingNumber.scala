package eu.ideata.datahub.registeradries.domain

import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert

/**
  * Created by mbarak on 04/10/16.
  */
case class BuildingNumber(databaseOperation: String, buildingNumberId: Int, versionId: Int, createdReason: String,
                           validFrom: Option[Date], validTo: Option[Date], effectiveDate: Option[Date], buildingIndex: String, propertyRegistrationNumberId: Option[Int], streetNameId: Option[Int])




class BuildingNumbers(val con: Connection) extends GenericInsert[BuildingNumber] {
  override val sqlString: String =
    """
      |INSERT INTO building_number (database_operation, building_number_id, version_id, created_reason, valid_from, valid_to, effective_date, building_index, property_registration_id, street_name_id)
      |VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  override def f(item: BuildingNumber, ps: PreparedStatement): Unit = {
    ps.setString(1, item.databaseOperation)
    ps.setInt(2, item.buildingNumberId)
    ps.setInt(3, item.versionId)
    ps.setString(4, item.createdReason)
    ps.setDate(5, item.validFrom.getOrElse(null))
    ps.setDate(6, item.validTo.getOrElse(null))
    ps.setDate(7, item.effectiveDate.getOrElse(null))
    ps.setInt(8, item.buildingNumberId)
    ps.setInt(9, item.propertyRegistrationNumberId.getOrElse(null.asInstanceOf[Int]))
    ps.setInt(10, item.streetNameId.getOrElse(null.asInstanceOf[Int]))
  }
}
