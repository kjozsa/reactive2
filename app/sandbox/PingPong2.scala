package sandbox

import akka.actor._

import scala.concurrent.duration._
import scala.util.Try

object PingPong2 extends App {

  case object Start
  case object Ping
  case object Pong
  case class PlayWith(ref: ActorRef)
  case object SimulateFailure

  class Game extends Actor {
    override def supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 1, withinTimeRange = 1 second, loggingEnabled = true) {
      case _ => SupervisorStrategy.Restart
    }

    def receive = {
      case Start => {
        val first = context.actorOf(Props(classOf[Player]), "Player1")
        val second = context.actorOf(Props(classOf[Player]), "Player2")
        first ! PlayWith(second)
      }

      case SimulateFailure => {
        println("### killing in the name of ..")
        context.children.head ! SimulateFailure
      }
    }
  }

  class Player extends Actor {
    var count = 1

    def send(message: Any) {
      println(s"${message.toString.toUpperCase} $count")
      count += 1
      Try(Thread.sleep(100))
      sender ! message
    }

    def receive = {
      case PlayWith(player) => player ! Pong
      case Ping => send(Pong)
      case Pong => send(Ping)
      case SimulateFailure => throw new RuntimeException("booom")
    }
  }

  val system = ActorSystem("ping-pong")
  val game = system.actorOf(Props(classOf[Game]), "game")
  game ! Start

  List(600, 1200, 1800).foreach(time =>
    Try(Thread.sleep(time)).foreach(_ => game ! SimulateFailure)
  )

  Try(Thread.sleep(2400)).foreach(_ => {
    println("================= shutdown")
    system.shutdown()
  })
}
