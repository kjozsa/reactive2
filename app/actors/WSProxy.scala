package actors

import akka.actor.{ActorLogging, Actor, Props, ActorRef}

class WSProxy(out: ActorRef) extends Actor with ActorLogging {

  context.system.eventStream.subscribe(self, classOf[Message])

  def receive = {
    case Message(content) => {
      out ! content
    }

    case s => log.info(s"received: $s")
  }
}

object WSProxy {
  def props(out: ActorRef) = Props(classOf[WSProxy], out)
}
