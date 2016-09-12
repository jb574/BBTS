package model

import java.time.LocalDate

import org.scalatest._

/**
  * Created by jackdavey on 03/09/2016.
  */
class WorkDayTest extends FlatSpec with Matchers
{

  private def buildShift(standardMins: Int, standardHours: Int,
                         overTimeMins: Int, overtimeHours: Int): WorkShift =
  {
    return new WorkShift(new SimpleTime(standardMins, standardHours),
      new SimpleTime(overTimeMins, overtimeHours))
  }

  "a workday morning" should "should be rejected if null" in
    {
      val shift = new WorkShift()
      var passed = false
      try
      {
        val day = new WorkDay(null, shift, LocalDate.now())
      }
      catch
      {
        case error: NullPointerException => passed = true;
      }
      passed should be === true
    }
  "a workday afternoon" should "should be rejected if null" in
    {
      val shift = new WorkShift()
      var passed = false
      try
      {
        val day = new WorkDay(shift, null, LocalDate.now())
      }
      catch
      {
        case error: NullPointerException => passed = true;
      }
      passed should be === true
    }
  "a workday date" should "should be rejected if null" in
    {
      val shift = new WorkShift()
      var passed = false
      try
      {
        val day = new WorkDay(shift, shift, null)
      }
      catch
      {
        case error: NullPointerException => passed = true;
      }
      passed should be === true
    }
  "when two workdays are added together, hhe result" should "be correct" in
    {
      val morning = buildShift(2, 2, 2, 2)
      val afternoon = buildShift(2, 2, 2, 2)
      val day = new WorkDay(morning, afternoon, LocalDate.now())
      val testResult = day.totalNumberOfHoursWorked()
      testResult.minutes should be === 8
      testResult.hours should be === 8
    }


}
