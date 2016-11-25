package eu.ideata.datahub.registeradries.extractor

import scala.concurrent.{ExecutionContext, Future}
import scala.xml.Node

/**
  * Created by mbarak on 30/09/16.
  */


trait BaseExtractor[T] extends BaseInformationExtractor {
  val Base: String
  def liftedExtract(node: Node)(implicit ec: ExecutionContext): Future[Iterable[T]] = Future { extract(node) }
  def extract(node: Node): Iterable[T] = (node \\ Base).toStream.map(e)
  def e(node: Node): T  = specific(node)(extractSharedInformation(node))
  def specific(n: Node)(base: BaseInformation): T
  def genericExtract[E](node: Node, f: T => E): Iterable[E] = (node \\ Base).toStream.map(n => f(specific(n)(extractSharedInformation(node))))
}