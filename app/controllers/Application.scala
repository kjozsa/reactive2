package controllers

import actors.WSProxy
import play.api.mvc._

object Application extends Controller {

  import play.api.Play.current

  def index() = Action { implicit request =>
    Ok(views.html.index())
  }

  def wsocket = WebSocket.acceptWithActor[String, String] { request => out => {
    WSProxy.props(out)
  }}

}
