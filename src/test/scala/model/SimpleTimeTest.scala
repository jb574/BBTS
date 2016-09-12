package model

import org.scalatest._

/**
  * Created by jackdavey on 14/05/2016.
  */
class SimpleTimeTest extends FlatSpec with Matchers
{
  "The default constructor of a simpleTime object" should "set everything to 0" in
    {
      val time = new SimpleTime
      time.minutes should be === 0
      time.hours should be === 0
    }

  "a simpletime object" should "have the correct values upon construction" in
    {
      val time = new SimpleTime(4, 5);
      time.minutes should be === 4
      time.hours should be === 5
    }

  "a simpletime" should "reject minute values < 0" in
    {
      var passed = false
      try
      {
        val time = new SimpleTime(-3, 5)
      }
      catch
      {
        case error: IllegalArgumentException => passed = true;
      }
      passed should be === true
    }

  "a simpletime" should "reject hour values < 0" in
    {
      var passed = false
      try
      {
        val time = new SimpleTime(3, -5)
      }
      catch
      {
        case error: IllegalArgumentException => passed = true;
      }
      passed should be === true
    }

  "a SimpleTime minute field" should "be set to 5 afer 5 minutes are added to it" in
    {
      val time = new SimpleTime
      time.changeValue(true, 5)
      time.minutes should be === 5
    }

  "a SimpleTime minute field" should "be set to 11 afer 11 minutes are added to it" in
    {
      val time = new SimpleTime
      time.changeValue(true, 11)
      time.minutes should be === 11
    }
  "a SimpleTime minute field" should "be set to 40 afer 100 minutes are added to it" in
    {
      val time = new SimpleTime
      time.changeValue(true, 40)
      time.minutes should be === 40
    }
  "a SimpleTime hour field" should "be set to 5 afer 5 hours are added to it" in
    {
      val time = new SimpleTime
      time.changeValue(false, 5)
      time.hours should be === 5
    }

  "a SimpleTime hour field" should "be set to 11 afer 11 hours are added to it" in
    {
      val time = new SimpleTime
      time.changeValue(false, 11)
      time.hours should be === 11
    }
  "a SimpleTime hour field" should "be set to 100 afer 100 hours are added to it" in
    {
      val time = new SimpleTime
      time.changeValue(false, 100)
      time.hours should be === 100
    }
  "a SimpleTime minute field" should "defend against illegal arguments" in
    {
      val time = new SimpleTime(2, 2);
      time.changeValue(true, -1)
      time.minutes should be === 2
    }
  "a SimpleTime hour field" should "defend against illegal arguments" in
    {
      val time = new SimpleTime(2, 2)
      time.changeValue(false, -1)
      time.hours should be === 2
    }
  "a SimpleTime minute field" should "be set to 5 afer 5 minutes are added to it " +
    "using the + operator" in
    {
      val time = new SimpleTime
      time.changeValue(true, 5)
      var other = new SimpleTime
      other = other + time
      other.minutes should be === 5
    }

  "a SimpleTime minute field" should "be set to 11 afer 11 minutes are added to it" +
    "using the + operator" in
    {
      val time = new SimpleTime
      time.changeValue(true, 11)
      val other = new SimpleTime + time
      other.minutes should be === 11
    }
  "a SimpleTime minute field" should "be set to 40 afer 40 minutes are added to it" +
    "using the + operator" in
    {
      val time = new SimpleTime
      time.changeValue(true, 40)
      val other = new SimpleTime + time
      other.minutes should be === 40
    }
  "a SimpleTime hour field" should "be set to 5 afer 5 hours are added to it" +
    "using the + operator" in
    {
      val time = new SimpleTime
      time.changeValue(false, 5)
      val other = new SimpleTime + time
      other.hours should be === 5
    }

  "a SimpleTime hour field" should "be set to 11 afer 11 hours are added to it" +
    "using the + operator" in
    {
      val time = new SimpleTime
      time.changeValue(false, 11)
      val other = new SimpleTime + time
      other.hours should be === 11
    }
  "a SimpleTime hour field" should "be set to 100 afer 100 hours are added to it" +
    "using the + operator" in
    {
      val time = new SimpleTime
      time.changeValue(false, 100)
      val other = new SimpleTime + time
      other.hours should be === 100
    }

  " the changeTime method" should "not accept minute illegal values" in
    {
      val time = new SimpleTime(2, 4)
      time.changeTime(-2, 3)
      time.minutes should be === 2
      time.hours should be === 3
    }

  " the changeTime method" should "not accept hour illegal values" in
    {
      val time = new SimpleTime(2, 4)
      time.changeTime(3, -4)
      time.minutes should be === 3
      time.hours should be === 4
    }

  "a simpletime" should "not do truncate minutes when they are < 60" in
    {
      val time = new SimpleTime(30, 20)
      time.convertExtraMinutesToHours
      time.minutes should be === 30
    }

  "a simpletime" should "handle minute values greater than 60 correctly" in
    {
      val time = new SimpleTime(124, 5)
      time.convertExtraMinutesToHours
      time.minutes should be === 4
      time.hours should be === 7
    }

  "a simpletime" should "cahnlde extra minute conversion when the time is changed" in
    {
      val time = new SimpleTime
      time.changeTime(124, 5)
      time.minutes should be === 4
      time.hours should be === 7
    }
  "a simpletime" should "cahnlde extra minute conversion when  tow are added together" in
    {
      val firstTime = new SimpleTime
      val time = firstTime + new SimpleTime(124, 5)
      time.minutes should be === 4
      time.hours should be === 7
    }

  "a simpletime" should "keep both values intact after additon" in
    {
      val firstTime = new SimpleTime(2, 2)
      val firstMins = firstTime.minutes
      val firstHours = firstTime.hours
      val secondTime = new SimpleTime(2, 2)
      val secondMins = secondTime.minutes
      val secondHours = secondTime.hours
      val result = firstTime + secondTime
      firstTime.minutes should be === firstMins
      firstTime.hours should be === firstHours
      secondTime.minutes should be === secondMins
      secondTime.hours should be === secondHours
    }

  "a simpletime" should "convert extra minutes to hoursr" in
    {
      val time = new SimpleTime(0, 5)
      info("rocking")
      time.changeValue(true, 124)
      time.minutes should be === 4
      time.hours should be === 7
    }

}
