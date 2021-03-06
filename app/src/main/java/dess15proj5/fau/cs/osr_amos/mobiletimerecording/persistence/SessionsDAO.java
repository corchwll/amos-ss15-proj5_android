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

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface SessionsDAO extends DAO
{
	/**
	 * This method inserts the given information into the sessions table and creates an object of type session.
	 *
	 * @param projectId the project id the required session belongs to
	 * @param startTime the time when the new session started
	 * @return the required session object is returned
	 * methodtype conversion method (since the given information is converted into an object of type session)
	 */
	Session create(String projectId, Date startTime);

	/**
	 * This method inserts the given information into the sessions table and creates an object of type session.
	 *
	 * @param projectId the project id the required session belongs to
	 * @param startTime the time when the new session started
	 * @param stopTime the time when the new session terminated
	 * @return the required session object is returned
	 * methodtype conversion method (since the given information is converted into an object of type session)
	 */
	Session create(String projectId, Date startTime, Date stopTime);

	/**
	 * This method is used to update a given session in the database.
	 *
	 * @param session the session which has to be updated.
	 * methodtype command method
	 */
	void update(Session session);

	/**
	 * This method loads the session with the given id from the database.
	 *
	 * @param sessionId the id of the session that should be loaded from database
	 * @return the session matching the given id
	 * methodtype query method
	 */
	Session load(long sessionId);

	/**
	 * This method deletes the session with the given id from the database.
	 *
	 * @param sessionId the id of the session that should be deleted
	 * methodtype command method
	 */
	void delete(long sessionId);

	/**
	 * This method loads all sessions from the database that are belonging to the given projectID.
	 *
	 * @param projectID the id the sessions have to belong to
	 * @return a list containing all sessions for the given projectID
	 * methodtype query method
	 */
	List<Session> listAllForProject(String projectID);

	/**
	 * This method loads all sessions from the database that are belonging to the given projectID and happened since the
	 * given date ordered by startTime.
	 *
	 * @param projectID the id the sessions have to belong to
	 * @param date the date since which the sessions have to be recoreded
	 * @return a list containing all sessions for the given projectID
	 * methodtype query method
	 */
	List<Session> listAllForProjectSinceDate(String projectID, Date date);

	/**
	 * This method loads all sessions from the database.
	 *
	 * @return a list containing all sessions
	 * methodtype query method
	 */
	List<Session> listAll();

	/**
	 * This method loads all recorded sessions for the given date from the database
	 *
	 * @param cal the given date as calendar object
	 * @return the list containing all requested sessions
	 * methodtype query method
	 */
	List<Session> listAllForDate(Calendar cal);
}
