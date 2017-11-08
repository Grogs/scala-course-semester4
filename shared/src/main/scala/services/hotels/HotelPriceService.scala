package services.hotels

import model.Price


trait HotelPriceService {
  def findPrice(hotelId: Long): Option[Price]
  def findPrices(hotelIds: Seq[Long]): Map[Long, Price]
}
