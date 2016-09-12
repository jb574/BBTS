package model

import com.google.common.base.Preconditions;

/**
  * class that represents a working Shift
  *
  * @author Jack Davey
  * @version 5th December 2015
  */
class WorkShift(private var _standardTime: SimpleTime,
                private var _overTime: SimpleTime) extends WorkPeriod
{
  Preconditions.checkNotNull(_standardTime)
  Preconditions.checkNotNull(_overTime)

  /**
    * * default constructor that
    * sets up the field with no values
    */
  def this()
  {
    this(new SimpleTime(), new SimpleTime())
  }

  /**
    * @return the total time worked in that particular shift
    */
  @Override
  def totalNumberOfHoursWorked(): SimpleTime = _standardTime + _overTime

  /**
    * @return a string representation of the shift
    */
  override def toString(): String = _standardTime.toString + " " + _overTime.toString

  /**
    * a new set of values to this object
    *
    * @param  mins      , the number of minutes worked
    * @param hours      , the number of hours worked
    * @param isOvertime ,  true if this is extra time
    */
  def updateTimeWorked(mins: Int, hours: Int, isOvertime: Boolean) =
  {
    Preconditions.checkArgument(mins >= 0)
    Preconditions.checkArgument(hours >= 0)
    if (isOvertime)
    {
      _overTime.changeTime(mins, hours)
    }
    else
    {
      _standardTime.changeTime(mins, hours)
    }
  }


  def standardTime = _standardTime

  def overTime = _overTime

}
