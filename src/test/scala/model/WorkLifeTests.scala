package model

import java.time.LocalDate

import org.scalatest._

/**
  * Created by jackdavey on 10/09/2016.
  */
class WorkLifeTest extends FlatSpec with Matchers
{
  "a workmonth date" should "be rejected if null" in
    {
      var passed = false
      try
      {
        val month = new WorkLife(null)
      }
      catch
      {
        case error: NullPointerException => passed = true;
      }
      passed should be === true
    }

  "the date in a worklife" should "be the first day of the month" in
    {
      var date = LocalDate.now()
      val life = new WorkLife(date)
      date = LocalDate.of(date.getYear(), date.getMonth(), 1)
      life.date should be === date
    }

  "when the previous month is chosen the worklife" should "have the correct month" in
    {
      val life = new WorkLife
      var date = life.currentMonth.rawMonth
      date = date.minusMonths(1)
      life.getAdjacentMonth(false)
      life.currentMonth.rawMonth should be === date
    }



  "when the next month is chosen the worklife" should "have the correct month" in
    {
      val life = new WorkLife
      var date = life.currentMonth.rawMonth
      date = date.plusMonths(1)
      life.getAdjacentMonth(true)
      life.currentMonth.rawMonth should be === date
    }


}
