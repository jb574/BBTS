package view

import javax.swing.{JFormattedTextField, JLabel}

/**
  * code used by the gui to help it add totals
  *
  * @author Jack Davey
  * @version 5th May 2016
  */
object ModelMapper
{
  var monthTotals: Map[Int, JLabel] = Map()
  var periodTotals: Map[Int, TimePeriodCollection] = Map()

  /**
    * get a new label for a month total
    * to display on the gui, if one exists
    * then create one
    *
    * @param id           the month id
    * @param initialValue the intial value of the total
    * @return a jlabel for diaplaying on the gui
    */
  def addMonthTotal(id: Int, initialValue: String): JLabel =
  {
    if (monthTotals.contains(id))
    {
      val result = monthTotals.get(id).get;
      result.setText(initialValue)
      result
    }
    else
    {
      val result = new JLabel(initialValue)
      monthTotals = monthTotals + (id -> result)
      result
    }
  }

  /**
    * get a new label for a day total
    * to display on the gui, if one exists
    * then create one
    *
    * @param id        the day id
    * @param minField  the intial value of the minute field
    * @param hourField the intial value of the hour field
    * @return a jlabel for diaplaying on the gui
    */
  def addPeriodTotal(id: Int, minField: JFormattedTextField,
                     hourField: JFormattedTextField) =
  {
    println(s"adding id $id")
    if (periodTotals.contains(id))
    {
      val container = periodTotals.get(id).get
      container.minuteField.setText(minField.getText)
      container.hourField.setText(hourField.getText)
    }
    else
    {
      periodTotals = periodTotals + (id -> new TimePeriodCollection(minField, hourField))
    }
  }

  /**
    * @param id the id of the month label to retireve
    * @return the appropriate jlabel for gui display
    */
  def retrieveMonthLabel(id: Int): JLabel =
  {
    println(id)
    if (monthTotals.get(id).isDefined)
    {
      return monthTotals.get(id).get
    }
    else
    {
      // throw new IllegalArgumentException("invalid id");
      return new JLabel()
    }
  }

  /**
    * @param id the id of the day label to retireve
    * @return the appropriate timePeriodCollection for gui display
    */
  def retrieveDayhLabel(id: Int): TimePeriodCollection =
  {
    if (periodTotals.get(id).isDefined)
    {
      return periodTotals.get(id).get
    }
    else
    {
      throw new IllegalArgumentException(s"invalid id, $id")
    }
  }

  /**
    * change a day label
    *
    * @param id       the day id
    * @param minsText the new text of the minute field
    * @param hourText the new text of the hour field
    */
  def changeDayLabel(id: Int, minsText: String, hourText: String) =
  {
    if (periodTotals.get(id).isDefined)
    {
      val period = periodTotals.get(id).get
      period.minuteField.setText(minsText)
      period.hourField.setText(hourText)
    }
    else
    {
      throw new IllegalArgumentException("invalid id")
    }

  }

}


/**
  * simple class to rperesent a time period
  *
  * @author Jack Davey
  * @version 4th May 2016
  * @param minuteField a field for the minutes
  * @param hourField   a field for the hours
  */
class TimePeriodCollection(val minuteField: JFormattedTextField, val hourField: JFormattedTextField)
{
  /**
    * change the time displayed on the gui
    *
    * @param minutes the new minute field
    * @param hours   the new hour field
    */
  def setTime(minutes: Int, hours: Int) =
  {
    minuteField.setText(minutes.toString)
    hourField.setText(hourField.toString)
  }
}