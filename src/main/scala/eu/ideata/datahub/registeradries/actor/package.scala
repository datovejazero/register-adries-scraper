package eu.ideata.datahub.registeradries

/**
  * Created by mbarak on 14/10/2016.
  */
package object actor {

  trait DatahubMessage

  case object InitialDone extends DatahubMessage
  case object ChangeLoadDone extends DatahubMessage
  case class Init(url: String) extends DatahubMessage

  val DONE = "DONE"
  val PING = "PING"

}
