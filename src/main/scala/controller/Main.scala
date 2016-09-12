package controller

import model.WorkLife
import view._

/**
  * main controller that specifies what the application
  * should do
  *
  * @author Jack Davey
  * @version 12th December 2015
  */
object Main extends App
{
  var life = new WorkLife()
  var mainView = new GUIView(life)
  presentInformation()


  def exit() =
  {
    life = null
    mainView = null
  }

  /**
    * method to display everything on screen
    * called when a new view is loaded
    */
  def presentInformation(): Unit =
  {
    mainView = new GUIView(life)
    mainView.clear();
    val monthTitle = life.displayableMonth;
    mainView.initGUI(monthTitle);
    mainView.createTable();
    val month = life.currentMonth
    (1 until month.days.size + 1).foreach((day) =>
      mainView.drawRow(month.day(day))
    )
    mainView.drawBottom(month.totalNumberOfHoursWorked().toString)
    mainView.display();
  }
}
