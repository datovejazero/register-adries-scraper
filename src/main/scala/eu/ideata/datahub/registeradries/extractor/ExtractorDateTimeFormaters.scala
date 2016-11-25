package eu.ideata.datahub.registeradries.extractor

import org.joda.time.format.{DateTimeFormat, ISODateTimeFormat}

/**
  * Created by mbarak on 03/10/16.
  */
trait ExtractorDateTimeFormaters {
  val validToParser = ISODateTimeFormat.dateTimeParser()
  val efectiveDateParser = DateTimeFormat.forPattern("yyyy-MM-dd+HH:mm")
  val effectiedateParser2 = DateTimeFormat.forPattern("yyyy-MM-dd")

  def fromIsoDateTime(s: String): java.sql.Date = new java.sql.Date(validToParser.parseDateTime(s).getMillis)
  def fromRegularDate(s: String): java.sql.Date = new java.sql.Date(efectiveDateParser.parseDateTime(s).getMillis)
  def fromRegularDate2(s: String): java.sql.Date = new java.sql.Date(effectiedateParser2.parseDateTime(s).getMillis)
}
