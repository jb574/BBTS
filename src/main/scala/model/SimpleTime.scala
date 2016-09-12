package model

import com.google.common.base.Preconditions
import controller.LinkedPeriod

/**
  * incredibly simple class representing a unit
  * of time in hours and minutes
  *
  * @author Jack Davey
  * @version 23rd May 2016
  * @param _minutes the amount of minutes specified
  * @param _hours   the amount of hours specified
  */
class SimpleTime(private var _minutes: Int, private var _hours: Int) extends LinkedPeriod
{
  Preconditions.checkArgument(_minutes >= 0)
  Preconditions.checkArgument(_hours >= 0)

  /**
    * add a unit of time to the simple time
    * object
    *
    * @param isMinutes boolean that determines
    *                  whether we add to the minutes field
    *                  or the hours field
    * @param unit      the amount of time to add
    * @return a new object with the correct amount of time added
    */
  def changeValue(isMinutes: Boolean, unit: Int): SimpleTime =
  {
    var temp = 0;
    if (isMinutes)
    {
      temp = evaluateValue(unit, _minutes)
      _minutes = 0 + temp
      convertExtraMinutesToHours()

    }
    else
    {
      temp = evaluateValue(unit, _hours)
      _hours = 0 + temp
    }
    return this
  }

  /**
    * adds two simpletime units together
    *
    * @param other the other object
    * @return the simpletime post addition
    */
  def +(other: SimpleTime): SimpleTime =
  {
    val result = new SimpleTime
    result._minutes = this._minutes + other._minutes
    result._hours = this._hours + other._hours
    result.convertExtraMinutesToHours()
    result
  }

  /**
    * default construcotr
    * it just sets both of the two
    * values to zero
    */
  def this() = this(0, 0)

  /**
    * allows for changing  time value
    * stored by this object
    *
    * @param newMins  the new minutes value
    * @param newHours the new hours value
    */
  def changeTime(newMins: Int, newHours: Int) =
  {
    _minutes = evaluateValue(newMins, _minutes)
    _hours = evaluateValue(newHours, _hours)
    convertExtraMinutesToHours()
  }

  /**
    * method to check if this object
    * stores more than 60 minutes
    * if it does, then we convert those minutes
    * to hours,
    */
  def convertExtraMinutesToHours(): Unit =
  {
    println(_minutes)
    while (_minutes >= 60)
    {
      _minutes = _minutes - 60
      _hours = _hours + 1
      println(_minutes)
    }
  }

  def minutes_=(newMinutes: Int) = evaluateValue(newMinutes, _minutes)

  def hours_=(newHours: Int) = evaluateValue(newHours, _hours)

  /**
    * helper method to save code duplication
    * in the setter methods
    * if the new value is greater than zero
    * we return it, otherwise, the old value is returned
    *
    * @param newValue the new value to test
    * @param oldValue the value to return if the test is
    *                 unsuccessful
    * @return as above
    */
  private def evaluateValue(newValue: Int, oldValue: Int): Int =
  {
    if (newValue > 0)
    {
      newValue
    }
    else
    {
      oldValue
    }
  }

  override def toString = s"m: $minutes  h $hours"

  def minutes = _minutes

  def hours = _hours

  /**
    * @param other another object
    * @return whether that object is equal to this simpletime object
    */
  override def equals(other: Any): Boolean = other match
  {
    case that: SimpleTime =>
      (that canEqual this) &&
        _minutes == that._minutes &&
        _hours == that._hours
    case _ => false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[SimpleTime]

  /**
    * @return the hashcode for this simpletime object
    */
  override def hashCode(): Int =
  {
    val state = Seq(_minutes, _hours)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
