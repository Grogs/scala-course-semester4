package controllers

import javax.inject.{Inject, Singleton}

import autowire.Core.Request
import model.{Hotel, Price}
import play.api.mvc.InjectedController
import services.hotels.{HotelPriceService, HotelPriceServiceImpl, HotelsService}
import upickle.Js.Value
import upickle.default._
import upickle.{Js, json}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


@Singleton
class HotelsController @Inject()(hotelsService: HotelsService, hotelPriceService: HotelPriceService, webJarAssets: WebJarAssets) extends InjectedController {

  def search(destination: String, radius: String) = Action {

    val distance = radius.toDouble

    if (distance > 0) {
      val foundHotels: Seq[Hotel] = hotelsService.search(destination, distance)
      val prices: Map[Long, String] = hotelPriceService
        .findPrices(foundHotels.map(_.id))
        .mapValues(p => s"${p.amount}${p.currency}")

      Ok(
        views.html.searchResults(
          destination, radius,
          foundHotels,
          prices
        )(webJarAssets)
      )
    } else {
      BadRequest("Invalid distance")
    }
  }


  object ApiServer extends autowire.Server[Js.Value, Reader, Writer] {
    def read[Result: Reader](p: Js.Value) = upickle.default.readJs[Result](p)
    def write[Result: Writer](r: Result) = upickle.default.writeJs(r)
  }

  def api(path: String) = Action.async{ implicit req =>

    val body = req.body.asText.getOrElse("")

    val parameters = json.read(body)
      .asInstanceOf[Js.Obj]
      .value
      .toMap

    val request = Request(path.split("/"), parameters)

    val hotelServiceRouter = ApiServer.route[HotelsService](hotelsService)
    val pricingServiceRouter = ApiServer.route[HotelPriceService](hotelPriceService)
    for {
      resp <- (hotelServiceRouter orElse pricingServiceRouter)(request)
    } yield
      Ok(json.write(resp))
  }

}
