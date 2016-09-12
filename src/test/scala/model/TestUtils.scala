package model

/**
  * Created by jackdavey on 22/05/2016.
  */
object TestUtils
{
  def checkSimpleTimeValues(target: SimpleTime, mins: Int, hours: Int): Boolean =
    target.minutes == mins && target.hours == hours

  def workShift(sMins: Int, sHours: Int, oMins: Int, oHours: Int): WorkShift =
    new WorkShift(simpleTime(sMins, sHours), simpleTime(oMins, oHours))

  def simpleTime(mins: Int, hours: Int): SimpleTime = new SimpleTime(mins, hours)

}
