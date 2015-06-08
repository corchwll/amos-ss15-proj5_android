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

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CSVCreatorTests
{
	@Test
	public void testGetTimeInMinutesForDate_8HourSession_480MinutesReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 0, 1, 8, 0);
		Date start = cal.getTime();

		cal.set(2015, 0, 1, 16, 0);
		Date stop = cal.getTime();

		cal.set(2015, 0, 1, 0, 0);
		Date requestedDate = cal.getTime();

		Session one = new Session();
		one.setStartTime(start);
		one.setStopTime(stop);
		List<Session> sessions = new ArrayList<Session>();
		sessions.add(one);

		CSVCreator creator = new CSVCreator();
		int minutes = creator.getTimeInMinutesForDate(sessions, requestedDate);

		assertTrue("There should be 480 minutes returned, but were " + minutes + " minutes!", minutes == 480);
	}

	@Test
	public void testWriteDataHeader_5Projects_6Columns() throws UnsupportedEncodingException
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteArrayOutputStream);

		List<Project> projects = new ArrayList<>();
		Project p1 = new Project();
		p1.setId("p1234");
		Project p2 = new Project();
		p2.setId("p2345");
		Project p3 = new Project();
		p3.setId("p3456");
		Project p4 = new Project();
		p4.setId("p4567");
		Project p5 = new Project();
		p5.setId("p5678");
		projects.add(p1);
		projects.add(p2);
		projects.add(p3);
		projects.add(p4);
		projects.add(p5);

		CSVCreator creator = new CSVCreator();

		try
		{
			creator.writeDataHeader(out, projects);
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		String compare = new String("Date,p1234,p2345,p3456,p4567,p5678\n");
		String result = byteArrayOutputStream.toString();

		assertTrue("result should be '" + compare + "', but was '" + result + "'", compare.equals(result));
	}
}
