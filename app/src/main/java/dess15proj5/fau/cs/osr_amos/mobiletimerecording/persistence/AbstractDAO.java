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

import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public abstract class AbstractDAO
{
	protected SQLiteDatabase database;
	protected PersistenceHelper persistenceHelper;

	/**
	 *	This method fetches a writable database via android SQLiteOpenHelper and prepares it for usage.
	 *
	 * @throws SQLException
	 * methodtype initialization method
	 */
	public void open() throws SQLException
	{
		database = persistenceHelper.getWritableDatabase();
	}

	/**
	 * This method closes the writable database.
	 *
	 * methodtype command method
	 */
	public void close()
	{
		persistenceHelper.close();
	}
}
