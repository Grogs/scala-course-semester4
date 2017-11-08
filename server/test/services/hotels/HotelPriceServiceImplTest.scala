package services.hotels

import model.Price
import org.scalatest.{FunSuite, Matchers}


class HotelPriceServiceImplTest extends FunSuite with Matchers {

  private val priceGenerator = new HotelPriceGenerator(new HotelCatalogueService)
  private val service = new HotelPriceServiceImpl(priceGenerator)

  test("The HotelPriceServiceImpl should find the price for an existent hotel") {
    val existingHotelId = 106271L
    val price = service.findPrice(existingHotelId)

    price shouldBe Some(Price(71))
  }

  test("The HotelPriceServiceImpl should not find the price for a non-existent hotel") {
    val fakeHotelId = 1L
    val price = service.findPrice(fakeHotelId)

    price shouldBe None
  }

  test("The HotelPriceServiceImpl should find all available prices of a group of hotel IDs") {
    val existingHotels = Seq(106271L, 218097L)
    val notAvailableHotels = Seq(1L, 2L)

    val prices: Map[Long, Price] = service.findPrices(existingHotels ++ notAvailableHotels)

    prices.get(106271L) shouldBe Some(Price(71))
    prices.get(218097L) shouldBe Some(Price(297))
    prices.get(1L) shouldBe None
    prices.get(2L) shouldBe None
  }

}
