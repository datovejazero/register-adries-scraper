package eu.ideata.datahub.registeradries.extractor

import java.sql.Date

/**
  * Created by mbarak on 04/10/16.
  */
case class BaseInformation(databaseOperation: String, objectId: Int, versionId: Int, createdReason: String, validTo: Option[Date], validFrom: Option[Date], effectiveDate: Option[Date])
