package actors

import akka.actor.{Actor, ActorLogging}
import twitter4j._

class Streamer extends Actor with ActorLogging {

  System.setProperty("twitter4j.loggerFactory", "twitter4j.NullLoggerFactory")
  val twitter = TwitterFactory.getSingleton()
  val stream = new TwitterStreamFactory(twitter.getConfiguration).getInstance()

  stream.addListener(new StatusAdapter {
    override def onStatus(status: Status): Unit = {
      context.system.eventStream.publish(StatusUpdate(status.getUser.getScreenName, status.getText))
    }
  })

  stream.filter(new FilterQuery().language("en").track("scala", "linux", "android"))

  def receive = {
    case _ =>
  }

  override def postStop(): Unit = {
    log.info("shutting down twitter stream")
    stream.shutdown()
  }
}
