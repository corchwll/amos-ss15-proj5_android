/*
 *     Mobile Time Accounting
 *     Copyright (C) 2015
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic;

import java.util.*;

public class Holidays
{
	public static int getHolidaysInbetween(Date start, Date stop)
	{
		Calendar startCal = GregorianCalendar.getInstance();
		startCal.setTime(start);
		Calendar stopCal = GregorianCalendar.getInstance();
		stopCal.setTime(stop);

		int amountOfHolidays = 0;
		if(startCal.YEAR == stopCal.YEAR)
		{
			List<Calendar> holidays = getHolidaysForYear(startCal.YEAR);
			amountOfHolidays += amountOfHolidaysSince(holidays, startCal);
		} else
		{
			List<Calendar> holidaysInStartYear = getHolidaysForYear(startCal.YEAR);
			List<Calendar> holidaysInStopYear = getHolidaysForYear(stopCal.YEAR);

			amountOfHolidays += amountOfHolidaysSince(holidaysInStartYear, startCal);
			amountOfHolidays += amountOfHolidaysUntil(holidaysInStopYear, stopCal);
		}

		return amountOfHolidays;
	}

	private static List<Calendar> getHolidaysForYear(int year)
	{
		List<Calendar> holidays = getFixedHolidays(year);

		return null;
	}

	private static List<Calendar> getFixedHolidays(int year)
	{
		List<Calendar> fixedHolidays = new ArrayList<>();

		fixedHolidays.add(new GregorianCalendar(year, 1, 1)); //Neujahrstag
		fixedHolidays.add(new GregorianCalendar(year, 1, 6)); //Heilige Drei Koenige
		fixedHolidays.add(new GregorianCalendar(year, 5, 1)); //Tag der Arbeit
		fixedHolidays.add(new GregorianCalendar(year, 8, 15)); //Maria Himmelfahrt
		fixedHolidays.add(new GregorianCalendar(year, 10, 3)); //Tag der deutschen Einheit
		fixedHolidays.add(new GregorianCalendar(year, 11, 1)); //Allerheiligen
		fixedHolidays.add(new GregorianCalendar(year, 12, 25)); //1. Weihnachtstag
		fixedHolidays.add(new GregorianCalendar(year, 12, 26)); //2. Weihnachtstag

		return fixedHolidays;
	}

	private static Calendar getEaster(int year)
	{
		int a = year % 19;
		int b = year % 4;
		int c = year % 7;
		int k = year/100;
		int p = (8*k + 13)/25;
		int q = k/4;
		int M = (15 + k - p -q) % 30;
		int d = (19*a + M) % 30;
		int N = (4 + k - q) % 7;
		int e = (2*b + 4*c + 6*d + N) % 7;
		int o = 22 + d + e;

		int month = 3;
		if(o > 31)
		{
			month++;
		}

		int day = o % 31;

		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year, month, day);
		return cal;
	}

	private static int amountOfHolidaysSince(List<Calendar> holidays, Calendar startCal)
	{
		return 0;
	}

	private static int amountOfHolidaysUntil(List<Calendar> holidaysInStopYear, Calendar stopCal)
	{
		return 0;
	}
}
