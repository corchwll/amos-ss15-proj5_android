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

	public static DataAccessObjectFactory getInstance()
	{
		if(instance == null)
		{
			instance = new SQLiteDataAccessObjectFactory();
		}

		return instance;
	}

	@Override
	public ProjectsDAO doCreateProjectsDAO(Context context)
	{
		return new ProjectsDAOImpl(context);
	}

	@Override
	public SessionsDAO doCreateSessionsDAO(Context context)
	{
		return new SessionsDAOImpl(context);
	}

	@Override
	protected UsersDAO doCreateUsersDAO(Context context)
	{
		return new UsersDAOImpl(context);
	}
}
