import java.util.concurrent.Executors

import actors.Streamer
import akka.actor.Props
import play.api._
import play.libs.Akka
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    val streamer = Akka.system.actorOf(Props[Streamer], name = "streamer")

    val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))
    Akka.system.scheduler.schedule(1 seconds, 2 seconds, streamer, "tick")(ec)
    Logger.info("Akka scheduler started")
  }

}