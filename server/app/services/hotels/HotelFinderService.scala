package services.hotels

import javax.inject.{Inject, Singleton}

import model.Coordinates

@Singleton
class HotelFinderService @Inject()(catalogueService: HotelCatalogueService) {

  val hotelCoordinatesAndIds: Seq[(Coordinates, Long)] =
    for {
      hotel <- catalogueService.hotels.values.toSeq
    } yield (hotel.coordinates, hotel.id)

  def findHotels(near: Coordinates, radius: Double): Seq[Long] = {

    for {
      (coordinates, id) <- hotelCoordinatesAndIds
      distance = haversine(near.lat, near.long, coordinates.lat, coordinates.long)
      if distance <= radius
    } yield id

  }

  def haversine(lat1:Double, lon1:Double, lat2:Double, lon2:Double)={

    val R = 6372.8

    import math._

    val dLat=(lat2 - lat1).toRadians
    val dLon=(lon2 - lon1).toRadians

    val a = pow(sin(dLat/2),2) + pow(sin(dLon/2),2) * cos(lat1.toRadians) * cos(lat2.toRadians)
    val c = 2 * asin(sqrt(a))
    R * c
  }

}