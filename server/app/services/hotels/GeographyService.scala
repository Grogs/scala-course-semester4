package services.hotels

import javax.inject.Singleton

import model.Coordinates

@Singleton
class GeographyService {

  def lookupDestination(name: String): Option[Coordinates] = {
    //Takes a destination as a string, and returns the coordinates, if available.

    //We return a Option[Coordinate] because the the destination may not be supported.
    //If you are unfamiliar with Scala's Option, this is a good resource: http://danielwestheide.com/blog/2012/12/19/the-neophytes-guide-to-scala-part-5-the-option-type.html

    throw new NotImplementedError("You need to implement this method to return the coordinates specified in the test.")
  }

}