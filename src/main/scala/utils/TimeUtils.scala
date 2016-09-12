package utils

import java.time.Duration

/**
  * class to contain utiltiy functions
  *
  * @author Jack Davey
  * @version 15th Febuary 2015
  */
object TimeUtils
{
  /**
    * @param duration a java duration object
    * @return a string representation of said duration
    */
  def getTimeAsString(duration: Duration): String =
  {
    val minutes = duration.toMinutes
    val hours = duration.toHours
    s"$minutes m $hours h"
  }
}
