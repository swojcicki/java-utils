/**
 * Copyright 2007 Slawomir Wojcicki
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Off-day utils methods.
 * <p/>
 * Created on: 2008-03-06 19:47:27 <br/>
 *
 * @author Slawek Wojcicki
 */
@SuppressWarnings({"MagicNumber"})
public final class OffDayUtils {

  private OffDayUtils() {
    throw new UnsupportedOperationException();
  }

  public static boolean isOffDayInPoland(GregorianCalendar date) {
    return isWeekend(date) || isPolishHolyDay(date);
  }

  public static boolean isWeekend(GregorianCalendar date) {
    return isSaturday(date) || isSunday(date);
  }

  public static boolean isSaturday(GregorianCalendar date) {
    return date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
  }

  public static boolean isSunday(GregorianCalendar date) {
    return date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
  }

  public static boolean isNewYear(GregorianCalendar date) {
    return date.get(Calendar.DAY_OF_YEAR) == 1;
  }

  public static boolean isChristmas(GregorianCalendar date) {

    return date.get(Calendar.MONTH) == GregorianCalendar.DECEMBER
      && (date.get(Calendar.DAY_OF_MONTH) == 25
      || date.get(Calendar.DAY_OF_MONTH) == 26);
  }

  public static boolean isEaster(GregorianCalendar date) {
    Calendar easter = Easter.findHolyDay(date.get(Calendar.YEAR));
    return easter.get(Calendar.MONTH) == date.get(Calendar.MONTH)
      && easter.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
  }

  public static boolean isPolishHolyDay(GregorianCalendar date) {
    int year = date.get(Calendar.YEAR);

    Calendar easter = (Calendar) Easter.findHolyDay(year).clone();
    Calendar secondEasterDay = (Calendar) easter.clone();
    secondEasterDay.add(Calendar.DAY_OF_MONTH, 1);

    Calendar zieloneSwiatki = (Calendar) easter.clone();
    zieloneSwiatki.add(Calendar.DAY_OF_MONTH, 49);

    Calendar bozeCialo = (Calendar) easter.clone();
    bozeCialo.add(Calendar.DAY_OF_MONTH, 60);

    GregorianCalendar d = new GregorianCalendar(year, date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

    return isNewYear(d)
      || isChristmas(d)
      || isEaster(d)
      || secondEasterDay.equals(d) // drugi dzien Wielkiej Nocy
      || new GregorianCalendar(year, Calendar.MAY, 1).equals(d)
      || new GregorianCalendar(year, Calendar.MAY, 3).equals(d)
      || zieloneSwiatki.equals(d) // pierwszy dzien Zielonych Swiatek
      || bozeCialo.equals(d) // dzien Bozego Ciala
      || new GregorianCalendar(year, Calendar.AUGUST, 15).equals(d) // 15 sierpnia - Wniebowziecie Najswietszej Maryi Panny
      || new GregorianCalendar(year, Calendar.MAY, 1).equals(d) // 1 maja - Swieto Panstwowe
      || new GregorianCalendar(year, Calendar.MAY, 3).equals(d) // 3 maja - Swieto Narodowe Trzeciego Maja
      || new GregorianCalendar(year, Calendar.NOVEMBER, 1).equals(d) // 1 listopada - Wszystkich Swietych
      || new GregorianCalendar(year, Calendar.NOVEMBER, 11).equals(d); // 11 listopada - Narodowe Swieto Niepodleglosci
  }


  public static GregorianCalendar getFirstWorkDayBefore(GregorianCalendar date) {
    if (isOffDayInPoland(date)) {
      date.add(Calendar.DAY_OF_MONTH, -1);
      getFirstWorkDayBefore(date);
    }
    return date;
  }

  public static GregorianCalendar getFirstWorkDayBefore(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return getFirstWorkDayBefore(calendar);
  }
}
