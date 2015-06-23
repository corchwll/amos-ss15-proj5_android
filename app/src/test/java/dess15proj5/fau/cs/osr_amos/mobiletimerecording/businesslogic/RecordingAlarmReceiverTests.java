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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordingAlarmReceiverTests
{
	@Test
	public void testIsTodayAWeekday_MondayChecked_TrueReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 22); //Monday

		RecordingAlarmReceiver rec = new RecordingAlarmReceiver();
		boolean result = rec.isTodayAWeekday(cal);

		assertTrue("result should be true, but was " + result, result);
	}

	@Test
	public void testIsTodayAWeekday_SaturdayChecked_FalseReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 20); //Saturday

		RecordingAlarmReceiver rec = new RecordingAlarmReceiver();
		boolean result = rec.isTodayAWeekday(cal);

		assertFalse("result should be false, but was " + result, result);
	}

	@Test
	public void testIsTodayAWeekday_EasterMondayChecked_FalseReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 3, 6); //Easter Monday

		RecordingAlarmReceiver rec = new RecordingAlarmReceiver();
		boolean result = rec.isTodayAWeekday(cal);

		assertFalse("result should be false, but was " + result, result);
	}

	@Test
	public void testIsTodayAnHoliday_EasterMondayChecked_TrueReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 3, 6); //Easter Monday

		RecordingAlarmReceiver rec = new RecordingAlarmReceiver();
		boolean result = rec.isTodayAnHoliday(cal);

		assertTrue("result should be true, but was " + result, result);
	}

	@Test
	public void testIsTodayAnHoliday_NoHolidayChecked_FalseReturned()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 22); //Monday

		RecordingAlarmReceiver rec = new RecordingAlarmReceiver();
		boolean result = rec.isTodayAnHoliday(cal);

		assertFalse("result should be false, but was " + result, result);
	}
}
