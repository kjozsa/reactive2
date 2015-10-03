package actors

import akka.actor.{Actor, ActorLogging}

class Streamer extends Actor with ActorLogging {

  def receive = {
    case message => {
      log.info(s"received: $message")
    }
  }

}
