import org.jsoup.Jsoup
import org.scalatest.{FunSuite, Matchers}
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test._

import scala.collection.JavaConverters._

class Lesson7 extends FunSuite with WsScalaTestClient with GuiceOneAppPerTest with Matchers {

  /*

    See: https://getbootstrap.com/docs/3.3/javascript/#modals

  */


  test("add another button for showing the map"){

    val body = Jsoup.parse(route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get)

    val buttons = body.select("button").asScala

    buttons.map(_.id) should contain ("show-map")

    body.getElementById("show-map").classNames.asScala should contain ("btn")
  }

  test("bootstrap modal for the map"){

    val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

    val maybeModal = Jsoup.parse(body).select("div.modal").asScala.headOption

    maybeModal shouldBe 'defined

    val modal = maybeModal.get

    modal.id shouldBe "mapModal"
  }

  test("button should be configurd to open the modal"){

    val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

    val modal = Jsoup.parse(body).getElementById("show-map")

    modal.attr("data-toggle") shouldBe "modal"
    modal.attr("data-target") shouldBe "#mapModal"
  }

  test("modal should include a container for the google map"){

    val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

    val modal = Jsoup.parse(body).select("div.modal").first

    val mapContainer = modal.getElementById("map")

    mapContainer should not be null

    //Specify the height otherwise it will be 0px high
    mapContainer.attr("style") shouldBe "height: 500px"
  }

}