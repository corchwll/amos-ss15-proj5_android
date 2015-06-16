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
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SessionValidator
{
	private static SessionValidator instance;

	/**
	 * This method returns an instance of SessionValidator to fulfill the singleton pattern. If there wasn't
	 * initialized an instance yet, it will be constructed.
	 *
	 * @param context the context under which the instance should be
	 * @return the singleton instance of the DataAccessObjectFactory
	 * methodtype get method
	 */
	public static SessionValidator getInstance(Context context)
	{
		if(instance == null)
		{
			instance = new SessionValidator(context);
		} else
		{
			instance.setContext(context);
		}

		return instance;
	}

	private Context context;

	/**
	 * This constructor is only used by the singleton implementation.
	 *
	 * @param context the application context under which this object is created
	 * methodtype constructor
	 */
	protected SessionValidator(Context context)
	{
		this.context = context;
	}

	/**
	 * This method is used to check whether a session overlaps with sessions recorded previously.
	 *
	 * @param session the new session that is not allowed to overlap
	 * @return true if is overlapping, false if not
	 * @throws SQLException in case of database error
	 * methodtype boolean query method
	 */
	public boolean isOverlapping(Session session) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(session.getStartTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		List<Session> sessions = DataAccessObjectFactory.getInstance()
														.createSessionsDAO(context)
														.listAllForDate(cal);

		return checkOverlapping(session, sessions);
	}

	/**
	 * This method is used to check whether there is a session in the list that overlaps with the current one.
	 *
	 * @param session the session that should not overlap
	 * @param sessions the sessions that have to be checked
	 * @return true if is overlapping, false if not
	 * methodtype boolean query method
	 */
	protected boolean checkOverlapping(Session session, List<Session> sessions)
	{
		boolean result = false;

		Date startTime = session.getStartTime();
		Date stopTime = session.getStopTime();
		for(Session s : sessions)
		{
			Date currentStartTime = s.getStartTime();
			Date currentStopTime = s.getStopTime();

			if((startTime.after(currentStartTime) && startTime.before(currentStopTime))
					|| (stopTime.after(currentStartTime) && stopTime.before(currentStopTime)))
			{
				result = true;
				break;
			}
		}

		return result;
	}

	public Session cutWorkingTime(Session session) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(session.getStartTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		List<Session> sessions = DataAccessObjectFactory.getInstance()
														.createSessionsDAO(context)
														.listAllForDate(cal);

		long leftTime = calculateLeftTime(sessions);

		if(leftTime < (session.getStopTime().getTime() - session.getStartTime().getTime()))
		{
			session.setStopTime(new Date(session.getStartTime().getTime() + leftTime));
		}

		return session;
	}

	protected long calculateLeftTime(List<Session> sessions)
	{
		long tenHours = 1000L*60L*60L*10L;

		long currentTime = 0;
		for(Session s : sessions)
		{
			currentTime += s.getStopTime().getTime() - s.getStartTime().getTime();
		}

		long result;
		if(currentTime >= tenHours)
		{
			result = 0L;
		} else
		{
			result = tenHours - currentTime;
		}

		return result;
	}

	/**
	 * Sets the context under which this object should be.
	 *
	 * @param context the context under which this object should be
	 * methodtype set method
	 */
	public void setContext(Context context)
	{
		this.context = context;
	}
}
