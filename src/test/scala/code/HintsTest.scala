package code

import org.specs.Specification

import _root_.org.joda.time._

import _root_.net.liftweb.json._
import _root_.net.liftweb.json.JsonAST._
import _root_.net.liftweb.json.Serialization.{read, write => swrite}
import _root_.net.liftweb.mongodb._

import com.mongodb._

import model._

case class TstCls(interval: Interval)
case class CustomTstCls(interval: CustomInterval)
  
object HintsTest extends Specification {

  doBeforeSpec {
    //MongoDB.defineDb(DefaultMongoIdentifier, MongoAddress(MongoHost(), "test_hints"))
  }

  "Joda Interval Hints Test" in {

    implicit val formats = Serialization.formats(JodaHints.hints)

    val t = TstCls(new Interval(new DateTime(2004, 12, 25, 0, 0, 0, 0), new DateTime(2005, 1, 1, 0, 0, 0, 0)))
    
    val ser = swrite(t)
    val t2 = read[TstCls](ser) // this throws an Exception
    
    t.interval.getStart must_== t2.interval.getStart
    t.interval.getEnd must_== t2.interval.getEnd
  }
  
  "CustomInterval Hints Test" in {
    
    implicit val formats = Serialization.formats(CustomIntervalHints.hints)

    val t = CustomTstCls(new CustomInterval(10, 12))
    
    val ser = swrite(t)
    val t2 = read[CustomTstCls](ser)
    
    t.interval.start must_== t2.interval.start
    t.interval.end must_== t2.interval.end
  }
  /*
  "MongoDocInterval Test" in {
    
    val oid = ObjectId.get.toString
    val cls = MongoDocIntervalCls(oid, new Interval(new DateTime(2004, 12, 25, 0, 0, 0, 0), new DateTime(2005, 1, 1, 0, 0, 0, 0)))
    
    cls.save
    
    val cls2 = MongoDocIntervalCls.find(oid)
    
    cls must_== cls2
  }
  
  
  "MongoDocCustomInterval Test" in {
    
    val oid = ObjectId.get.toString
    val cls = MongoDocCustomIntervalCls(oid, new CustomInterval(10, 12))
    
    cls.save
    
    val cls2 = MongoDocCustomIntervalCls.find(oid)
    
    cls2.isDefined must_== true
    cls._id must_== cls2.get._id
  }
  */

}