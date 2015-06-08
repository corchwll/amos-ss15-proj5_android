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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionsDAOImpl extends AbstractDAO implements SessionsDAO
{
	//an array of strings, containing all column names of the sessions table
	private String[] allColumns = {PersistenceHelper.SESSIONS_ID, PersistenceHelper.SESSIONS_PROJECT_ID,
			PersistenceHelper.SESSIONS_TIMESTAMP_START, PersistenceHelper.SESSIONS_TIMESTAMP_STOP};

	/**
	 * Constructs a concrete SessionsDAO object.
	 *
	 * @param context the application context under which the object is constructed
	 * methodtype constructor
	 */
	public SessionsDAOImpl(Context context)
	{
		persistenceHelper = new PersistenceHelper(context);
	}

	/**
	 * This method inserts the given information into the sessions table and creates an object of type session.
	 *
	 * @param projectId the project id the required session belongs to
	 * @param startTime the time when the new session started
	 * @return the required session object is returned
	 * methodtype conversion method (since the given information is converted into an object of type session)
	 */
	@Override
	public Session create(String projectId, Date startTime)
	{
		return create(projectId, startTime, startTime);
	}

	/**
	 * This method inserts the given information into the sessions table and creates an object of type session.
	 *
	 * @param projectId the project id the required session belongs to
	 * @param startTime the time when the new session started
	 * @param stopTime the time when the new session terminated
	 * @return the required session object is returned
	 * methodtype conversion method (since the given information is converted into an object of type session)
	 */
	@Override
	public Session create(String projectId, Date startTime, Date stopTime)
	{
		//preparation and insert of the new session
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.SESSIONS_PROJECT_ID, projectId);
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_START, startTime.getTime());
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_STOP, stopTime.getTime());
		long insertId = database.insert(PersistenceHelper.TABLE_SESSIONS, null, values);

		//retrieving the new session from database and constructing the object
		Cursor cursor = database.query(PersistenceHelper.TABLE_SESSIONS, allColumns, PersistenceHelper.SESSIONS_ID +
				" = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Session newSession = cursorToSession(cursor);
		cursor.close();
		return newSession;
	}

	/**
	 * This method is used to update a given session in the database.
	 *
	 * @param session the session which has to be updated.
	 * methodtype command method
	 */
	@Override
	public void update(Session session)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.SESSIONS_ID, session.getId());
		values.put(PersistenceHelper.SESSIONS_PROJECT_ID, session.getProjectId());
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_START, session.getStartTime()
																	  .getTime());
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_STOP, session.getStopTime()
																	 .getTime());

		database.update(PersistenceHelper.TABLE_SESSIONS, values,
				PersistenceHelper.SESSIONS_ID + " = " + session.getId(), null);
	}

	/**
	 * This method loads the session with the given id from the database.
	 *
	 * @param sessionId the id of the session that should be loaded from database
	 * @return the session matching the given id
	 * methodtype query method
	 */
	@Override
	public Session load(long sessionId)
	{
		Cursor cursor = database.query(PersistenceHelper.TABLE_SESSIONS, allColumns, PersistenceHelper.SESSIONS_ID +
				" = " + sessionId, null, null, null, null);
		cursor.moveToFirst();
		Session session = cursorToSession(cursor);
		cursor.close();
		return session;
	}

	/**
	 * This method deletes the session with the given id from the database.
	 *
	 * @param sessionId the id of the session that should be deleted
	 * methodtype command method
	 */
	@Override
	public void delete(long sessionId)
	{
		database.delete(PersistenceHelper.TABLE_SESSIONS, PersistenceHelper.SESSIONS_ID + " = " + sessionId, null);
	}

	/**
	 * This method loads all sessions from the database that are belonging to the given projectId.
	 *
	 * @param projectID the id the sessions have to belong to
	 * @return a list containing all sessions for the given projectId
	 * methodtype query method
	 */
	@Override
	public List<Session> listAllForProject(String projectID)
	{
		List<Session> sessions = new ArrayList<>();

		String query = "SELECT * FROM " + PersistenceHelper.TABLE_SESSIONS + " s INNER JOIN " +
				PersistenceHelper.TABLE_PROJECTS + " p ON s." + PersistenceHelper.SESSIONS_PROJECT_ID + " = p." +
				PersistenceHelper.PROJECTS_ID + " WHERE p." + PersistenceHelper.PROJECTS_ID + " = ? ORDER BY "
				+ PersistenceHelper.SESSIONS_TIMESTAMP_START + " DESC;";

		Cursor cursor = database.rawQuery(query, new String[]{projectID});

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Session session = cursorToSession(cursor);
			sessions.add(session);
			cursor.moveToNext();
		}

		cursor.close();
		return sessions;
	}

	/**
	 * This method loads all sessions from the database that are belonging to the given projectID and happened since the
	 * given date ordered by startTime.
	 *
	 * @param projectID the id the sessions have to belong to
	 * @param date the date since which the sessions have to be recoreded
	 * @return a list containing all sessions for the given projectID
	 * methodtype query method
	 */
	@Override
	public List<Session> listAllForProjectSinceDate(String projectID, Date date)
	{
		List<Session> sessions = new ArrayList<>();

		String query = "SELECT * FROM " + PersistenceHelper.TABLE_SESSIONS + " s INNER JOIN " +
				PersistenceHelper.TABLE_PROJECTS + " p ON s." + PersistenceHelper.SESSIONS_PROJECT_ID + " = p." +
				PersistenceHelper.PROJECTS_ID + " WHERE p." + PersistenceHelper.PROJECTS_ID + " = ? AND " +
				PersistenceHelper.SESSIONS_TIMESTAMP_START + " >= " + date.getTime() + " ORDER BY "
				+ PersistenceHelper.SESSIONS_TIMESTAMP_START + ";";

		Cursor cursor = database.rawQuery(query, new String[]{projectID});

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Session session = cursorToSession(cursor);
			sessions.add(session);
			cursor.moveToNext();
		}

		cursor.close();
		return sessions;
	}

	/**
	 * This method loads all sessions from the database.
	 *
	 * @return a list containing all sessions
	 * methodtype query method
	 */
	@Override
	public List<Session> listAll()
	{
		List<Session> sessions = new ArrayList<>();

		Cursor cursor = database.query(PersistenceHelper.TABLE_SESSIONS, allColumns, null, null, null,
				null, PersistenceHelper.SESSIONS_TIMESTAMP_START);

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Session session = cursorToSession(cursor);
			sessions.add(session);
			cursor.moveToNext();
		}

		cursor.close();
		return sessions;
	}

	/**
	 * This method converts a database cursor containing a row of the sessions table into a concrete session object.
	 *
	 * @param cursor the database cursor containing a row of the sessions table
	 * @return the session object representing one row of the sessions table
	 * methodtype conversion method
	 */
	private Session cursorToSession(Cursor cursor)
	{
		Session session = new Session();
		session.setId(cursor.getLong(0));
		session.setProjectId(cursor.getString(1));
		session.setStartTime(new Date(cursor.getLong(2)));
		session.setStopTime(new Date(cursor.getLong(3)));
		return session;
	}
}
