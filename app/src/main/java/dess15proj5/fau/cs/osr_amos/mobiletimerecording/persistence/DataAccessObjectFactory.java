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

import android.content.Context;

import java.sql.SQLException;

public abstract class DataAccessObjectFactory
{
	private static DataAccessObjectFactory instance;
	private ProjectsDAO projectsDAO;
	private SessionsDAO sessionsDAO;
	private UsersDAO usersDAO;

	/**
	 * This method returns an instance of DataAccessObjectFactory to fulfill the singleton pattern. If there wasn't
	 * initialized an instance yet, it will be constructed.
	 *
	 * @return the singleton instance of the DataAccessObjectFactory
	 * methodtype get method
	 */
	public static DataAccessObjectFactory getInstance()
	{
		if(instance == null)
		{
			instance = new SQLiteDataAccessObjectFactory();
		}

		return instance;
	}

	/**
	 * This method allows to inject another DataAccessObjectFactory if needed.
	 *
	 * @param dAOFactory DataAccessObjectFactory
	 * methodtype set method
	 */
	public static void setInstance(DataAccessObjectFactory dAOFactory)
	{
		instance = dAOFactory;
	}

	/**
	 * This method creates a DAO for projects if there wasn't already one created. It also opens the database
	 * connection so that queries can instantly be executed.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The requested ProjectsDAO will be returned
	 * @throws SQLException
	 * methodtype factory method
	 */
	public ProjectsDAO createProjectsDAO(Context context) throws SQLException
	{
		if(projectsDAO == null)
		{
			projectsDAO = doCreateProjectsDAO(context);
			projectsDAO.open();
		}

		return projectsDAO;
	}

	/**
	 * This is the abstract method an actual factory class can overwrite to specify the concrete DAO object which
	 * should be used.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The concrete ProjectsDAO will be returned
	 * methodtype initialization method
	 */
	protected abstract ProjectsDAO doCreateProjectsDAO(Context context);

	/**
	 * This method creates a DAO for sessions if there wasn't already one created. It also opens the database
	 * connection so that queries can instantly be executed.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The requested SessionsDAO will be returned
	 * @throws SQLException
	 * methodtype factory method
	 */
	public SessionsDAO createSessionsDAO(Context context) throws SQLException
	{
		if(sessionsDAO == null)
		{
			sessionsDAO = doCreateSessionsDAO(context);
			sessionsDAO.open();
		}

		return sessionsDAO;
	}

	/**
	 * This is the abstract method an actual factory class can overwrite to specify the concrete DAO object which
	 * should be used.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The concrete SessionsDAO will be returned
	 * methodtype initialization method
	 */
	protected abstract SessionsDAO doCreateSessionsDAO(Context context);

	/**
	 * This method creates a DAO for users if there wasn't already one created. It also opens the database
	 * connection so that queries can instantly be executed.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The requested UsersDAO will be returned
	 * @throws SQLException
	 * methodtype factory method
	 */
	public UsersDAO createUsersDAO(Context context) throws SQLException
	{
		if(usersDAO == null)
		{
			usersDAO = doCreateUsersDAO(context);
			usersDAO.open();
		}

		return usersDAO;
	}

	/**
	 * This is the abstract method an actual factory class can overwrite to specify the concrete DAO object which
	 * should be used.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The concrete UsersDAO will be returned
	 * methodtype initialization method
	 */
	protected abstract UsersDAO doCreateUsersDAO(Context context);
}
