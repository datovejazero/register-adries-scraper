package eu.ideata.datahub.registeradries.extractor

import scala.util.Try
import scala.xml.Node

/**
  * Created by mbarak on 04/10/16.
  */
trait BaseInformationExtractor extends ExtractorDateTimeFormaters {
  def extractSharedInformation(node: Node): BaseInformation = {

    val databaseOperation = (node \\ "databaseOperation").text
    val objectId = (node \ "objectId").text.toInt
    val versionId = (node \ "versionId").text.toInt
    val createdReason = (node \ "createdReason").text
    val validTo = Option((node \ "validTo").text).filter(_.nonEmpty).map(fromIsoDateTime)
    val validFrom = Option((node \ "validFrom").text).filter(_.nonEmpty).map(fromIsoDateTime)
    val effectiveDate = Option((node \ "effectiveDate").text).filter(_.nonEmpty).flatMap(s => {
      Try(fromRegularDate(s))
        .recover{ case e => fromRegularDate2(s) }
        .toOption
    })

    BaseInformation(databaseOperation, objectId, versionId, createdReason, validTo, validFrom, effectiveDate)
  }
}
