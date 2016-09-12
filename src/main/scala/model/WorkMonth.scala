package model

import java.time.LocalDate

import com.google.common.base.Preconditions
import controller.LinkedPeriod
import view.ModelMapper

import scala.collection.Map

/**
  * class to represent a working month
  *
  * @author Jack Davey
  * @version 5th December 2015
  */
class WorkMonth(val rawMonth: LocalDate) extends LinkedPeriod with WorkPeriod
{
  Preconditions.checkNotNull(rawMonth)
  val year = rawMonth.getYear
  val month = rawMonth.getMonth
  private var _days: Map[Int, WorkDay] = Map()
  println(rawMonth.lengthOfMonth() + " monthLength")
  (1 until rawMonth.lengthOfMonth + 1).foreach(
    (day) => if (!days.contains(day))
    {
      println(s"day is  $day")
      val date = LocalDate.of(year, month, day)
      _days = _days + (day -> new WorkDay(date))
    }
  )

  def day(day: Int): WorkDay =
  {
    val option = days.get(day)
    if (option.isEmpty)
    {
      throw new IllegalArgumentException("that day does not" +
        "exist")
    }
    option.get
  }

  def updateLabels() =
  {
    days.keySet.foreach((date) =>
      ModelMapper.retrieveMonthLabel(
        days.get(date).get.id).setText(
        days.get(date).get.totalNumberOfHoursWorked().toString)
    )
    ModelMapper.retrieveMonthLabel(id).setText(totalNumberOfHoursWorked().toString)
  }

  /**
    * @return the total number of hours worked in this work month
    */
  @Override
  def totalNumberOfHoursWorked(): SimpleTime =
  {
    var result: SimpleTime = new SimpleTime
    (1 until rawMonth.lengthOfMonth + 1).foreach(
      (date) =>
      {
        val day = days.get(date).get
        println(s"current date is $day")
        println(s"result mins is ${result.minutes}")
        println(s"result hours jis ${result.hours}")
        val currentDayTime = day.totalNumberOfHoursWorked
        println(s"current day mins is ${currentDayTime.minutes}")
        println(s"current day hours is ${currentDayTime.hours}")
        result = result + day.totalNumberOfHoursWorked
      }
    )
    for ((date, day) <- days)
    {

    }
    result
  }

  def days = _days

  def this() = this(LocalDate.now())

  /**
    * @return the current month
    *         as a string to be displayed
    */
  def monthId: String = rawMonth.getYear() + rawMonth.getMonth().toString()

  def computeNewMonthId(rawMonth: LocalDate): String = rawMonth.getYear() + rawMonth.getMonth().toString()

  /**
    * @return the number of days in the current
    *         month
    */
  def numberOfDays = days.size


}
