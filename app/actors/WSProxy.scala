package actors

import akka.actor.{ActorLogging, Actor, Props, ActorRef}

class WSProxy(out: ActorRef) extends Actor with ActorLogging {

  log.info("subcribed to event stream")
  context.system.eventStream.subscribe(self, classOf[Message])

  def receive = {
    case Message(content) => {
      out ! content
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
