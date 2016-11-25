package eu.ideata.datahub.registeradries.domain

import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert

/**
  * Created by mbarak on 03/10/16.
  */
case class Country(databaseOperation: String, objectId: Int, versionId: Int, createdReason: String, validFrom: Option[Date], validTo: Option[Date], effectiveDate: Option[Date], codeListCode: String, itemCode: Option[String], itemName: String, regionId: Option[Int])

class Countries(val con: Connection) extends GenericInsert[Country]{
  override val sqlString: String =
    """
      | INSERT INTO country (database_operation, object_id, version_id, created_reason, valid_from, valid_to, effective_date, code_list_code, item_list_code, item_name, region_id)
      | VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  override def f(item: Country, ps: PreparedStatement): Unit = {
    ps.setString(1, item.databaseOperation)
    ps.setInt(2, item.objectId)
    ps.setInt(3, item.versionId)
    ps.setString(4, item.createdReason)
    ps.setDate(5, item.validFrom.orNull)
    ps.setDate(6, item.validTo.orNull)
    ps.setDate(7, item.effectiveDate.orNull)
    ps.setString(8, item.codeListCode)
    ps.setString(9, item.itemCode.orNull)
    ps.setString(10, item.itemName)
    ps.setInt(11, item.regionId.getOrElse(null.asInstanceOf[Int]))
  }
}