package eu.ideata.datahub.registeradries.domain

import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert

/**
  * Created by mbarak on 03/10/16.
  */
case class District(dataBaseOperation: String, districtId: Int, versionId: Int, createdReason: String, validFrom: Option[Date], validTo: Option[Date],
                    effectiveDate: Option[Date], codeListCode: String, itemCode: Option[String], itemName: String, municipalityId: Option[Int])

class Districts(val con: Connection) extends GenericInsert[District] {
  override val sqlString: String =
    """
      | INSERT INTO district (database_operation, district_id, version_id, created_reason, valid_from, valid_to, effective_date, code_list_code, item_list_code, street_name, municipality_id)
      | VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  override def f(item: District, ps: PreparedStatement): Unit = {
    ps.setString(1, item.dataBaseOperation)
    ps.setInt(2, item.districtId)
    ps.setInt(3, item.versionId)
    ps.setString(4, item.createdReason)
    ps.setDate(5, item.validTo.orNull)
    ps.setDate(6, item.validFrom.orNull)
    ps.setDate(7, item.effectiveDate.orNull)
    ps.setString(8, item.codeListCode)
    ps.setString(9, item.itemCode.orNull)
    ps.setString(10, item.itemName)
    ps.setInt(11, item.municipalityId.getOrElse(null.asInstanceOf[Int]))
  }
}