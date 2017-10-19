package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, InjectedController}
import services.hotels.HotelsService

@Singleton
class HotelsController @Inject()(hotelsService: HotelsService, webJarAssets: WebJarAssets) extends InjectedController {

  def search(destination: String, radius: String) = Action {

    val distance = radius.toDouble

    //Create a new view and call it with the search results, which you can get from HotelsService
    InternalServerError
  }

}
