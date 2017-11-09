import org.jsoup.Jsoup
import org.scalatest.{Assertion, FunSuite, Matchers}
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test._

import scala.collection.JavaConverters._

class Lesson7 extends FunSuite with WsScalaTestClient with GuiceOneAppPerTest with Matchers {

  /*

    See: https://getbootstrap.com/docs/3.3/javascript/#live-demo

  */


  test("add another button for showing the map"){

    val body = Jsoup.parse(route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get)

    val buttons = body.select("button").asScala

    (buttons.map(_.id) should contain ("show-map")) orElse "We want a new button with the ID `show-map`."

    (body.getElementById("show-map").classNames.asScala should contain ("btn")) orElse "We need to add the `btn` class so bootstrap styles it nicely."
  }

  test("bootstrap modal for the map"){

    val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

    val maybeModal = Jsoup.parse(body).select("div.modal").asScala.headOption

    (maybeModal shouldBe 'defined) orElse "You haven't created a modal."

    val modal = maybeModal.get

    (modal.id shouldBe "mapModal") orElse "You haven't given the modal the correct ID."
  }

  test("button should be configurd to open the modal"){

    val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

    val modal = Jsoup.parse(body).getElementById("show-map")

    withClue("Check the modal docs again, we need to set some attributes on the button to configure it to show the map modal.") {
      modal should not be null
      (modal.attr("data-toggle") shouldBe "modal") orElse "You haven't made the button a modal toggle."
      (modal.attr("data-target") shouldBe "#mapModal") orElse "You haven't specified the target element to show as the modal (it's mapModal which you created for the previous test)."
    }
  }

  test("modal should include a container for the google map"){

    val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

    val modal = Jsoup.parse(body).select("div.modal").first

    val mapContainer = modal.getElementById("map")

    (mapContainer should not be null) orElse "You need to create a div to put the map into"

    (mapContainer.attr("style") shouldBe "height: 500px") orElse "You need to specify the height otherwise it will be 0px high 🙁."
  }

  implicit class WithClueSyntax(a: => Assertion) {
    def orElse(clue:String) = withClue(clue)(a)
  }
}