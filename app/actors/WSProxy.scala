package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class WSProxy(out: ActorRef) extends Actor with ActorLogging {

  log.info("subcribed to event stream")
  context.system.eventStream.subscribe(self, classOf[Message])

  def receive = {
    case message: Message => {
      out ! message.toJson.toString
    }

    case s => log.info(s"received: $s")
  }

  override def postStop() = {
    log.info("unsubcribed from event stream")
    context.system.eventStream.unsubscribe(self)
  }
}

object WSProxy {
  def props(out: ActorRef) = Props(classOf[WSProxy], out)
}
