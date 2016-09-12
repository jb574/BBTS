package controller

import org.scalatest._

import scala.collection.Set

/**
  * Created by jackdavey on 10/09/2016.
  */
class LinkedPeriodTest extends FlatSpec with Matchers
{
  "the linkedPeriod id" should "always be 1 greater than the last one" in
    {
      var count = 1;
      var seenIDs: Set[Int] = Set()
      while (count < 100)
      {
        var id = LinkedPeriod.getNewID()
        seenIDs(id) should be === false
        seenIDs = seenIDs + id
        count = count + 1
      }
    }

}
