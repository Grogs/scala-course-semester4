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
    //This code runs when the page loads
  }

}


