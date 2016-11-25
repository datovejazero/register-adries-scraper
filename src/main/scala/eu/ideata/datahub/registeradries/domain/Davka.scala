package eu.ideata.datahub.registeradries.domain
import java.io.InputStream
import java.sql.{Connection, Date, PreparedStatement}

import eu.ideata.datahub.registeradries.db.GenericInsert
import eu.ideata.datahub.registeradries.util.Constants
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}

trait Davka {
  val id: String
  val url: String
}

case class InitialLoad(val id: String, val url: String) extends Davka
case class ChangeLoad(val id: String, val url: String) extends Davka

case class InitialDavkaWithStream(val id: String, val url: String, val is: InputStream) extends Davka
case class ZmenovaDavkaWithStream(val id: String, val url: String, val is: InputStream) extends Davka

case class ProcessedDavka(val id: String, val url: String, date: Date) extends Davka {
  def this(id: String, url: String, dateTime: DateTime) = this(id, url, new Date(dateTime.getMillis))
}

class LoadsRepository(val con: Connection) extends GenericInsert[ProcessedDavka] {
  override val sqlString: String =
    """
      |insert into processed_davky (id, url, date) values (?, ?, ?)
    """.stripMargin

  override def f(item: ProcessedDavka, ps: PreparedStatement): Unit = {
    val Array(_,resourcePath) = item.url.split(Constants.DomainNameRegex)

    ps.setString(1, item.id)
    ps.setString(2, resourcePath)
    ps.setDate(3, item.date)
  }

  def getLastDavka(implicit ec: ExecutionContext): Future[Option[ProcessedDavka]] = Future {

    val ps = con.prepareStatement("select top 1 * from processed_davky ORDER BY date DESC")
    val rs = ps.executeQuery()

    if (rs.next()) {
      val id = rs.getString(1)
      val url = rs.getString(2)
      val date = rs.getDate(3)

      Some(ProcessedDavka(id, url, date))
    } else None
  }

  def isProcessed(url: String)(implicit ec: ExecutionContext): Boolean = {
    val ps = con.prepareStatement("select * from processed_davky where url = ?")
    val Array(_,resourcePath) = url.split(Constants.DomainNameRegex)
    ps.setString(1, resourcePath)
    ps.executeQuery().next()
  }
}

