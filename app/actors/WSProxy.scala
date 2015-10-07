package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class WSProxy(out: ActorRef) extends Actor with ActorLogging {

  def receive = {
    case message: Message => {
      out ! message.toJson.toString
    }

    case s => log.info(s"received: $s")
  }

  override def preStart() = {
    context.system.eventStream.subscribe(self, classOf[Message])
    statsActor.map(_ ! ClientsTracker.Joined)
  }

  override def postStop() = {
    context.system.eventStream.unsubscribe(self)
    statsActor.map(_ ! ClientsTracker.Left)
  }

  def statsActor = context.actorSelection("akka://application/user/stats").resolveOne(1 seconds)
}

object WSProxy {
  def props(out: ActorRef) = Props(classOf[WSProxy], out)
}
