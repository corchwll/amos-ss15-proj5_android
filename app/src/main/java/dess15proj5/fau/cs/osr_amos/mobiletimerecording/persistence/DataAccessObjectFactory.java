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
	private static DataAccessObjectFactory instance = new SQLiteDataAccessObjectFactory();
	private ProjectsDAO projectsDAO;
	private SessionsDAO sessionsDAO;
	private UsersDAO usersDAO;

	public static DataAccessObjectFactory getInstance()
	{
		return instance;
	}

	public static void setInstance(DataAccessObjectFactory dAOFactory)
	{
		instance = dAOFactory;
	}

	public ProjectsDAO createProjectsDAO(Context context) throws SQLException
	{
		if(projectsDAO == null)
		{
			projectsDAO = doCreateProjectsDAO(context);
			projectsDAO.open();
		}
		return projectsDAO;
	}

	protected abstract ProjectsDAO doCreateProjectsDAO(Context context);

	public SessionsDAO createSessionsDAO(Context context) throws SQLException
	{
		if(sessionsDAO == null)
		{
			sessionsDAO = doCreateSessionsDAO(context);
			sessionsDAO.open();
		}
		return sessionsDAO;
	}

	protected abstract SessionsDAO doCreateSessionsDAO(Context context);

	public UsersDAO createUsersDAO(Context context) throws SQLException
	{
		if(usersDAO == null)
		{
			usersDAO = doCreateUsersDAO(context);
			usersDAO.open();
		}
		return usersDAO;
	}

	protected abstract UsersDAO doCreateUsersDAO(Context context);
}
