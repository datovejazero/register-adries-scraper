package eu.ideata.datahub.registeradries.domain

import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert
/**
  * Created by mbarak on 30/09/16.
  */


case class Region(dataBaseOperation: String, regionId: Int, versionId: Int, createdReason: String, validTo: Option[Date], validFrom: Option[Date],
                  effectiveDate: Option[Date], codeListCode: String, itemCode: Option[String], regionName: String, regionIdentifier: Option[Int])


class Regions(val con: Connection) extends GenericInsert[Region] {
  override val sqlString: String =
    """
      |INSERT INTO region (database_operation, region_id, version_id, created_reason, valid_from, valid_to, effective_date, code_list_code, item_list_code, region_name, region_identifier)
      |VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  override def f(item: Region, ps: PreparedStatement): Unit = {
    ps.setString(1, item.dataBaseOperation)
    ps.setInt(2, item.regionId)
    ps.setInt(3, item.versionId)
    ps.setString(4, item.createdReason)
    ps.setDate(5, item.validFrom.getOrElse(null))
    ps.setDate(6, item.validTo.getOrElse(null))
    ps.setDate(7, item.effectiveDate.getOrElse(null))
    ps.setString(8, item.codeListCode)
    ps.setString(9, item.itemCode.getOrElse(null))
    ps.setString(10, item.regionName)
    ps.setInt(11, item.regionIdentifier.getOrElse(null.asInstanceOf[Int]))
  }
}