package code {
package model {

import _root_.org.joda.time._

import _root_.net.liftweb.json._
import _root_.net.liftweb.json.JsonAST._
import _root_.net.liftweb.mongodb._

class CustomInterval(val start: Int, val end: Int)

case class MongoDocIntervalCls(_id: String, interval: Interval)
  extends MongoDocument[MongoDocIntervalCls] {
  
  def meta = MongoDocIntervalCls
}

object MongoDocIntervalCls extends MongoDocumentMeta[MongoDocIntervalCls] {
  override def formats = Serialization.formats(JodaHints.hints)
}

case class MongoDocCustomIntervalCls(_id: String, interval: CustomInterval)
  extends MongoDocument[MongoDocCustomIntervalCls] {
  
  def meta = MongoDocCustomIntervalCls
}

object MongoDocCustomIntervalCls extends MongoDocumentMeta[MongoDocCustomIntervalCls] {
  override def formats = Serialization.formats(CustomIntervalHints.hints)
}


object JodaHints {
  val hints = new ShortTypeHints(classOf[Interval] :: Nil) {
    override def serialize: PartialFunction[Any, JObject] = {
      case interval: Interval => JObject(JField("startInstant",
        JInt(interval.getStart.getMillis)) :: JField("endInstant",
        JInt(interval.getEnd.getMillis)) :: Nil)
    }

    override def deserialize: PartialFunction[(String, JObject), Any] = {
      case ("Interval", JObject(JField("startInstant", JInt(start)) ::
        JField("endInstant", JInt(end)) :: Nil)) => new Interval(start.longValue,
          end.longValue)
    }
  }
}

object CustomIntervalHints {
  val hints = new ShortTypeHints(classOf[CustomInterval] :: Nil) {
    override def serialize: PartialFunction[Any, JObject] = {
      case interval: CustomInterval => JObject(JField("startInstant",
        JInt(interval.start)) :: JField("endInstant",
        JInt(interval.end)) :: Nil)
    }

    override def deserialize: PartialFunction[(String, JObject), Any] = {
      case ("CustomInterval", JObject(JField("startInstant", JInt(start)) ::
        JField("endInstant", JInt(end)) :: Nil)) => new CustomInterval(start.intValue, end.intValue)
    }
  }
}

}
}
