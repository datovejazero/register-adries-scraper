package eu.ideata.datahub.registeradries.domain

import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert
/**
  * Created by mbarak on 03/10/16.
  */
case class StreetName(dataBaseOperation: String, streetId: Int, versionId: Int ,createdReason: String, validFrom: Option[Date], validTo: Option[Date], effectiveDate: Option[Date], streetName: Option[String], municipalityId: Option[Int], districtId: Option[Int])


class StreetNames( val con: Connection) extends GenericInsert[StreetName] {
  override val sqlString: String =
    """
      |INSERT INTO street_name (database_operation, street_id, version_id, created_reason, valid_from, valid_to, effective_date, street_name, municipality_id, district_id)
      |VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  override def f(item: StreetName, ps: PreparedStatement): Unit = {
    ps.setString(1, item.dataBaseOperation)
    ps.setInt(2, item.streetId)
    ps.setInt(3, item.versionId)
    ps.setString(4, item.createdReason)
    ps.setDate(5, item.validFrom.orNull)
    ps.setDate(6, item.validTo.orNull)
    ps.setDate(7, item.effectiveDate.orNull)
    ps.setString(8, item.streetName.orNull)
    ps.setInt(9, item.municipalityId.getOrElse(null.asInstanceOf[Int]))
    ps.setInt(10, item.districtId.getOrElse(null.asInstanceOf[Int]))
  }
}