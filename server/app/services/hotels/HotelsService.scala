package services.hotels

import javax.inject.Inject

import model.Hotel

class HotelsService @Inject() (
                                catalogueService: HotelCatalogueService,
                                geographyService: GeographyService,
                                hotelFinderService: HotelFinderService) {

  def search(destination: String, radius: Double): Seq[Hotel] = {
    throw new NotImplementedError("You need to implement this by calling the other services you've implemented already")
    //You get get the coordinates from the geographyService
    //Then use those coordinates to find the IDs for hotels in that area using the hotelFinderService
    //Then use the catalogueService to fetch the Hotel for each ID
  }

}