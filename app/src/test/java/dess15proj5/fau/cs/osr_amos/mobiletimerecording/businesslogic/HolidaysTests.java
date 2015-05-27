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

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HolidaysTests
{
	@Test
	public void testGetHolidaysInbetween_ValidTimeInterval_CorrectAmountReturned()
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(2015, 12, 1);
		Calendar cal2 = GregorianCalendar.getInstance();
		cal.set(2015, 12, 30);

		int holidayCount = Holidays.getHolidaysInbetween(cal.getTime(), cal2.getTime());

		assertTrue("amount of holidays should be 2, but was " + holidayCount, holidayCount == 2);
	}

	@Test
	public void testGetEaster_ForYear2015_20150405()
	{
		Calendar cal = Holidays.getEaster(2015);

		assertTrue("Easter should be at April 5th, 2015. But was " + cal.get(Calendar.MONTH) + " " +
				cal.get(Calendar.DATE) + ", " +	cal.get(Calendar.YEAR),
				cal.get(Calendar.YEAR) == 2015 && cal.get(Calendar.MONTH) == Calendar.APRIL
						&& cal.get(Calendar.DATE) == 5);
	}

	@Test
	public void testGetFixedHolidays_ForYear2015_ContainsAllFourHolidays()
	{
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 1); //Neujahrstag
		Calendar cal2 = new GregorianCalendar(2015, Calendar.JANUARY, 6); //Heilige Drei Koenige
		Calendar cal3 = new GregorianCalendar(2015, Calendar.MAY, 1); //Tag der Arbeit
		Calendar cal4 = new GregorianCalendar(2015, Calendar.DECEMBER, 25); //1. Weihnachtstag

		List<Calendar> fixedHolidays = Holidays.getFixedHolidays(2015);

		assertTrue("There should be exact 4 holidays, there were " + fixedHolidays.size(),
				fixedHolidays.size() == 4 && fixedHolidays.contains(cal) && fixedHolidays.contains(cal2) &&
						fixedHolidays.contains(cal3) && fixedHolidays.contains(cal4));
	}
}
