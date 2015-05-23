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

public class SQLiteDataAccessObjectFactory extends DataAccessObjectFactory
{
	private static DataAccessObjectFactory instance;

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
	 * This method specifies the concrete DAO object which should be used.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The concrete ProjectsDAO will be returned
	 * methodtype initialization method
	 */
	@Override
	public ProjectsDAO doCreateProjectsDAO(Context context)
	{
		return new ProjectsDAOImpl(context);
	}

	/**
	 * This method specifies the concrete DAO object which should be used.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The concrete SessionsDAO will be returned
	 * methodtype initialization method
	 */
	@Override
	public SessionsDAO doCreateSessionsDAO(Context context)
	{
		return new SessionsDAOImpl(context);
	}

	/**
	 * This method specifies the concrete DAO object which should be used.
	 *
	 * @param context The application context under which the database object will be created
	 * @return The concrete UsersDAO will be returned
	 * methodtype initialization method
	 */
	@Override
	protected UsersDAO doCreateUsersDAO(Context context)
	{
		return new UsersDAOImpl(context);
	}
}
