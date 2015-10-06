import actors.Streamer
import akka.actor.Props
import play.api._
import play.libs.Akka

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    val streamer = Akka.system.actorOf(Props[Streamer], name = "streamer")
    streamer ! "init"
  }

}