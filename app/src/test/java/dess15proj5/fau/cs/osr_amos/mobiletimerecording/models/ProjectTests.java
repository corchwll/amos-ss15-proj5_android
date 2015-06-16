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
	private static Date finalDate;

	@Before
	public void setUp() throws Exception
	{
		finalDate = new Date();

		project = new Project();
		project.setId("test2");
		project.setName("testProject");
		project.setFinalDate(finalDate);
		project.setIsDisplayed(true);
		project.setIsUsed(true);
		project.setIsArchived(false);
	}

	@After
	public void tearDown() throws Exception
	{
		finalDate = null;
		project = null;
	}

	@Test
	public void testGetId_MethodCalled_testProjectReturned() throws Exception
	{
		String iD = project.getId();
		assertTrue("ID should be test2, but was " + iD, iD.equals("test2"));
	}

	@Test
	public void testSetId() throws Exception
	{
		project.setId("newID");
		String iD = project.getId();
		assertTrue("ID should be newID, but was " + iD, iD.equals("newID"));
	}

	@Test
	public void testGetName() throws Exception
	{
		String name = project.getName();
		assertTrue("Name should be testProject, but was " + name, name.equals("testProject"));
	}

	@Test
	public void testSetName() throws Exception
	{
		project.setName("newName");
		String name = project.getName();
		assertTrue("Name should be newName, but was " + name, name.equals("newName"));
	}

	@Test
	public void testGetFinalDate() throws Exception
	{
		Date date = project.getFinalDate();
		assertTrue("date should be " + finalDate + ", but was " + date, date.equals(finalDate));
	}

	@Test
	public void testSetFinalDate() throws Exception
	{
		Date newDate = new Date();
		project.setFinalDate(newDate);
		Date date = project.getFinalDate();
		assertTrue("date should be " + newDate + ", but was " + date, date.equals(newDate));
	}

	@Test
	public void testIsDisplayed() throws Exception
	{
		boolean isDisplayed = project.isDisplayed();
		assertTrue("isDisplayed should be true, but was " + isDisplayed, isDisplayed);
	}

	@Test
	public void testSetIsDisplayed() throws Exception
	{
		project.setIsDisplayed(false);
		boolean isDisplayed = project.isDisplayed();
		assertFalse("isDisplayed should be false, but was " + isDisplayed, isDisplayed);
	}

	@Test
	public void testIsUsed() throws Exception
	{
		boolean isUsed = project.isUsed();
		assertTrue("isUsed should be true, but was " + isUsed, isUsed);
	}

	@Test
	public void testSetIsUsed() throws Exception
	{
		project.setIsUsed(false);
		boolean isUsed = project.isUsed();
		assertFalse("isUsed should be false, but was " + isUsed, isUsed);
	}

	@Test
	public void testIsArchived() throws Exception
	{
		boolean isArchived = project.isArchived();
		assertFalse("isArchived should be false, but was " + isArchived, isArchived);
	}

	@Test
	public void testSetIsArchived() throws Exception
	{
		project.setIsArchived(true);
		boolean isArchived = project.isArchived();
		assertTrue("isArchived should be true, but was " + isArchived, isArchived);
	}

	@Test
	public void testToString() throws Exception
	{
		String result = project.toString();
		assertTrue("result should be 'test2 testProject', but was " + result,
				result.equals("test2 testProject"));
	}
}
