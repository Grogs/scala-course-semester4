package services.hotels

import org.scalatest.{FunSuite, Matchers}

class HotelsServiceTest extends FunSuite with Matchers {

  val catalogueService = new HotelCatalogueService

  val service = new HotelsServiceImpl(
    catalogueService,
    new GeographyService,
    new HotelFinderService(catalogueService)
  )

  test("searching in an unsupport location should return zero hotels") {
    service.search("New York", 20) shouldBe Nil
  }

  test("the search should return the correct number of hotels") {
    service.search("London", 0.5).size shouldBe 1
    service.search("London", 5).size shouldBe 9
    service.search("Paris", 5).size shouldBe 9
    service.search("Bath", 5).size shouldBe 2
  }

  test("the search results should be correct") {
    def hotels(ids: Long*) = ids.flatMap(catalogueService.lookupHotel)

    service.search("London", 0.5) shouldBe hotels(180395)
    service.search("Bath", 5) shouldBe hotels(182106, 177208)
  }

}
