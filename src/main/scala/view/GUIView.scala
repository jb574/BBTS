package view

import java.awt
import java.awt._
import java.awt.event._
import java.text.NumberFormat
import javax.swing._

import controller.Main
import model.{WorkDay, WorkLife, WorkShift}


/**
  * this class represents
  * the main graphical user interface for the application
  *
  * @author Jack Davey
  * @version 12th March 2016
  */
class GUIView(life: WorkLife) extends View
{


  private val errorMessage = new JLabel("")
  private val headings = scala.collection.immutable.List("Week Day", "Standard AM Minutes",
    "Standard AM Hours", "Overtime AM Minutes", "Overtime AM Hours",
    "Standard PM Minutes",
    "Standard PM Hours", "Overtime PM Minutes", "Overtime PM Hours", "Total")
  private var frame: JFrame = new JFrame()
  private var table: JPanel = new JPanel()
  private var currentPanel = new JPanel()

  implicit def toActionListener(f: awt.event.ActionEvent => Unit): ActionListener = new ActionListener
  {
    def actionPerformed(event: awt.event.ActionEvent) = f(event)
  }

  /**
    * method that sets up the gui
    *
    * @param title the title of the application
    */
  def initGUI(title: String) =
  {
    frame = new JFrame("Bizzy Bees time management system");
    val pane = frame.getContentPane();
    frame.getContentPane().setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS))
    val headingPanel = getNewRowLayout();
    val next = new JButton("next month");
    next.addActionListener(new ActionListener
    {
      override def actionPerformed(e: awt.event.ActionEvent): Unit =
      {
        life.getAdjacentMonth(true)
        frame.setVisible(false)
        Main.presentInformation()
      }
    })
    val previous = new JButton("previous month");
    previous.addActionListener(new ActionListener
    {
      override def actionPerformed(e: awt.event.ActionEvent): Unit =
      {
        life.getAdjacentMonth(false)
        frame.setVisible(false)
        frame.removeAll()
        Main.presentInformation()
      }
    })
    headingPanel.add(previous);
    headingPanel.add(new JLabel(title));
    headingPanel.add(next);
    pane.add(headingPanel);
  }

  /**
    * @return a new jpanel containing a layout for a row
    */
  def getNewRowLayout(): JPanel =
  {
    val result: JPanel = new JPanel()
    result.setLayout(new FlowLayout())
    result.setBorder(BorderFactory.createLineBorder(Color.black))
    result
  }

  /**
    * method that draws the outline of the table
    */
  def createTable() =
  {
    table = new JPanel()
    table.setLayout(new BoxLayout(table, BoxLayout.Y_AXIS))
    currentPanel = initRow()
    headings.foreach(heading => currentPanel.add(new JLabel(heading)))
    table.add(currentPanel)
  }

  /**
    * @return a jpanel with a boxlayout
    */
  def initRow(): JPanel =
  {
    val panel = new JPanel()
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS))
    panel.setBorder(BorderFactory.createLineBorder(Color.black))
    panel
  }

  /**
    * method that draws a row
    * pertaining to a particualr working day
    *
    * @param day the day we're reffering to
    */
  def drawRow(day: WorkDay) =
  {
    println(day.id)
    println(day.date)
    currentPanel = initRow()
    val date = day.date
    var dateText = date.getDayOfMonth.toString
    var weekDay = date.getDayOfWeek.toString
    weekDay = weekDay.toLowerCase()
    weekDay = weekDay.substring(0, 1).toUpperCase() + weekDay.substring(1)
    dateText = s"$dateText $weekDay"
    currentPanel.add(new JLabel(dateText))
    drawShift(day.morning, true)
    drawShift(day.afternoon, false)
    currentPanel.add(ModelMapper.addMonthTotal(day.id, day.totalNumberOfHoursWorked().toString))
    table.add(currentPanel)
  }

  /**
    * draws a workshift on screen
    *
    * @param shift     the workshift itself
    * @param isMorning a boolean indicating morning or afternoon
    */
  def drawShift(shift: WorkShift, isMorning: Boolean) =
  {
    if (isMorning)
    {
      drawTimePeriod(false, shift, "Standard AM Minutes",
        "Standard AM Hours")
      drawTimePeriod(true, shift, "Overtime AM Minutes", "Overtime AM Hours")
    }
    else
    {
      drawTimePeriod(false, shift, "Standard PM Minutes",
        "Standard PM Hours")
      drawTimePeriod(true, shift, "Overtime PM Minutes", "Overtime PM Hours")
    }

  }

  /**
    * draws a particular time period on screen
    *
    * @param isOverTime    is this classed as overtime?
    * @param shift         the workshift object the values are from
    * @param minuteGuiText the text containing the minutes display
    * @param hourGuiText   the text containing the hours display
    */
  def drawTimePeriod(isOverTime: Boolean, shift: WorkShift, minuteGuiText: String, hourGuiText: String): Unit =
  {
    val minutes = createTextFeild()
    val hours = createTextFeild()
    if (isOverTime)
    {
      ModelMapper.addPeriodTotal(shift.overTime.id, minutes, hours)
      minutes.setText(shift.overTime.minutes.toString)
      hours.setText(shift.overTime.hours.toString)
    }
    else
    {
      ModelMapper.addPeriodTotal(shift.standardTime.id, minutes, hours)
      minutes.setText(shift.standardTime.minutes.toString)
      hours.setText(shift.standardTime.hours.toString)
    }
    minutes.addActionListener(new ActionListener
    {
      override def actionPerformed(e: awt.event.ActionEvent): Unit =
        ChangeTimeWorked(minutes, hours, shift, isOverTime, minutes)
    })
    hours.addActionListener(new ActionListener
    {
      override def actionPerformed(e: awt.event.ActionEvent): Unit =
        ChangeTimeWorked(minutes, hours, shift, isOverTime, minutes)
    })
    currentPanel.add(minutes)
    currentPanel.add(hours)
  }

  /**
    * event handler for all text boxes
    *
    * @param newTimeFeild the text field containing the
    *                     new time entered by the user
    */
  def ChangeTimeWorked(minuteFeild: JTextField, hourFeild: JTextField, shift: WorkShift, isOverTime: Boolean,
                       activeField: JTextField) =
  {
    val minutes: Int = getIntFromTextFeild(minuteFeild.getText)
    val hours: Int = getIntFromTextFeild(hourFeild.getText())
    if (minutes != -1 && hours != -1)
    {
      shift.updateTimeWorked(minutes, hours, isOverTime)
      isOverTime match
      {
        case true =>
          ModelMapper.changeDayLabel(shift.overTime.id, minutes.toString, hours.toString)
        case false =>
          ModelMapper.changeDayLabel(shift.standardTime.id, minutes.toString, hours.toString)
      }
      life.currentMonth.updateLabels()
    }
    else
    {
      errorMessage.setText("invalid input")
    }
  }


  /**
    * @param newTime the text field to work on
    * @return an integer from the given text field
    */
  def getIntFromTextFeild(newTime: String): Int =
  {
    val result = -1
    val stripped = newTime.trim
    try
    {
      val result = Integer.parseInt(stripped)
      return result
    }
    catch
    {
      case numberError: NumberFormatException => errorMessage.setText("you must enter a number")

    }
    result
  }

  /**
    * @return a new text field to be used on the gui
    */
  def createTextFeild(): JFormattedTextField =
  {
    val format = NumberFormat.getNumberInstance();
    format.setMinimumIntegerDigits(1);
    format.setMaximumIntegerDigits(1);
    format.setMaximumFractionDigits(0);
    val result = new JFormattedTextField(format);
    result.setColumns(1)
    result
  }

  /**
    * method that draws the botom of the table
    */
  def drawBottom(total: String): Unit =
  {
    val pane = frame.getContentPane
    val scroller = new JScrollPane(table)
    pane.add(scroller)
    val viewableTotal = s"total is $total"
    pane.add(ModelMapper.addMonthTotal(life.currentMonth.id, viewableTotal))
    pane.add(errorMessage)
  }

  /**
    * clears the screen
    **/
  def clear() =
  {

  }

  /**
    * displays everything on screen
    */
  def display() =
  {
    frame.setVisible(false);
    frame.pack();
    frame.setVisible(true);
  }
}
