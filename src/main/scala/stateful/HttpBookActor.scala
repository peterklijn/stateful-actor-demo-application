package stateful

import scala.concurrent._

import akka.actor._
import akka.pattern._

import spray.json._

object HttpBookActor {
  def props() = Props[HttpBookActor]
  def name = "http-book-actor"

  sealed trait BookQuery
  case class GetBookById(bookId: String) extends BookQuery
  case class FindBookByTitle(bookTitle: String) extends BookQuery
}

class HttpBookActor extends Actor {

  import HttpBookActor._

  def receive = uninitialized()

  def uninitialized(): Receive = {
    case GetBookById(bookId) => sender() ! s"Book! $bookId"
    case _ => sender()
  }
}
