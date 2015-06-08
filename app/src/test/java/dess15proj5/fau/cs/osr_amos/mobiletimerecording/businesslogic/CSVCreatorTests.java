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
import org.junit.Test;

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
}
