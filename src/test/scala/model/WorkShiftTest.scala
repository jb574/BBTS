package model

import org.scalatest._

/**
  * Created by jackdavey on 22/05/2016.
  */
class WorkShiftTest extends FlatSpec with Matchers
{
  "all time fields of a WorkShift" should "be set to zero" in
    {
      val shift = new WorkShift
      val standardTimeOk = TestUtils.checkSimpleTimeValues(shift.standardTime, 0, 0)
      val overTimeOk = TestUtils.checkSimpleTimeValues(shift.overTime, 0, 0)
      standardTimeOk should be === true
      overTimeOk should be === true
    }

  "a workshift" should "have the correct values when not default constructed" in
    {
      val standardTime = new SimpleTime(2, 4)
      val overTime = new SimpleTime(3, 5)
      val shift = new WorkShift(standardTime, overTime);
      shift.standardTime should be === standardTime
      shift.overTime should be === overTime
    }

  "a workshift standard time" should "should be rejected if null" in
    {
      val time = new SimpleTime
      var passed = false
      try
      {
        val shift = new WorkShift(null, time)
      }
      catch
      {
        case error: NullPointerException => passed = true;
      }
      passed should be === true
    }

  "a workshift overtime" should "should be rejected if null" in
    {
      val time = new SimpleTime
      var passed = false
      try
      {
        val shift = new WorkShift(time, null)
      }
      catch
      {
        case error: NullPointerException => passed = true;
      }
      passed should be === true
    }

  "a workshift standard time time" should " produce the values 2 and 4 when thse values are assigned" in
    {
      val shift: WorkShift = new WorkShift(
        new SimpleTime(5, 4), new SimpleTime(5, 4))
      shift.updateTimeWorked(2, 4, false)
      TestUtils.checkSimpleTimeValues(shift.standardTime, 2, 4) should be === true
    }

  "a workshift overtime time" should " produce the values 2 and 4 when thse values are assigned" in
    {
      val shift: WorkShift = new WorkShift(
        new SimpleTime(5, 4), new SimpleTime(5, 4))
      shift.updateTimeWorked(2, 4, true)
      TestUtils.checkSimpleTimeValues(shift.overTime, 2, 4) should be === true
    }

  "adding (2,4) + (4,5)" should "produce (6,9)" in
    {
      val shift: WorkShift = TestUtils.workShift(2, 4, 4, 5)
      val res: SimpleTime = shift.totalNumberOfHoursWorked()
      TestUtils.checkSimpleTimeValues(res, 6, 9) should be === true
    }

  "adding (2,4) + (0,0)" should "produce (2,4)" in
    {
      val shift: WorkShift = TestUtils.workShift(2, 4, 0, 0)
      val res: SimpleTime = shift.totalNumberOfHoursWorked()
      TestUtils.checkSimpleTimeValues(res, 2, 4) should be === true
    }

  "adding (0,0) + (4,5)" should "produce (4,5)" in
    {
      val shift: WorkShift = TestUtils.workShift(0, 0, 4, 5)
      val res: SimpleTime = shift.totalNumberOfHoursWorked()
      TestUtils.checkSimpleTimeValues(res, 4, 5) should be === true
    }

  "adding (6,6) + (6,6)" should "produce (12,12)" in
    {
      val shift: WorkShift = TestUtils.workShift(6, 6, 6, 6)
      val res: SimpleTime = shift.totalNumberOfHoursWorked()
      TestUtils.checkSimpleTimeValues(res, 12, 12) should be === true
    }

  "a  new workshift  minute value" should "should be rejected if less than zero" in
    {
      val shift = new WorkShift
      var passed = false
      try
      {
        shift.updateTimeWorked(-3, 4, true)
      }
      catch
      {
        case error: IllegalArgumentException => passed = true;
      }
      passed should be === true
    }

  "a  new workshift  hour value" should "should be rejected if less than zero" in
    {
      val shift = new WorkShift
      var passed = false
      try
      {
        shift.updateTimeWorked(3, -4, true)
      }
      catch
      {
        case error: IllegalArgumentException => passed = true;
      }
      passed should be === true
    }


}
