package actors

import play.api.libs.json.{Json, JsObject}

case class Message(user: String, content: String) {
  def toJson: JsObject = Json.obj(
    "user" -> user,
    "content" -> content
  )
}
