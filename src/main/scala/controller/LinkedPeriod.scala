package controller

/**
  * any class that inherits from this
  * has a corresponding graphical object
  * or pair of objects in the GUI
  *
  * @author Jack Davey
  * @version 17th May 2016
  */
abstract class LinkedPeriod
{
  val id = LinkedPeriod.getNewID()
}

/**
  * this class contains a static method and variable to support
  * the LinkedPeriod class
  *
  * @author Jack Davey
  * @version 23rd May 2016
  */
object LinkedPeriod
{
  private var currrentID: Int = 0

  def reset = currrentID = 0

  /**
    * @return the next availible unused ID number
    */
  def getNewID(): Int =
  {
    currrentID = currrentID + 1
    currrentID
  }
}

