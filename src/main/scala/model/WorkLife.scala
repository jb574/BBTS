package model

import java.time.LocalDate

import com.google.common.base.Preconditions

/**
  * class that represents the working life
  * of a user using this application
  *
  * @author Jack Davey
  * @version 12th December 2015
  */
class WorkLife(private var _date: LocalDate)
{
  Preconditions.checkNotNull(_date)
  var currentMonth: WorkMonth = new WorkMonth(_date);
  _date = LocalDate.of(date.getYear(), date.getMonth(), 1)
  var displayableMonth: String = initDisplayableMonth(_date);
  private var monthsWorked: Map[String, WorkMonth] = Map()
  monthsWorked = monthsWorked + (currentMonth.monthId -> currentMonth)

  /**
    * @return the number of days in the onth
    */
  def numberOfMonthDays = currentMonth.numberOfDays

  /**
    * default constructor
    */
  def this() = this(LocalDate.now())

  /**
    * @return the current month
    */
  def date = _date

  def getAdjacentMonth(isNext: Boolean) =
  {
    var month = currentMonth.rawMonth
    if (isNext)
    {
      month = month.plusMonths(1);
    }
    else
    {
      month = month.minusMonths(1);
    }
    currentMonth = createNewWorkMonth(month)
    displayableMonth = initDisplayableMonth(month)
  }

  def initDisplayableMonth(month: LocalDate): String =
  {
    var displaybleMonth = ""
    displaybleMonth = month.getMonth().toString();
    displaybleMonth = displaybleMonth.toLowerCase();
    displaybleMonth = displaybleMonth.substring(0, 1).toUpperCase() +
      displaybleMonth.substring(1);
    displaybleMonth = displaybleMonth + " ";
    displaybleMonth = displaybleMonth +
      month.getYear();
    displaybleMonth
  }

  private def createNewWorkMonth(nextMonth: LocalDate): WorkMonth =
  {
    val option = monthsWorked.get(currentMonth.computeNewMonthId(nextMonth))
    if (option.isDefined)
    {
      option.get
    }
    else
    {
      currentMonth = new WorkMonth(nextMonth)
      monthsWorked = monthsWorked + (currentMonth.monthId -> currentMonth)
      currentMonth
    }
  }

}
