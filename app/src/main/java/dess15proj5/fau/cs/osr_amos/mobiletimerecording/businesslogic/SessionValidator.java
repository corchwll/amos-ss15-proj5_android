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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SessionValidator
{
	private static SessionValidator instance;

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

	protected SessionValidator(Context context)
	{
		this.context = context;
	}

	public boolean isOverlapping(Session session) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(session.getStartTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		List<Session> sessions = loadSessionsForDate(cal);

		return checkOverlapping(session, sessions);
	}

	protected List<Session> loadSessionsForDate(Calendar cal) throws SQLException
	{
		List<Session> sessions = DataAccessObjectFactory.getInstance()
							   							.createSessionsDAO(context)
														.listAllForDate(cal);

		return sessions;
	}

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

	public void setContext(Context context)
	{
		this.context = context;
	}
}
