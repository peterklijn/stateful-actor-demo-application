package stateful

import akka.actor._
import akka.io.IO

import spray.can.Http

object Main extends App {
  val system = ActorSystem("stateful-demo-system")

  val api = system.actorOf(Props[HttpActor], "http-actor")

  IO(Http)(system) ! Http.Bind(listener = api, interface = "0.0.0.0", port = 5000)
}

