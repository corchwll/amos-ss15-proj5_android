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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordingAlarmReceiver extends BroadcastReceiver
{
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		this.context = context;

		try
		{
			if(isNotificationRequired())
			{
				new AccountingNotification(context).createNotification();
			}
		} catch(SQLException e)
		{
			Log.e("RecordingAlarmReceiver", "sql exception");
		}
	}

	protected boolean isNotificationRequired() throws SQLException
	{
		boolean result;

		boolean existsRecord = existsRecordForToday();
		boolean isWeekday = true;

		if(!existsRecord)
		{
			isWeekday = isTodayAWeekday();
		}

		result = !existsRecord && isWeekday;
		return result;
	}

	protected boolean existsRecordForToday() throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		List<Session> sessions = DataAccessObjectFactory.getInstance()
														.createSessionsDAO(context)
														.listAllForDate(cal);

		return sessions.size() > 0;
	}

	protected boolean isTodayAWeekday()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		boolean isWeekday = (dayOfWeek != Calendar.SATURDAY) && (dayOfWeek != Calendar.SUNDAY);
		boolean isHoliday = isTodayAnHoliday(cal);

		return isWeekday && !isHoliday;
	}

	protected boolean isTodayAnHoliday(Calendar cal)
	{
		boolean result = false;

		List<Calendar> holidays = Holidays.getHolidaysForYear(cal.get(Calendar.YEAR));

		for(Calendar holiday : holidays)
		{
			int day = holiday.get(Calendar.DATE);
			int month = holiday.get(Calendar.MONTH);
			int year = holiday.get(Calendar.YEAR);

			if(day == cal.get(Calendar.DATE) && month == cal.get(Calendar.MONTH) && year == cal.get(Calendar.YEAR))
			{
				result = true;
				break;
			}
		}

		return result;
	}
}
