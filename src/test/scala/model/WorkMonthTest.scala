package model

import java.time.LocalDate

import org.scalatest._


/* Created by jackdavey on 04/09/2016.
 */
class WorkMonthTest extends FlatSpec with Matchers
{
  "a workmonth date" should "be rejected if null" in
    {
      var passed = false
      try
      {
        val month = new WorkMonth(null)
      }
      catch
      {
        case error: NullPointerException => passed = true;
      }
      passed should be === true
    }
  "a workmonth" should "contain an entry for every day of the month" in
    {
      val month = new WorkMonth(LocalDate.now());
      month.numberOfDays should be === LocalDate.now().lengthOfMonth()
    }

  "a workmonth" should "create a workday object for every day" in
    {
      val rawMonth = LocalDate.now()
      val month = new WorkMonth()
      (1 until rawMonth.lengthOfMonth + 1).foreach(
        (day) => month.day(day).date.getDayOfMonth should be === day
      )
    }


  "a workday" should "add up the values of its days correctly" in
    {
      var expectedTime = new SimpleTime
      for (index <- 1 to 2)
      {
        val shift: WorkShift = new WorkShift
        println("i rule")
        info("i rule")
        setDummyTimeData(shift)
        var newTime = shift.totalNumberOfHoursWorked()
        newTime = newTime + shift.totalNumberOfHoursWorked()
        expectedTime = expectedTime + newTime
        info("hi")
      }
      info("out")
      val month: WorkMonth = new WorkMonth();
      setDummyDayData(1, month)
      setDummyDayData(2, month)
      info("should be getting somewhere now")
      month.totalNumberOfHoursWorked() should be === expectedTime
      info("well, we passed")
      true should be === true
    }


  private def setDummyDayData(day: Int, month: WorkMonth) =
  {
    val workday: WorkDay = month.day(day)
    setDummyTimeData(workday.morning)
    setDummyTimeData(workday.afternoon)
  }

  private def setDummyTimeData(shift: WorkShift): Unit =
  {
    info(shift.toString())
    shift.updateTimeWorked(2, 2, true)
    shift.updateTimeWorked(2, 2, false)
    info(shift.toString())
  }


}
