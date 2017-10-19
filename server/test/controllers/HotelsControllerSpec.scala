package services.hotels

import org.jsoup.Jsoup
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

class HotelsControllerSpec extends PlaySpec with OneAppPerTest {

  "HotelsController.search - Lesson 4" should {

    "send 200 on valid request" in {

      withClue("You need to add an entry to your routes file, and start implementing HotelsController.search\n") {

        route(app, FakeRequest(GET, "/hotels/london?distance=5")).map(status) mustBe Some(OK)
      }
    }

    "include distance and destination in the title" in {
      withClue("You can pass the endpoint's parameters to your view\n") {
        val body = route(app, FakeRequest(GET, "/hotels/london?distance=5")).map(contentAsString).get
        val title = Jsoup.parse(body).select("title").html

        title must include("london")
        title must (include("5km") or include("5 kilometers"))
      }
    }

    "output the titles of the hotels found" in {

      withClue("Now you need to output your results. Document for Play's Twirl templates is here: https://www.playframework.com/documentation/2.5.x/ScalaTemplates#Overview\n") {

        val body = route(app, FakeRequest(GET, "/hotels/london?distance=1.2")).map(contentAsString).get

        body must include("Park Plaza Westminster Bridge London")
        body must include("Club Quarters, Trafalgar Square")
      }
    }

    "send 400 on invalid distance, with a descriptive error message" in {

      withClue("Validate the distance in the Controller. You'll need to use play.api.mvc.Results.BadRequest.\n") {

        route(app, FakeRequest(GET, "/hotels/london?distance=-5")).map(status) mustBe Some(BAD_REQUEST)
        route(app, FakeRequest(GET, "/hotels/london?distance=-5")).map(contentAsString) mustBe Some("Invalid distance")
      }
    }

  }



  "HotelsController.search - Lesson 5" should {

    "display results in a table" in {

      val body = route(app, FakeRequest(GET, "/hotels/london&distance=1.2")).map(contentAsString).get

      body must include("Park Plaza Westminster Bridge London")

      withClue("See docs for tables: http://getbootstrap.com/docs/3.3/css/#tables") {
        body must include regex ("<table.*class=\"table\">")
      }
    }

    "display images in the table" in {

      val body = route(app, FakeRequest(GET, "/hotels/london&distance=1.2")).map(contentAsString).get

      withClue("Your table must include an images column. Docs for HTML tables: https://www.w3schools.com/tags/tag_table.asp") {
        body must include("<th>Images</th>")
      }

      withClue("Put the images for each hotel in the column you created using <img> tags: https://www.w3schools.com/tags/tag_img.asp") {
        body must include("http://exp.cdn-hotels.com/hotels/4000000/3120000/3113100/3113039/3113039_31_y.jpg")
      }
    }

    "add a form to allow the user to change their search" in {
      val body = route(app, FakeRequest(GET, "/hotels/london&distance=1.2")).map(contentAsString).get

      withClue("See bootstrap docs for forms: http://getbootstrap.com/docs/3.3/css/#forms") {
        body must include("<form")
      }

      withClue("Your form should include an input for the destination and distance: https://www.w3schools.com/tags/att_form_method.asp") {
        body must include regex "<input.*name=\"destination\""
        body must include regex "<input.*name=\"distance\""
      }

      withClue("The inputs should contain the values the user searched with: https://www.w3schools.com/tags/att_input_value.asp") {
        body must include regex "<input.*value=\"london\""
        body must include regex "<input.*value=\"1.2\""
      }

      withClue("There should be a search button that submits the form") {
        body must include regex "<button.*type=\"submit\""
      }

      withClue("Make sure your form submits a GET: https://www.w3schools.com/tags/att_form_method.asp") {
        body must include("method=\"get\"")
      }
    }

    "include a link to the hotel on Google Maps" in {

      val body = route(app, FakeRequest(GET, "/hotels/london&distance=1.2")).map(contentAsString).get

      withClue("Add a new column for a link to the hotel on google maps") {
        body must include("<th>Location</th>")
      }

      withClue("Add a link using the <a> tag. Look at the test to see how to create the link.") {
        val googleMapsSearchQuery = "Park Plaza Westminster Bridge London near 51.501108,-0.117331"
        body must include(s"""<a href="http://maps.google.com/?q=$googleMapsSearchQuery">""")
      }
    }
  }



}
