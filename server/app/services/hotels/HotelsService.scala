package services.hotels

import javax.inject.Inject

import model.{Hotel, Price}

class HotelsServiceImpl @Inject()(catalogueService: HotelCatalogueService,
                              geographyService: GeographyService,
                              hotelFinderService: HotelFinderService) extends HotelsService {

  def search(destination: String, radius: Double): Seq[Hotel] =
    for {
      coordinates <- geographyService.lookupDestination(destination).toSeq
      hotelId <- hotelFinderService.findHotels(coordinates, radius)
      hotel <- catalogueService.lookupHotel(hotelId)
    } yield hotel

//  def price(hotelId: Long): Option[Price] =
//    priceService.findPrice(hotelId)

}
