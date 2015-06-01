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
	/**
	 * This method is used to get the amound of holidays in a given time intervall. This method only counts the
	 * holidays that are on a weekday.
	 *
	 * @param start the lower bound of the time intervall
	 * @param stop the upper bound of the time intervall
	 * @return the amount of holidays on weekdays in the given time intervall as int
	 * methodtype get method
	 * pre start before stop
	 * post correct amount will be calculated
	 */
	public static int getHolidaysInbetween(Date start, Date stop)
	{
		Calendar startCal = GregorianCalendar.getInstance();
		startCal.setTime(start);
		Calendar stopCal = GregorianCalendar.getInstance();
		stopCal.setTime(stop);

		int amountOfHolidays = 0;
		if(startCal.get(Calendar.YEAR) == stopCal.get(Calendar.YEAR))
		{
			List<Calendar> holidays = getHolidaysForYear(startCal.get(Calendar.YEAR));
			amountOfHolidays += amountOfHolidaysBetween(holidays, startCal, stopCal);
		} else if(stopCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR) == 1)
		{
			List<Calendar> holidaysInStartYear = getHolidaysForYear(startCal.get(Calendar.YEAR));
			List<Calendar> holidaysInStopYear = getHolidaysForYear(stopCal.get(Calendar.YEAR));

			amountOfHolidays += amountOfHolidaysSince(holidaysInStartYear, startCal);
			amountOfHolidays += amountOfHolidaysUntil(holidaysInStopYear, stopCal);
		} else if(stopCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR) > 1)
		{
			List<Calendar> holidaysInStartYear = getHolidaysForYear(startCal.get(Calendar.YEAR));
			List<Calendar> holidaysInStopYear = getHolidaysForYear(stopCal.get(Calendar.YEAR));

			amountOfHolidays += amountOfHolidaysSince(holidaysInStartYear, startCal);
			amountOfHolidays += amountOfHolidaysUntil(holidaysInStopYear, stopCal);

			for(int i = startCal.get(Calendar.YEAR) + 1; i < stopCal.get(Calendar.YEAR); i++)
			{
				amountOfHolidays += getHolidaysForYear(i).size();
			}
		}

		return amountOfHolidays;
	}

	/**
	 * This method is used to get all holidays on weekdays for a given year.
	 *
	 * @param year the year for which the holidays should be calculated
	 * @return a List containing all holidays on weekdays for the requested year
	 * methodtype get method
	 */
	protected static List<Calendar> getHolidaysForYear(int year)
	{
		List<Calendar> holidays = getFixedHolidays(year);
		Calendar easterSunday = getEaster(year);
		Calendar tmp;

		//Karfreitag
		easterSunday.add(Calendar.DAY_OF_MONTH, -2);
		tmp = GregorianCalendar.getInstance();
		tmp.setTime(easterSunday.getTime());
		holidays.add(tmp);

		//Ostermontag
		easterSunday.add(Calendar.DAY_OF_MONTH, 3);
		tmp = GregorianCalendar.getInstance();
		tmp.setTime(easterSunday.getTime());
		holidays.add(tmp);

		//Christi Himmelfahrt
		easterSunday.add(Calendar.DAY_OF_MONTH, 38);
		tmp = GregorianCalendar.getInstance();
		tmp.setTime(easterSunday.getTime());
		holidays.add(tmp);

		//Pfingstmontag
		easterSunday.add(Calendar.DAY_OF_MONTH, 11);
		tmp = GregorianCalendar.getInstance();
		tmp.setTime(easterSunday.getTime());
		holidays.add(tmp);

		//Fronleichnam
		easterSunday.add(Calendar.DAY_OF_MONTH, 10);
		tmp = GregorianCalendar.getInstance();
		tmp.setTime(easterSunday.getTime());
		holidays.add(tmp);

		return holidays;
	}

	/**
	 * This method returns all holidays that are not depending on easter and are on a weekday for the requested year.
	 *
	 * @param year the year for which the fixed holidays should be calculated
	 * @return a list containing all fixed holidays on weekdays for this year
	 * methodtype get method
	 */
	protected static List<Calendar> getFixedHolidays(int year)
	{
		List<Calendar> fixedHolidays = new ArrayList<>();

		Calendar cal;
		cal = new GregorianCalendar(year, Calendar.JANUARY, 1); //Neujahrstag
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}
		cal = new GregorianCalendar(year, Calendar.JANUARY, 6); //Heilige Drei Koenige
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}
		cal = new GregorianCalendar(year, Calendar.MAY, 1); //Tag der Arbeit
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}
		cal = new GregorianCalendar(year, Calendar.AUGUST, 15); //Maria Himmelfahrt
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}
		cal = new GregorianCalendar(year, Calendar.OCTOBER, 3); //Tag der deutschen Einheit
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}
		cal = new GregorianCalendar(year, Calendar.NOVEMBER, 1); //Allerheiligen
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}
		cal = new GregorianCalendar(year, Calendar.DECEMBER, 25); //1. Weihnachtstag
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}
		cal = new GregorianCalendar(year, Calendar.DECEMBER, 26); //2. Weihnachtstag
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			fixedHolidays.add(cal);
		}

		return fixedHolidays;
	}

	/**
	 * This method calculates the date for easter sunday based on the gaussian easterformula.
	 *
	 * @param year the year for which easter sunday should be calculated
	 * @return the date of easter sunday
	 * methodtype get method
	 * pre year > 1970 due to the easter formula
	 * post date of easter sunday will be calculated correctly
	 */
	protected static Calendar getEaster(int year)
	{
		//Osterformel
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

		int month = Calendar.MARCH;
		if(o > 31)
		{
			month++;
		}

		int day = o % 31;

		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year, month, day);
		return cal;
	}

	/**
	 * This method checks how many of the holidays will be after the start date and before the stop date.
	 *
	 * @param holidays the holidays that should be checked
	 * @param startCal the date after which the holidays have to be
	 * @param stopCal the date before which the holidays have to be
	 * @return the amount of holidays inbetween the given dates
	 * methodtype helper method
	 * pre startCal != null && stopCal != null
	 * post correct amount will be returned
	 */
	protected static int amountOfHolidaysBetween(List<Calendar> holidays, Calendar startCal, Calendar stopCal)
	{
		int result = 0;
		for(Calendar cal : holidays)
		{
			if(startCal.before(cal) && stopCal.after(cal))
			{
				result++;
			}
		}

		return result;
	}

	/**
	 * This method checks how many of the holidays will be after the given date.
	 *
	 * @param holidays the holidays that should be checked
	 * @param startCal the date after which the holidays have to be
	 * @return the amount of holidays after the given date
	 * methodtype helper method
	 * pre startCal != null
	 * post correct amount will be returned
	 */
	protected static int amountOfHolidaysSince(List<Calendar> holidays, Calendar startCal)
	{
		int result = 0;
		for(Calendar cal : holidays)
		{
			if(startCal.before(cal))
			{
				result++;
			}
		}

		return result;
	}

	/**
	 * This method checks how many of the holidays will be before the given date.
	 *
	 * @param holidays the holidays that should be checked
	 * @param stopCal the date before which the holidays have to be
	 * @return the amount of holidays before the given date
	 * methodtype helper method
	 * pre stopCal != null
	 * post correct amount will be returned
	 */
	protected static int amountOfHolidaysUntil(List<Calendar> holidays, Calendar stopCal)
	{
		int result = 0;
		for(Calendar cal : holidays)
		{
			if(cal.before(stopCal))
			{
				result++;
			}
		}

		return result;
	}
}
