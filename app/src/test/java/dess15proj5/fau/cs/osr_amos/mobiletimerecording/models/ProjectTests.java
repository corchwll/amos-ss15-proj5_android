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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProjectTests
{
	private static Project project;
	private static String projectID = "test2";
	private static String projectName = "testProject";
	private static Date finalDate;
	private static boolean isDisplayed = true;
	private static boolean isUsed = true;
	private static boolean isArchived = false;
	private static String projectAsString = projectID + " " + projectName;


	@Before
	public void setUp() throws Exception
	{
		finalDate = new Date();

		project = new Project();
		project.setId(projectID);
		project.setName(projectName);
		project.setFinalDate(finalDate);
		project.setIsDisplayed(isDisplayed);
		project.setIsUsed(isUsed);
		project.setIsArchived(isArchived);
	}

	@After
	public void tearDown() throws Exception
	{
		finalDate = null;
		project = null;
	}

	@Test
	public void testGetId_MethodCalled_test2Returned() throws Exception
	{
		String iD = project.getId();
		assertTrue("ID should be " + projectID + ", but was " + iD, iD.equals(projectID));
	}

	@Test
	public void testSetId_MethodCalled_CorrectSet() throws Exception
	{
		project.setId("newID");
		String iD = project.getId();
		assertTrue("ID should be newID, but was " + iD, iD.equals("newID"));
	}

	@Test
	public void testSetId_IdLessThan5Digits_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			project.setId("test");
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testSetId_IdGreaterThan5Digits_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			project.setId("testtest");
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetName_MethodCalled_testProjectReturned() throws Exception
	{
		String name = project.getName();
		assertTrue("Name should be " + projectName + ", but was " + name, name.equals(projectName));
	}

	@Test
	public void testSetName_MethodCalled_CorrectSet() throws Exception
	{
		project.setName("newName");
		String name = project.getName();
		assertTrue("Name should be newName, but was " + name, name.equals("newName"));
	}

	@Test
	public void testSetName_NameIsNull_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			project.setName(null);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetFinalDate_MethodCalled_FinalDateReturned() throws Exception
	{
		Date date = project.getFinalDate();
		assertTrue("date should be " + finalDate + ", but was " + date, date.equals(finalDate));
	}

	@Test
	public void testSetFinalDate_MethodCalled_CorrectSet() throws Exception
	{
		Date newDate = new Date();
		project.setFinalDate(newDate);
		Date date = project.getFinalDate();
		assertTrue("date should be " + newDate + ", but was " + date, date.equals(newDate));
	}

	@Test
	public void testSetFinalDate_DateIsNull_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			project.setFinalDate(null);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testIsDisplayed_MethodCalled_TrueReturned() throws Exception
	{
		boolean isDisplayed = project.isDisplayed();
		assertTrue("isDisplayed should be " + ProjectTests.isDisplayed + ", but was " + isDisplayed, isDisplayed);
	}

	@Test
	public void testSetIsDisplayed_MethodCalled_CorrectSet() throws Exception
	{
		project.setIsDisplayed(false);
		boolean isDisplayed = project.isDisplayed();
		assertFalse("isDisplayed should be false, but was " + isDisplayed, isDisplayed);
	}

	@Test
	public void testIsUsed_MethodCalled_TrueReturned() throws Exception
	{
		boolean isUsed = project.isUsed();
		assertTrue("isUsed should be " + ProjectTests.isUsed + ", but was " + isUsed, isUsed);
	}

	@Test
	public void testSetIsUsed_MethodCalled_CorrectSet() throws Exception
	{
		project.setIsUsed(false);
		boolean isUsed = project.isUsed();
		assertFalse("isUsed should be false, but was " + isUsed, isUsed);
	}

	@Test
	public void testIsArchived_MethodCalled_FalseReturned() throws Exception
	{
		boolean isArchived = project.isArchived();
		assertFalse("isArchived should be " + ProjectTests.isArchived + ", but was " + isArchived, isArchived);
	}

	@Test
	public void testSetIsArchived_MethodCalled_CorrectSet() throws Exception
	{
		project.setIsArchived(true);
		boolean isArchived = project.isArchived();
		assertTrue("isArchived should be true, but was " + isArchived, isArchived);
	}

	@Test
	public void testGetGPSPoint_MethodCalled_CorrectReturned() throws Exception
	{
		double lat = 30.445;
		double lon = 13.34;
		GPSPoint point = new GPSPoint(lat, lon);
		project.setGPSPoint(point);
		GPSPoint result = project.getGPSPoint();

		assertTrue("GPSPoint should be at " + lat + " - " + lon + ", but was at " + result.getLatitude() + " - " +
				result.getLongitude(), lat == result.getLatitude() && lon == result.getLongitude());
	}

	@Test
	public void testSetGPSPoint_MethodCalled_CorrectSet() throws Exception
	{
		double lat = 30.445;
		double lon = 13.34;
		GPSPoint point = new GPSPoint(lat, lon);
		project.setGPSPoint(point);
		GPSPoint result = project.getGPSPoint();

		assertTrue("GPSPoint should be at " + lat + " - " + lon + ", but was at " + result.getLatitude() + " - " +
				result.getLongitude(), lat == result.getLatitude() && lon == result.getLongitude());
	}

	@Test
	public void testToString_MethodCalled_CorrectRepresentationReturned() throws Exception
	{
		String result = project.toString();
		assertTrue("result should be '" + projectAsString + "', but was " + result, result.equals(projectAsString));
	}
}
