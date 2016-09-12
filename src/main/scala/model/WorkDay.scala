package model

import java.time.LocalDate

import com.google.common.base.Preconditions
import controller.LinkedPeriod
;

/**
  * class to represent a working day
  *
  * @author Jack Davey
  * @version 5th December 2015
  */
class WorkDay(val morning: WorkShift, val afternoon: WorkShift, val date: LocalDate) extends LinkedPeriod with WorkPeriod
{
  Preconditions.checkNotNull(morning)
  Preconditions.checkNotNull(afternoon)
  Preconditions.checkNotNull(date)

  def this() = this(new WorkShift(), new WorkShift(), LocalDate.now())


  def this(date: LocalDate) = this(new WorkShift(), new WorkShift(), date)

  /**
    * @return a string representation of this work day
    */
  override def toString: String =
  date.toString + " " + totalNumberOfHoursWorked().toString

  /**
    * method to calculate the total number
    * of hours worked over this worked period
    *
    * @return as above
    */
  override def totalNumberOfHoursWorked(): SimpleTime =
  {
    var result: SimpleTime = new SimpleTime()
    result = result + morning.totalNumberOfHoursWorked()
    result = result + afternoon.totalNumberOfHoursWorked()
    result
  }

}
