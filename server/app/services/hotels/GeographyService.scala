package services.hotels

import javax.inject.Singleton

import model.Coordinates

@Singleton
class GeographyService {

  val destinations = Map(
    "london" -> Coordinates(51.507351, -0.127758),
    "paris" -> Coordinates(48.856614, 2.352222),
    "bath" -> Coordinates(51.375801, -2.359904),
    "birmingham" -> Coordinates(52.486243, -1.890401)
  )

  def lookupDestination(name: String): Option[Coordinates] = {
    destinations.get(name.toLowerCase.trim)
  }

}