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
import android.content.SharedPreferences;
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
	{}

	/**
	 * Constructs a concrete DashboardInformation object.
	 *
	 * @param context the application context under which the object is constructed
	 * methodtype constructor
	 */
	public DashboardInformation(User currentUser, Context context)
	{
		this.currentUser = currentUser;
		this.context = context;
	}

	/**
	 * This method is used to get the overtime of the user according to his recorded sessions and his weekly working
	 * time.
	 *
	 * @return the overtime in hours
	 * @throws SQLException
	 * methodtype get method
	 */
	public int getOvertime() throws SQLException
	{
		List<Session> sessions = DataAccessObjectFactory.getInstance().createSessionsDAO(context).listAll();
		int overTimeInHours;

		if(sessions.size() != 0)
		{
			Date startTime = sessions.get(0)
									 .getStartTime();
			Date stopTime = new Date();

			int amountOfHolidays = Holidays.getHolidaysInbetween(startTime, stopTime);
			long amountOfWorkdays = calculateWorkdays(startTime, stopTime);
			long recordedTimeInMillis = sumUpSessions(sessions);

			double hoursPerDay = currentUser.getWeeklyWorkingTime()/5.0;
			long debtInMillis = (long)((amountOfWorkdays - amountOfHolidays)*hoursPerDay*60*60*1000);

			long overTimeInMillis = recordedTimeInMillis - debtInMillis;
			overTimeInHours = (int)(overTimeInMillis/(1000*60*60));
		} else
		{
			overTimeInHours = 0;
		}

		return currentUser.getCurrentOvertime() + overTimeInHours;
	}

	/**
	 * This method calculates the amount of workdays that exist inbetween the given interval.
	 *
	 * @param start the start date of the interval
	 * @param stop the stop date of the interval
	 * @return the amount of workdays
	 * methodtype helper method
	 * pre start != null && stop != null
	 * post correct amount of workdays calculated
	 */
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

	/**
	 * This method sums up the recorded time of the sessions in the list and returns the time in millis.
	 *
	 * @param sessions the list of sessions that should be summed up
	 * @return the recorded time from the sessions in millis
	 * methodtype helper method
	 */
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

	/**
	 * This method calculates the amount of vacation days left for the user based on the recorded sessions for the
	 * default project vacation in the database.
	 *
	 * @return the amount of left vacation days
	 * @throws SQLException
	 * methodtype get method
	 */
	public int getLeftVacationDays() throws SQLException
	{
		Date sinceDate = getLastResetDate();
		List<Session> vacationSessions = DataAccessObjectFactory.getInstance()
																.createSessionsDAO(context)
																.listAllForProjectSinceDate(
																		PersistenceHelper.VACATION_ID, sinceDate);
		long vacationInMillis = sumUpSessions(vacationSessions);

		double hoursPerDay = currentUser.getWeeklyWorkingTime()/5.0;
		int vacationInDays = (int)(vacationInMillis/(1000*60*60*hoursPerDay));

		int currentVacationTime = currentUser.getTotalVacationTime();
		if(sinceDate.equals(currentUser.getRegistrationDate()))
		{
			currentVacationTime = currentUser.getCurrentVacationTime();
		}

		return currentVacationTime - vacationInDays;
	}

	/**
	 * This method is used to get the last reset date so that it is possible to determine if the current vacation
	 * days of the user object have to be used in calculation or not.
	 *
	 * @return the last reset date
	 * methodtype helper method
	 */
	protected Date getLastResetDate()
	{
		SharedPreferences sharedPrefs = context.getSharedPreferences("vacation", Context.MODE_PRIVATE);
		Date resetDate = new Date(sharedPrefs.getLong("lastReset", currentUser.getRegistrationDate().getTime()));

		Calendar calReset = Calendar.getInstance();
		calReset.setTime(resetDate);

		Date result;
		if(resetDate.equals(currentUser.getRegistrationDate()))
		{
			result = resetDate;
			doResetNow(calReset, sharedPrefs);
		} else
		{
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);

			if(cal.get(Calendar.MONTH) >= Calendar.APRIL ||
					(cal.get(Calendar.MONTH) < Calendar.APRIL && cal.get(Calendar.YEAR) > calReset.get(Calendar.YEAR)))
			{
				if(resetDate.before(now))
				{
					cal = doResetNow(cal, sharedPrefs);
				}
			}

			result = cal.getTime();
		}

		return result;
	}

	/**
	 * This method is used to reset a given calendar object to the reset date for the new year.
	 *
	 * @param cal the calendar representation of the date that has to be resetted
	 * @param sharedPrefs the shared preferences where the reset has to be stored
	 * @return the reseted calendar object
	 * methodtype helper method
	 */
	protected Calendar doResetNow(Calendar cal, SharedPreferences sharedPrefs)
	{
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Calendar.APRIL);

		sharedPrefs.edit().putLong("lastReset", cal.getTimeInMillis()).apply();
		return cal;
	}
}
