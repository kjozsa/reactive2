package actors

import akka.actor.{Actor, ActorLogging}

class Streamer extends Actor with ActorLogging {

  def receive = {
    case message => {
      context.system.eventStream.publish(Message(message))
    }
  }

}
