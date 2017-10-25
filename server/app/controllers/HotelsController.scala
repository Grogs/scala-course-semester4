package controllers

import javax.inject.{Inject, Singleton}

import autowire.Core.Request
import play.api.mvc.InjectedController
import services.hotels.HotelsService
import upickle.default._
import upickle.{Js, json}

import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class HotelsController @Inject()(hotelsService: HotelsService, webJarAssets: WebJarAssets) extends InjectedController {

  def search(destination: String, radius: String) = Action {

    val distance = radius.toDouble

    if (distance > 0) {
      Ok(
        views.html.searchResults(
          destination, radius,
          hotelsService.search(destination, distance)
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

    for {
      resp <- ApiServer.route[HotelsService](hotelsService)(request)
    } yield
      Ok(json.write(resp))
  }

}
