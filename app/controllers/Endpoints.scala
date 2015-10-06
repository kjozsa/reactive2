package controllers

import actors.WSProxy
import play.api.libs.json._
import play.api.mvc._

object Endpoints extends Controller {

  def boo(stuff: String) = Action {
    Ok(
      Json.obj(
        "result" -> s"stuff was $stuff"
      )
    )
  }

  import play.api.Play.current

  def wsocket = WebSocket.acceptWithActor[String, String] { request => out => {
    WSProxy.props(out)
  }}

  def wstest() = Action { implicit request =>
    Ok(views.html.wstest("boo"))
  }
}