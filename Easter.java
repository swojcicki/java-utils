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
import java.util.GregorianCalendar;

/**
 * Easter date util.
 * <p/>
 * Created on: 2008-03-06 19:41:21 <br/>
 *
 * @author Slawek Wojcicki
 */
public final class Easter {

  public static final int ALGORITHM_FIRST_VALID_YEAR = 1582;
  private static final int MARCH_DAYS_NUMBER = 31;

  private Easter() {
    throw new UnsupportedOperationException();
  }

  /**
   * Compute the day of the year that Easter falls on.
   * Step names E1 E2 etc., are direct references to Knuth, Vol 1, p 155.
   *
   * @param year where looking for the Easter date
   * @return date of Easter holy day.
   * @throws IllegalArgumentException If the year is before 1582 (since the
   *                                  algorithm only works on the Gregorian calendar).
   */
  @SuppressWarnings({"MagicNumber"})
  public static Calendar findHolyDay(int year) {
    if (year <= ALGORITHM_FIRST_VALID_YEAR) {
      throw new IllegalArgumentException("Algorithm invalid before April 1583");
    }

    int golden = (year % 19) + 1;           /* E1: metonic cycle */
    int century = (year / 100) + 1;         /* E2: e.g. 1984 was in 20th C */
    int x = (3 * century / 4) - 12;         /* E3: leap year correction */
    int z = ((8 * century + 5) / 25) - 5;   /* E4: sync with moon's orbit */
    int d = (5 * year / 4) - x - 10;
    int epact = (11 * golden + 20 + z - x) % 30; /* E5: epact */
    if ((epact == 25 && golden > 11) || epact == 24) {
      epact++;
    }
    int n = 44 - epact;
    if (n < 21) { /* E6: */
      n += 30;
    } else {
      n += 0;
    }
    n += 7 - ((d + n) % 7);
    if (n > MARCH_DAYS_NUMBER) { /* E7: */
      return new GregorianCalendar(year, Calendar.APRIL, n - MARCH_DAYS_NUMBER); /* April */
    } else {
      return new GregorianCalendar(year, Calendar.MARCH, n);  /* March */
    }
  }

  /**
   * Main program, when used as a standalone application
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      int thisYear = new GregorianCalendar().get(Calendar.YEAR);
      Calendar c = Easter.findHolyDay(thisYear);
      System.out.println(c.getTime());
    } else {
      for (String arg : args) {
        int year;
        try {
          year = Integer.parseInt(arg);
          System.out.println(Easter.findHolyDay(year).getTime());
        } catch (IllegalArgumentException e) {
          System.err.println("Year " + arg + " invalid (" + e.getMessage() + ").");
        }
      }
    }
  }
}
