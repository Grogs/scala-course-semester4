package services.hotels

import javax.inject.{Inject, Singleton}

import model.Coordinates

@Singleton
class HotelFinderService @Inject()(catalogueService: HotelCatalogueService) {

  def haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double = {
    throw new NotImplementedError("You need to implement haversine distance calculation here. See https://rosettacode.org/wiki/Haversine_formula#Scala")
  }

  def findHotels(near: Coordinates, radius: Double): Seq[Long] = {
    throw new NotImplementedError("To implement this method, go through the hotels in the catalogueService, find the ones in the area specified (which you can check with your haversine function)")
  }

}