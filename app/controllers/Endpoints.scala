package controllers

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
}