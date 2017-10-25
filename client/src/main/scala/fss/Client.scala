package fss

import org.scalajs.dom.ext.Ajax
import upickle.Js.Obj
import upickle.default._
import upickle.{Js, json}

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object Client extends autowire.Client[Js.Value, Reader, Writer] {
    def doCall(req: Request): Future[Js.Value] =
        Ajax.post(
            url = "/hotels/api/" + req.path.mkString("/"),
            data = json.write(Obj(req.args.toSeq: _*))
        ).map(_.responseText).map(json.read)

    def read[Result: Reader](p: Js.Value) = readJs[Result](p)

    def write[Result: Writer](r: Result) = writeJs(r)
}