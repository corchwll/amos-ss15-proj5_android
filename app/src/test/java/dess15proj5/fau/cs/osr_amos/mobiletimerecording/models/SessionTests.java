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

public class SessionTests
{
	private static Session session;
	private static long id = 5L;
	private static String projectId = "12345";
	private static Date startTime;
	private static Date stopTime;

	@Before
	public void setUp()
	{
		startTime = new Date();
		stopTime = new Date();

		session = new Session();
		session.setId(id);
		session.setProjectId(projectId);
		session.setStartTime(startTime);
		session.setStopTime(stopTime);
	}

	@After
	public void tearDown()
	{
		startTime = null;
		stopTime = null;
		session = null;
	}

	@Test
	public void testGetId_MethodCalled_IDReturned()
	{
		long id = session.getId();
		assertTrue("id should be " + SessionTests.id + ", but was " + id, id == SessionTests.id);
	}

	@Test
	public void testSetId_IDGreaterZero_CorrectSet()
	{
		session.setId(10L);
		long id = session.getId();
		assertTrue("id should be " + 10L + ", but was " + id, id == 10L);
	}

	@Test
	public void testSetId_IDLowerZero_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			session.setId(-5L);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetProjectId_MethodCalled_ProjectIDReturned()
	{
		String id = session.getProjectId();
		assertTrue("id should be " + projectId + ", but was " + id, projectId.equals(id));
	}

	@Test
	public void testSetProjectId_IDGreaterZero_CorrectSet()
	{
		session.setProjectId("hallo");
		String id = session.getProjectId();
		assertTrue("id should be hallo, but was " + id, "hallo".equals(id));
	}

	@Test
	public void testSetProjectId_IDHasNotFiveDigits_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			session.setProjectId("hall");
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetStartTime_MethodCalled_StartTimeReturned()
	{
		Date date = session.getStartTime();
		assertTrue("date should be " + startTime + ", but was " + date, startTime.equals(date));
	}

	@Test
	public void testSetStartTime_CorrectStartTime_CorrectSet()
	{
		Date newDate = new Date();
		session.setStartTime(newDate);
		Date date = session.getStartTime();
		assertTrue("date should be " + newDate + ", but was " + date, newDate.equals(date));
	}

	@Test
	public void testSetStartTime_NullParameter_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			session.setStartTime(null);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetStopTime_MethodCalled_CorrectSet()
	{
		Date date = session.getStopTime();
		assertTrue("date should be " + stopTime + ", but was " + date, stopTime.equals(date));
	}

	@Test
	public void testSetStopTime_CorrectStopTime_CorrectSet()
	{
		Date newDate = new Date();
		session.setStopTime(newDate);
		Date date = session.getStopTime();
		assertTrue("date should be " + newDate + ", but was " + date, newDate.equals(date));
	}

	@Test
	public void testSetStopTime_NullParameter_AssertionThrown()
	{
		boolean wasThrown = false;
		try
		{
			session.setStopTime(null);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}
}
