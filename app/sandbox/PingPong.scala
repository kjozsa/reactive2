package sandbox

import akka.actor._
import scala.util.Try

object PingPong extends App {
  case object Ping
  case object Pong
  case class PlayWith(ref: ActorRef)

  class Player extends Actor {
    def receive = {
      case PlayWith(player) => {
        player ! Ping
      }
      case Ping => {
        println("PING")
        sender ! Pong
      }
      case Pong => {
        println("PONG")
        sender ! Ping
      }
    }
  }

  val system = ActorSystem("pingpong")
  val first = system.actorOf(Props(classOf[Player]), "Player1")
  val second = system.actorOf(Props(classOf[Player]), "Player2")

  first ! PlayWith(second)

  Try(Thread.sleep(3000)).foreach(_ => system.shutdown())
}
