package fss

import org.scalajs.dom.{Event, document}
import org.scalajs.dom.html.{Button, Input}
import services.hotels.HotelsService

import autowire._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("App")
object App {

  def hotelsTables() = document.querySelector("table")
  def destination() = document.getElementById("destination").asInstanceOf[Input]
  def distance() = document.getElementById("distance").asInstanceOf[Input]
  def searchButton() = document.getElementById("load-hotels").asInstanceOf[Button]

  @JSExport
  def main(): Unit = {
    println("Hello from Scala.js")

    //EXERCISE 1

    //Key up for when the user changes the text
    destination().onkeyup = handleChange _
    distance().onkeyup = handleChange _

    //On change for when the user selects an item from the autocomplete
    distance().onchange = _ => reload(destination().value, distance().value.toDouble) //to show you can use a lambda instead

    searchButton().style.display = "none"


    //EXERCISE 2
    new Autocomplete(
      destination(),
      Seq("London", "Paris", "Bath"),
      _ => handleChange(null)
    )
  }

  def handleChange(e: Event) = {
    reload(destination().value, distance().value.toDouble)
  }

  def reload(destination: String, distance: Double) = {
    for {
      hotels <- Client[HotelsService].search(destination, distance).call() //Note the .call()
      table = views.html.hotelsTable(hotels).body //Yay, reused code across frontend and backend!
    } hotelsTables().outerHTML = table
  }

  //For exercise 3, add `if hotels.nonEmpty` to the for-comprehension.

}


