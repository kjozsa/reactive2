package actors

import play.api.libs.json.{JsObject, Json}

sealed trait Message {
  def toJson: JsObject
}

case class StatusUpdate(user: String, content: String) extends Message {
  override def toJson = Json.obj(
    "type" -> "status",
    "user" -> user,
    "content" -> content
  )
}

case class ClientsUpdate(clients: Int) extends Message {
  override def toJson = Json.obj(
    "type" -> "stats",
    "clients" -> clients
  )
}
