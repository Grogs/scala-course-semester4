package services.hotels

import model.Coordinates
import org.scalatest.{FunSuite, Matchers}

class HotelFinderServiceTest extends FunSuite with Matchers {

  val service = new HotelFinderService(new HotelCatalogueService)

  test("the HotelFinderService should implement haversine distance calculation, to calculate the kilometers between two coordinates") {
    service.haversine(0,0, 0,0) shouldBe 0.0
    service.haversine(10,10, 10,10) shouldBe 0.0

    service.haversine(0,0, 10,10) shouldBe 1568.963711248778
    service.haversine(-9,-9, 9,9) shouldBe 2825.5134978128904
  }

  test("the HotelFinderService should implement findHotels, to return all the hotels within a given radius of some coordinates") {
    service.findHotels(Coordinates(0, 0), 5) shouldBe Nil

    service.findHotels(Coordinates(51.507351, -0.127758), 0.5) shouldBe Seq(180395)
    service.findHotels(Coordinates(51.507351, -0.127758), 2) shouldBe Seq(192432, 118948, 335698, 448661, 180395)

    service.findHotels(Coordinates(48.856614, 2.352222), 2) shouldBe Seq(176705, 202244)
  }

}
