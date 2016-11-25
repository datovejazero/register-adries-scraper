package eu.ideata.datahub.registeradries.db

import java.sql.{Connection, PreparedStatement}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by mbarak on 10/10/16.
  */
trait GenericInsert[T <: Product] {
  val size = 1024
  val sqlString: String
  val con: Connection

  def f(item: T, ps: PreparedStatement): Unit
  con.setAutoCommit(false)

  def liftedInsert(items: Iterable[T])(implicit ec: ExecutionContext) : Future[Option[Int]] = Future {
    groupedInsert(items)
  }

  def groupedInsert(items: Iterable[T]): Option[Int] = {
    val statement = con.prepareStatement(sqlString)
    val sum = items.grouped(size).map { g =>
      insert(g, statement)
    }.flatten.sum
    statement.close()
    Some(sum)
  }

  def insert(items: Iterable[T], statement: PreparedStatement): Option[Int] = {
    items.foreach(i => {
      f(i,statement)
      statement.addBatch()
    })
    val i = statement.executeBatch().reduce(_ + _)
    con.commit()
    if(items.nonEmpty){
      println(s"${items.head.getClass.getName} $i inserted")
    }
    Some(i)
  }
}
