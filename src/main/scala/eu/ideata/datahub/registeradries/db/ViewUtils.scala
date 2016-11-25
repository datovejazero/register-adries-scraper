package eu.ideata.datahub.registeradries.db

import org.joda.time.DateTime

/**
  * Created by mbarak on 17/10/2016.
  */
object ViewUtils {
  val TmpTableFile = "create_tmp_table.sql"
  val InsertFile = "insert_into_table.sql"
  val IndexFile = "create_index_on_tmp_table.sql"
  val AlterFile = "alter_v_address.sql"
  val DomainTables = "domain_tables.sql"
  val MunicipalityView = "alter_municipality.sql"
  val StreetName = "alter_street_name.sql"

  def getTmpTable = s"address_${DateTime.now().getMillis}"

  def createTmpTable(tableName: String) =  getTableQuery(TmpTableFile).format(tableName)

  def insertIntoTmpTable(tmpTable: String) = getTableQuery(InsertFile).format(tmpTable)

  def createIndexOnTmpTable(tmpTable: String) = getTableQuery(IndexFile).format(Range(0, 5).map(_ => tmpTable): _*)

  def alterView(tmpTable: String) = getTableQuery(AlterFile).format(tmpTable)

  def alterDomainTable(now: String): Iterable[String] = List(
    getTableQuery(DomainTables).format(Range(0,4).map(_ => now): _*),
    getTableQuery(MunicipalityView).format(now),
    getTableQuery(StreetName).format(now)
  )

  def flow(tmpTable: String = getTmpTable) =
    List(createTmpTable(tmpTable),
      insertIntoTmpTable(tmpTable),
      createIndexOnTmpTable(tmpTable),
      alterView(tmpTable)
    ) ++ alterDomainTable(DateTime.now().getMillis.toString)

  private def getTableQuery(path: String) = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(path)).getLines().mkString("\n")
}
