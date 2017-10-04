package services.hotels

import model.Coordinates
import org.scalatest.{FunSuite, Matchers}

class GeographyServiceTest extends FunSuite with Matchers {

  val service = new GeographyService

  test("unsupported location") {
    service.lookupDestination("Hong Kong") shouldBe None
  }

  test("London") {
    service.lookupDestination("London") shouldBe Some(Coordinates(51.507351, -0.127758))
  }

  test("Paris") {
    service.lookupDestination("Paris") shouldBe Some(Coordinates(48.856614, 2.352222))
  }

  test("Bath") {
    service.lookupDestination("Bath") shouldBe Some(Coordinates(51.375801, -2.359904))
  }

  test("Birmingham") {
    service.lookupDestination("Birmingham") shouldBe Some(Coordinates(52.486243, -1.890401))
  }

  test("the lookup should be case insensitive") {
    service.lookupDestination("london") shouldBe Some(Coordinates(51.507351, -0.127758))
    service.lookupDestination("paris") shouldBe Some(Coordinates(48.856614, 2.352222))
    service.lookupDestination("bath") shouldBe Some(Coordinates(51.375801, -2.359904))
    service.lookupDestination("birmingham") shouldBe Some(Coordinates(52.486243, -1.890401))
  }

  test("whitespace should be ignored") {
    service.lookupDestination("london ") shouldBe Some(Coordinates(51.507351, -0.127758))
    service.lookupDestination("paris ") shouldBe Some(Coordinates(48.856614, 2.352222))
    service.lookupDestination("bath ") shouldBe Some(Coordinates(51.375801, -2.359904))
    service.lookupDestination("Birmingham ") shouldBe Some(Coordinates(52.486243, -1.890401))
  }
}
