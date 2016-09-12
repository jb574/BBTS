package view

import model.WorkDay

/**
  * interface defining a set
  * of methods all classes in the view should
  * have
  *
  * @author Jack Davey
  * @version 12th December 2015
  */
trait View
{
  /**
    * method that sets up the gui
    *
    * @param title the title of the application
    */
  def initGUI(title: String)

  /**
    * method that draws the outline of the table
    */
  def createTable()

  /**
    * method that draws a row
    * pertaining to a particualr working day
    *
    * @param day the day we're reffering to
    */
  def drawRow(day: WorkDay)

  /**
    * method that draws the botom of the table
    */
  def drawBottom(total: String)

  /**
    * clears the screen
    */
  def clear()

  /**
    * displays everything on screen
    */
  def display()

}
