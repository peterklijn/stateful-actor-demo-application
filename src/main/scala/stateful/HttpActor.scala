package stateful

import scala.concurrent.duration._

import akka.actor._
import akka.pattern.ask
import akka.util._

import spray.routing._
import spray.http.StatusCodes._

class HttpActor extends HttpServiceActor {

  import HttpBookActor._
  implicit def askTimeout = Timeout(1 second)
  implicit def ec = context.dispatcher

  val httpBookActor = context.system.actorOf(HttpBookActor.props, HttpBookActor.name)

  val routes = {
    pathPrefix("books") {
      path(Segment) { id =>
        getBookById(id)
      }
    }
  }

  def receive = runRoute(routes)

  private def getBookById(bookId: String) = {
    onSuccess(httpBookActor.ask(GetBookById(bookId))) {
      case book: String => complete(OK, book)
      case _ => complete(NotFound)
    }
  }
}

