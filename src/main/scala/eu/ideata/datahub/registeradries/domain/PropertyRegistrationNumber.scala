package eu.ideata.datahub.registeradries.domain
import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert

/**
  * Created by mbarak on 04/10/16.
  */
case class PropertyRegistrationNumber(databaseOperation: String, propertyRegId: Int, versionId: Int, createdReason: String, validFrom: Option[Date], validTo: Option[Date], effecitveDate: Option[Date],
                                      propertyRegistrationNumber: Option[Int], buildingPurposecodeListCode: String,
                                      buildingTypeItemCode: String, buildingName: Option[String], municipalityId: Option[Int], districtId: Option[Int])


class PropertyRegistrationNumbers(val con: Connection) extends GenericInsert[PropertyRegistrationNumber]{
  override val sqlString: String =
    """
      |INSERT INTO property_registration_number (database_operation, property_registration_id, version_id, created_reason,
      |valid_from, valid_to, effective_date, property_registration_number, building_purpose_list_code, building_type_item_code, building_name, municipality_id, district_id)
      |VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  override def f(item: PropertyRegistrationNumber, ps: PreparedStatement): Unit = {
    ps.setString(1,item.databaseOperation)
    ps.setInt(2, item.propertyRegId)
    ps.setInt(3, item.versionId)
    ps.setString(4, item.createdReason)
    ps.setDate(5, item.validFrom.orNull)
    ps.setDate(6, item.validTo.orNull)
    ps.setDate(7, item.effecitveDate.orNull)
    ps.setInt(8, item.propertyRegistrationNumber.getOrElse(null.asInstanceOf[Int]))
    ps.setString(9, item.buildingPurposecodeListCode)
    ps.setString(10, item.buildingTypeItemCode)
    ps.setString(11, item.buildingName.orNull)
    ps.setInt(12, item.municipalityId.getOrElse(null.asInstanceOf))
    ps.setInt(13, item.districtId.getOrElse(null.asInstanceOf[Int]))
  }
}