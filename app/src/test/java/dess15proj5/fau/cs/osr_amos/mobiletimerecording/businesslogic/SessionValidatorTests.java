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

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.testUtility.TestContext;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SessionValidatorTests
{
	private static final long ONE_HOUR = 3600000L;

	private static List<Session> sessions = new ArrayList<>();
	private static SessionValidator validator;

	@Before
	public void setUp()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 15, 8, 0, 0);
		Date date1 = cal.getTime();
		Date date2 = new Date(date1.getTime() + ONE_HOUR);
		Date date3 = new Date(date2.getTime() + ONE_HOUR);
		Date date4 = new Date(date3.getTime() + 3*ONE_HOUR);
		Date date5 = new Date(date4.getTime() + 3*ONE_HOUR);

		sessions.add(new Session(0L, "test", date1, date2));
		sessions.add(new Session(1L, "test", date2, date3));
		sessions.add(new Session(2L, "test", date4, date5));

		validator = new SessionValidator(new TestContext());
	}

	@Test
	public void testCheckOverlapping_NoOverlapping_FalseReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 15, 11, 0, 0);

		Session session = new Session(4L, "test", cal.getTime(), new Date(cal.getTime().getTime() + ONE_HOUR));

		boolean result = validator.checkOverlapping(session, sessions);

		assertFalse("result has to be false, but was " + result, result);
	}

	@Test
	public void testCheckOverlapping_OverlappingStartTime_TrueReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 15, 8, 30, 0);

		Session session = new Session(4L, "test", cal.getTime(), new Date(cal.getTime().getTime() + ONE_HOUR));

		boolean result = validator.checkOverlapping(session, sessions);

		assertTrue("result has to be true, but was " + result, result);
	}

	@Test
	public void testCheckOverlapping_OverlappingStopTime_TrueReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 15, 11, 00, 0);

		Session session = new Session(4L, "test", cal.getTime(), new Date(cal.getTime().getTime() + 3*ONE_HOUR));

		boolean result = validator.checkOverlapping(session, sessions);

		assertTrue("result has to be true, but was " + result, result);
	}

	@Test
	public void testCalculateLeftTime_5HoursWorked_5HoursInMillisReturned()
	{
		long leftTime = validator.calculateLeftTime(sessions);

		assertTrue("result has to be 5 hours in millis, but was " + leftTime, leftTime == (1000L*60L*60L*5L));
	}

	@Test
	public void testCalculateLeftTime_10HoursWorked_0HoursInMillisReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 15, 17, 0, 0);
		sessions.add(new Session(4L, "test", cal.getTime(), new Date(cal.getTime().getTime() + 5*ONE_HOUR)));

		long leftTime = validator.calculateLeftTime(sessions);

		assertTrue("result has to be 0 hours in millis, but was " + leftTime, leftTime == 0L);
	}
}
