package services.hotels

import com.google.inject.{Inject, Singleton}
import model.Price

@Singleton
class HotelPriceGenerator @Inject()(hotelCatalogueService: HotelCatalogueService) {
  lazy val pricesByHotelId: Map[Long, Price] =
    hotelCatalogueService.hotels.mapValues(hotel => Price(generateFakePrice(hotel.id).toInt))

  private def generateFakePrice(hotelId: Long): Double =
    hotelId % 600
}
