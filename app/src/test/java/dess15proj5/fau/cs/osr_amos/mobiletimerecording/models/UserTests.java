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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class UserTests
{
	private static User user;
	private static String employeeId = "12345";
	private static String lastName = "Mustermann";
	private static String firstName = "Max";
	private static int weeklyWorkingTime = 40;
	private static int totalVacationTime = 30;
	private static int currentVacationTime = 14;
	private static int currentOvertime = 203;
	private static Date registrationDate;

	@Before
	public void setUp()
	{
		registrationDate = new Date();

		user = new User();
		user.setEmployeeId(employeeId);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setWeeklyWorkingTime(weeklyWorkingTime);
		user.setTotalVacationTime(totalVacationTime);
		user.setCurrentVacationTime(currentVacationTime);
		user.setCurrentOvertime(currentOvertime);
		user.setRegistrationDate(registrationDate);
	}

	@After
	public void tearDown()
	{
		registrationDate = null;
		user = null;
	}

	@Test
	public void testGetEmployeeId_MethodCalled_EmployeeIDReturned()
	{
		String id = user.getEmployeeId();
		assertTrue("id should be " + employeeId + ", but was " + id, employeeId.equals(id));
	}

	@Test
	public void testSetEmployeeId_LengthEqualsFive_CorrectSet()
	{
		user.setEmployeeId("23455");
		String id = user.getEmployeeId();
		assertTrue("id should be 23455, but was " + id, "23455".equals(id));
	}

	@Test
	public void testSetEmployeeId_LengthNotEqualsFive_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			user.setEmployeeId("test");
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetLastName_MethodCalled_LastNameReturned()
	{
		String name = user.getLastName();
		assertTrue("id should be " + lastName + ", but was " + name, lastName.equals(name));
	}

	@Test
	public void testSetLastName_CorrectValue_CorrectSet()
	{
		user.setLastName("Otto");
		String name = user.getLastName();
		assertTrue("id should be Otto, but was " + name, "Otto".equals(name));
	}

	@Test
	public void testSetLastName_NullValue_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			user.setLastName(null);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetFirstName_MethodCalled_FirstNameReturned()
	{
		String name = user.getFirstName();
		assertTrue("id should be " + firstName + ", but was " + name, firstName.equals(name));
	}

	@Test
	public void testSetFirstName_CorrectValue_CorrectSet()
	{
		user.setFirstName("Karl");
		String name = user.getFirstName();
		assertTrue("id should be Karl, but was " + name, "Karl".equals(name));
	}

	@Test
	public void testSetFirstName_NullValue_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			user.setFirstName(null);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetWeeklyWorkingTime_MethodCalled_WeeklyWorkingTimeReturned()
	{
		int time = user.getWeeklyWorkingTime();
		assertTrue("time should be " + weeklyWorkingTime + ", but was " + time, time == weeklyWorkingTime);
	}

	@Test
	public void testSetWeeklyWorkingTime_TimeGreaterZero_CorrectSet()
	{
		user.setWeeklyWorkingTime(36);
		int time = user.getWeeklyWorkingTime();
		assertTrue("time should be 36, but was " + time, time == 36);
	}

	@Test
	public void testSetWeeklyWorkingTime_TimeLowerZero_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			user.setWeeklyWorkingTime(-4);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetTotalVacationTime_MethodCalled_TotalVacationTimeReturned()
	{
		int time = user.getTotalVacationTime();
		assertTrue("time should be " + totalVacationTime + ", but was " + time, time == totalVacationTime);
	}

	@Test
	public void testSetTotalVacationTime_TimeGreaterZero_CorrectSet()
	{
		user.setTotalVacationTime(20);
		int time = user.getTotalVacationTime();
		assertTrue("time should be 20, but was " + time, time == 20);
	}

	@Test
	public void testSetTotalVacationTime_TimeLowerZero_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			user.setTotalVacationTime(-4);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetCurrentVacationTime_MethodCalled_CurrentVacationTimeReturned()
	{
		int time = user.getCurrentVacationTime();
		assertTrue("time should be " + currentVacationTime + ", but was " + time, time == currentVacationTime);
	}

	@Test
	public void testSetCurrentVacationTime_TimeGreaterZero_CorrectSet()
	{
		user.setCurrentVacationTime(10);
		int time = user.getCurrentVacationTime();
		assertTrue("time should be 10, but was " + time, time == 10);
	}

	@Test
	public void testSetCurrentVacationTime_TimeLowerZero_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			user.setCurrentVacationTime(-4);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetCurrentOvertime_MethodCalled_CurrentOvertimeReturned()
	{
		int time = user.getCurrentOvertime();
		assertTrue("time should be " + currentOvertime + ", but was " + time, time == currentOvertime);
	}

	@Test
	public void testSetCurrentOvertime_MethodCalled_CorrectSet()
	{
		user.setCurrentOvertime(100);
		int time = user.getCurrentOvertime();
		assertTrue("time should be 100, but was " + time, time == 100);
	}

	@Test
	public void testGetRegistrationDate_MethodCalled_RegistrationDateReturned()
	{
		Date date = user.getRegistrationDate();
		assertTrue("date should be " + registrationDate + ", but was " + date, registrationDate.equals(date));
	}

	@Test
	public void testSetRegistrationDate_CorrectValue_CorrectSet()
	{
		Date date = new Date();
		user.setRegistrationDate(date);
		Date date2 = user.getRegistrationDate();
		assertTrue("date should be " + date + ", but was " + date2, date.equals(date));
	}

	@Test
	public void testSetRegistrationDate_NullValue_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			user.setRegistrationDate(null);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}
}
