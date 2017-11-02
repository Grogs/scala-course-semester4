package fss

import org.scalajs.dom.{Element, Event, document}
import org.scalajs.dom.html.{Button, Input}
import services.hotels.HotelsService
import autowire._
import google.maps.InfoWindowOptions
import model.{Coordinates, Hotel}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.jquery.{JQueryEventObject, jQuery}

import scala.scalajs.js


@JSExportTopLevel("App")
object App {

  def hotelsTables() = document.querySelector("table")
  def destination() = document.getElementById("destination").asInstanceOf[Input]
  def distance() = document.getElementById("distance").asInstanceOf[Input]
  def searchButton() = document.getElementById("load-hotels").asInstanceOf[Button]

  @JSExport
  def main(): Unit = {
    println("Hello from Scala.js")
    initialiseInteractiveSearch()

    //Add event handler for modal, per https://getbootstrap.com/docs/3.3/javascript/#modals-events
    jQuery("#mapModal").on("shown.bs.modal", onMapOpen _)

    def onMapOpen(e: JQueryEventObject) = {
      println("This runs when the user opens the map")
      val dest = destination().value
      val dist = distance().value.toLong

      Client[HotelsService].search(dest, dist).call().foreach { hotels =>
        document.getElementById("mapModalLabel").innerHTML = s"Hotels within ${dist}km of $dest"
        renderMap(document.getElementById("map"), hotels)
      }
    }

  }




  //Lesson 6
  def initialiseInteractiveSearch() = {
    //EXERCISE 1
    distance().onchange =  (keyevent) => handleChange(keyevent)
    destination().onkeyup = handleChange
    distance().onkeyup = handleChange _

    searchButton().style.display = "none"

    def handleChange(e: Event) = reload(destination().value, distance().value.toDouble)

    def reload(destination: String, distance: Double) = {
      for {
        hotels <- Client[HotelsService].search(destination, distance).call() //Note the .call()
        table = views.html.hotelsTable(hotels).body //Yay, reused code across frontend and backend!
      } hotelsTables().outerHTML = table
    }

    //EXERCISE 2
    new Autocomplete(
      destination(),
      Seq("London", "Paris", "Bath"),
      _ => handleChange(null)
    )

    //For exercise 3, add `if hotels.nonEmpty` to the for-comprehension in the reload function.
  }

  def renderMap(target: Element, hotels: Seq[Hotel]) = {

    val opts = google.maps.MapOptions(
      center = new google.maps.LatLng(50, 0),
      zoom = 11
    )

    val gmap = new google.maps.Map(target, opts)

    val point =
      for {
        hotel <- hotels
        Coordinates(lat, long) = hotel.coordinates
        latLng = new google.maps.LatLng(lat, long)
      } yield {

        val marker = new google.maps.Marker(google.maps.MarkerOptions(
          position = latLng,
          map = gmap,
          title = hotel.name
        ))

        val infoWindow = new google.maps.InfoWindow(
          InfoWindowOptions( content =
            s"""
               |<div>
               |  <h2>${hotel.name}</h2>
               |  <p>${hotel.descriptionHtml}</p>
               |</div>
            """.stripMargin
          )
        )

        marker -> infoWindow
      }

    val markerBounds = new google.maps.LatLngBounds()
    var activeInfoWindow = new google.maps.InfoWindow

    for {
      (marker, infoWindow) <- point
    } yield {
      marker.addListener("click", (_:js.Any) => {
        activeInfoWindow.close()
        activeInfoWindow = infoWindow
        infoWindow.open(gmap, marker)
      })
      markerBounds.extend(marker.getPosition())
    }

    gmap.fitBounds(markerBounds)

  }

}


