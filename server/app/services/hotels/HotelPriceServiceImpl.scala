package services.hotels

import com.google.inject.{Inject, Singleton}
import model.Price

@Singleton
class HotelPriceServiceImpl @Inject()(hotelPriceGenerator: HotelPriceGenerator) extends HotelPriceService {

  def findPrice(hotelId: Long): Option[Price] =
    hotelPriceGenerator.pricesByHotelId.get(hotelId)

  def findPrices(hotelIds: Seq[Long]): Map[Long, Price] =
    hotelIds.map(hotelId => (hotelId -> findPrice(hotelId)))
      .collect {
        case (hotelId, Some(price)) => (hotelId -> price)
      }.toMap

}
