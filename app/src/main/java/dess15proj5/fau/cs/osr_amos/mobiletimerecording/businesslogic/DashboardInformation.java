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

import android.content.Context;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.PersistenceHelper;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardInformation
{
	private User currentUser;
	private Context context;

	public DashboardInformation()
	{
	}

	public DashboardInformation(User currentUser, Context context)
	{
		this.currentUser = currentUser;
		this.context = context;
	}

	public int getOvertime() throws SQLException
	{
		List<Session> sessions = DataAccessObjectFactory.getInstance().createSessionsDAO(context).listAll();
		Date startTime = sessions.get(0).getStartTime();
		Date stopTime = sessions.get(sessions.size() - 1).getStopTime();

		int amountOfHolidays = Holidays.getHolidaysInbetween(startTime, stopTime);
		long amountOfWorkdays = calculateWorkdays(startTime, stopTime);
		long recordedTimeInMillis = sumUpSessions(sessions);

		double hoursPerDay = currentUser.getWeeklyWorkingTime() / 5.0;
		long debtInMillis = (long)((amountOfWorkdays - amountOfHolidays)*hoursPerDay*60*60*1000);

		long overTimeInMillis = recordedTimeInMillis - debtInMillis;
		int overTimeInHours = (int)(overTimeInMillis/(1000*60*60));

		return currentUser.getCurrentOvertime() + overTimeInHours;
	}

	protected long calculateWorkdays(Date start, Date stop)
	{
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(start);
		//backup on which weekday the intervall started
		int startWeekday = startCal.get(Calendar.DAY_OF_WEEK);
		//start interval on mondays
		startCal.add(Calendar.DAY_OF_WEEK, -startWeekday);

		Calendar stopCal = Calendar.getInstance();
		stopCal.setTime(stop);
		//backup on which weekday the intervall stopped
		int stopWeekday = stopCal.get(Calendar.DAY_OF_WEEK);
		//end interval on mondays
		stopCal.add(Calendar.DAY_OF_WEEK, -stopWeekday);

		//calc
		long days = (stopCal.getTimeInMillis() - startCal.getTimeInMillis())/(1000*60*60*24);
		long workDays = days*5/7;

		if(startWeekday == Calendar.SUNDAY)
		{
			startWeekday = Calendar.MONDAY;
		}

		if(stopWeekday == Calendar.SUNDAY)
		{
			stopWeekday = Calendar.MONDAY;
		}

		return workDays - startWeekday + stopWeekday + 1;
	}

	protected long sumUpSessions(List<Session> sessions)
	{
		long recordedTimeInMillis = 0L;

		for(Session session : sessions)
		{
			Date start = session.getStartTime();
			Date stop = session.getStopTime();

			recordedTimeInMillis += stop.getTime() - start.getTime();
		}

		return recordedTimeInMillis;
	}

	public int getLeftVacationDays() throws SQLException
	{
		List<Session> vacationSessions = DataAccessObjectFactory.getInstance().createSessionsDAO(context)
																.listAllForProject(PersistenceHelper.VACATION_ID);
		long vacationInMillis = sumUpSessions(vacationSessions);

		double hoursPerDay = currentUser.getWeeklyWorkingTime()/5.0;
		int vacationInDays = (int)(vacationInMillis/(1000*60*60*hoursPerDay));

		return currentUser.getCurrentVacationTime() - vacationInDays;
	}
}
