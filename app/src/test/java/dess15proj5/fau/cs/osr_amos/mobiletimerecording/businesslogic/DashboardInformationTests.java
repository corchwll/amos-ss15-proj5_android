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

import static org.junit.Assert.assertTrue;

public class DashboardInformationTests
{
	@Test
	public void testCalculateWorkdays_OneWeek_FiveWorkdaysReturned()
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(2015, Calendar.DECEMBER, 1);
		Calendar cal2 = GregorianCalendar.getInstance();
		cal2.set(2015, Calendar.DECEMBER, 7);

		DashboardInformation db = new DashboardInformation();
		long workdays = db.calculateWorkdays(cal.getTime(), cal2.getTime());

		assertTrue("Amount of workdays should be 5, but was " + workdays, workdays == 5);
	}

	@Test
	public void testCalculateWorkdays_TwoWeeks_TenWorkdaysReturned()
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(2015, Calendar.DECEMBER, 1);
		Calendar cal2 = GregorianCalendar.getInstance();
		cal2.set(2015, Calendar.DECEMBER, 14);

		DashboardInformation db = new DashboardInformation();
		long workdays = db.calculateWorkdays(cal.getTime(), cal2.getTime());

		assertTrue("Amount of workdays should be 10, but was " + workdays, workdays == 10);
	}

	@Test
	public void testCalculateWorkdays_2015_261WorkdaysReturned()
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(2015, Calendar.JANUARY, 1);
		Calendar cal2 = GregorianCalendar.getInstance();
		cal2.set(2015, Calendar.DECEMBER, 31);

		DashboardInformation db = new DashboardInformation();
		long workdays = db.calculateWorkdays(cal.getTime(), cal2.getTime());

		assertTrue("Amount of workdays should be 281, but was " + workdays, workdays == 261);
	}
}