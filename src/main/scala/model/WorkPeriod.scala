package model

;

/**
  * interface representing the specifcation
  * that all shifts need to futfil
  *
  * @author Jack Davey
  * @version 5th December  2015
  */
trait WorkPeriod
{
  /**
    * method to calculate the total number
    * of hours worked over this worked period
    *
    * @return as above
    */
  def totalNumberOfHoursWorked(): SimpleTime
}
