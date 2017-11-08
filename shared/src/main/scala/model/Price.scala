package model

//import play.api.libs.json.Json


case class Price(amount: Int, currency: String = "£")

object Price {
//  implicit val writes = Json.format[Price]
}
