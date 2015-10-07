package actors

import java.util.concurrent.Executors

import actors.ClientsTracker._
import akka.actor.{Actor, ActorLogging}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class ClientsTracker extends Actor with ActorLogging {
  private var clients = 0

  val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
  context.system.scheduler.schedule(1 seconds, 1 seconds, self, PublishStats)(ec)

  def receive = {
    case Joined => clients += 1
    case Left => clients -= 1
    case PublishStats => context.system.eventStream.publish(ClientsUpdate(clients))
  }
}

object ClientsTracker {
  case object Joined
  case object Left
  case object PublishStats
}
