import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest extends Simulation {
  val httpConf = http
    .baseURL("http://localhost:9000")
    .wsBaseURL("ws://localhost:9000")
    .wsMaxReconnects(1)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("loadtest")
    .exec(http("Home").get("/"))
    .exec(ws("Connect WS").open("/wsocket"))
    .pause(90 seconds)
    .exec(ws("Close WS").close)
//    .pause(1 second)
//    .exec(ws("Reconciliate states").reconciliate)
    .pause(3 second)

  setUp(
    scn.inject(
      rampUsers(8000) over (60 seconds)
    )
  ).protocols(httpConf)
}
