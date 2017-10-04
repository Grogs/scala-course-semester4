package model

import play.api.libs.json.Json

case class Hotel(id: Long, name: String, coordinates: Coordinates, images: Seq[String])

object Hotel {
  implicit val writes = Json.format[Hotel]
}

