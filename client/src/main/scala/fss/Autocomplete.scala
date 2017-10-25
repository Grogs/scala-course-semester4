package fss

import org.scalajs.dom.ext.PimpedHtmlCollection
import org.scalajs.dom.html.{Input, UList}
import org.scalajs.dom.raw.Event
import org.scalajs.dom.{document, window}

import scalatags.JsDom.all._

/**
  * Requires Bootstrap CSS to be in scope
  *
  * @param input The input box to provide autocompletion on
  * @param values The possible values
  * @param select An optional callback to call when the user clicks on a value
  */
class Autocomplete(input: Input, values: Seq[String], select: String => Unit = _ => ()) {

  sealed trait Action
  case class Select(value: String) extends Action
  case class Change(value: String) extends Action
  case object Show extends Action
  case object Hide extends Action

  val dropdownStyle = style := s"left: ${input.offsetLeft}px; top: ${input.offsetTop + input.offsetHeight}px; position: absolute; z-index: 100; display: block;  bottom: auto;"
  val dropdownId = input.id + "-autcomplete"

  val suggestions =
    ul( `class`:="dropdown-menu", id:=dropdownId, dropdownStyle,
      for (v <- values) yield {
        li( onmousedown := (() => handle(Select(v))),
          a(v)
        )
      }
    ).render

  def handle(e: Action): Unit = {
    def show() = suggestions.style.display = "block"
    def hide() = suggestions.style.display = "none"
    e match {
      case Select(value) =>
        hide()
        input.value = value
        select(value)
      case Change("") | Show =>
        suggestions.children.foreach(_.classList.remove("hidden"))
        show()
      case Change(nonEmpty) =>
        for (s <- suggestions.children)
          if (s.textContent.toLowerCase contains nonEmpty.toLowerCase)
            s.classList.remove("hidden")
          else
            s.classList.add("hidden")
        show()
      case Hide =>
        hide()
    }
  }


  input.parentNode.insertBefore(suggestions, input.nextSibling)
  input.autocomplete = "off"
  input.oninput = (e: Event) => handle(Change(input.value))
  input.onfocus = (e: Event) => handle(Show)
  input.onblur = (e: Event) => { handle(Hide); false}

  window.onresize = (e: Event) => {
    val dropdownStyle = document.getElementById(dropdownId).asInstanceOf[UList].style
    dropdownStyle.left = input.offsetLeft+"px"
    dropdownStyle.top = (input.offsetTop + input.offsetHeight)+"px"
  }

  handle(Hide)

}