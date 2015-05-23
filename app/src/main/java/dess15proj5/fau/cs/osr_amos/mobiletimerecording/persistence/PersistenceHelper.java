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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PersistenceHelper extends SQLiteOpenHelper
{
	public static List<String> defaultProjects = new ArrayList<>();

	/*
	 * Constants for the database table projects
	 */
	public static final String TABLE_PROJECTS = "projects";
	public static final String PROJECTS_ID = "id";
	public static final String PROJECTS_NAME = "name";
	public static final String PROJECTS_FINAL_DATE = "final_date";
	public static final String PROJECTS_IS_DISPLAYED = "is_displayed";
	public static final String PROJECTS_IS_USED = "is_used";
	public static final String PROJECTS_IS_ARCHIVED = "is_archived";

	/*
	 * Create Table statement for the table projects
	 */
	private static final String CREATE_PROJECTS =
			"create table " + TABLE_PROJECTS + "(" + PROJECTS_ID + " text primary key not null, " +
					PROJECTS_NAME + " text, " + PROJECTS_FINAL_DATE + " integer, " + PROJECTS_IS_DISPLAYED +
					" integer, " + PROJECTS_IS_USED + " integer, " + PROJECTS_IS_ARCHIVED + " integer);";

	/*
	 * Constants for the database table sessions
	 */
	public static final String TABLE_SESSIONS = "sessions";
	public static final String SESSIONS_ID = "id";
	public static final String SESSIONS_PROJECT_ID = "project_id";
	public static final String SESSIONS_TIMESTAMP_START = "start_time";
	public static final String SESSIONS_TIMESTAMP_STOP = "stop_time";

	/*
	 * Create Table statement for the table sessions
	 */
	private static final String CREATE_SESSIONS =
			"create table " + TABLE_SESSIONS + "(" + SESSIONS_ID + " integer primary key autoincrement, " +
					SESSIONS_PROJECT_ID + " text, " + SESSIONS_TIMESTAMP_START + " integer not null, " +
					SESSIONS_TIMESTAMP_STOP + " integer not null, " + "foreign key(" + SESSIONS_PROJECT_ID + ") " +
					"references " +
					TABLE_PROJECTS + "(" + PROJECTS_ID + "));";

	/*
	 * Constants for the database table users
	 */
	public static final String TABLE_USERS = "users";
	public static final String USERS_ID = "id";
	public static final String USERS_LAST_NAME = "last_name";
	public static final String USERS_FIRST_NAME = "first_name";
	public static final String USERS_WEEKLY_WORKING_TIME = "weekly_working_time";
	public static final String USERS_TOTAL_VACATION_TIME = "total_vacation_time";
	public static final String USERS_CURRENT_VACATION_TIME = "current_vacation_time";
	public static final String USERS_CURRENT_OVERTIME = "current_overtime";
	public static final String USERS_REGISTRATION_DATE = "registration_date";

	/*
	 * Create Table statement for the table users
	 */
	private static final String CREATE_USERS =
			"create table " + TABLE_USERS + "(" + USERS_ID + " text primary key not null, " +
					USERS_LAST_NAME + " text, " + USERS_FIRST_NAME + " text, " + USERS_WEEKLY_WORKING_TIME + " " +
					"integer, " + USERS_TOTAL_VACATION_TIME + " integer, " + USERS_CURRENT_VACATION_TIME + " integer," +
					USERS_CURRENT_OVERTIME + " integer, " + USERS_REGISTRATION_DATE + " integer);";

	/*
	 * Constants for database name and version
	 */
	private static final String DATABASE_NAME = "mobile_time_recording";
	private static final int DATABASE_VERSION = 7;

	/**
	 * Constructor for the PersistenceHelper.
	 *
	 * @param context the application context under which this object should be constructed.
	 * methodtype constructor
	 */
	public PersistenceHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This method is called in the android lifecycle when the database is used for the first time.
	 *
	 * @param db the database object provided by the android application.
	 * methodtype initialization method
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_PROJECTS);
		insertDefaultProjects(db);
		db.execSQL(CREATE_SESSIONS);
		db.execSQL(CREATE_USERS);
	}

	/**
	 * This method is internally used to insert the default projects in to the created database.
	 *
	 * @param db the database object provided by the android application.
	 * methodtype command method
	 */
	private void insertDefaultProjects(SQLiteDatabase db)
	{
		//insertion of default project Vacation
		ContentValues values = new ContentValues();
		values.put(PROJECTS_ID, "10000");
		values.put(PROJECTS_NAME, "Vacation");
		values.put(PROJECTS_FINAL_DATE, 0);
		values.put(PROJECTS_IS_DISPLAYED, 1);
		values.put(PROJECTS_IS_USED, 1);
		values.put(PROJECTS_IS_ARCHIVED, 0);
		db.insert(TABLE_PROJECTS, null, values);

		//insertion of default project Training
		values = new ContentValues();
		values.put(PROJECTS_ID, "10001");
		values.put(PROJECTS_NAME, "Training");
		values.put(PROJECTS_FINAL_DATE, 0);
		values.put(PROJECTS_IS_DISPLAYED, 1);
		values.put(PROJECTS_IS_USED, 1);
		values.put(PROJECTS_IS_ARCHIVED, 0);
		db.insert(TABLE_PROJECTS, null, values);

		//insertion of default project Illness
		values = new ContentValues();
		values.put(PROJECTS_ID, "10002");
		values.put(PROJECTS_NAME, "Illness");
		values.put(PROJECTS_FINAL_DATE, 0);
		values.put(PROJECTS_IS_DISPLAYED, 1);
		values.put(PROJECTS_IS_USED, 1);
		values.put(PROJECTS_IS_ARCHIVED, 0);
		db.insert(TABLE_PROJECTS, null, values);

		//insertion of default project Office
		values = new ContentValues();
		values.put(PROJECTS_ID, "10003");
		values.put(PROJECTS_NAME, "Office");
		values.put(PROJECTS_FINAL_DATE, 0);
		values.put(PROJECTS_IS_DISPLAYED, 1);
		values.put(PROJECTS_IS_USED, 1);
		values.put(PROJECTS_IS_ARCHIVED, 0);
		db.insert(TABLE_PROJECTS, null, values);

		//keeping track of the default project ids
		defaultProjects.add("10000");
		defaultProjects.add("10001");
		defaultProjects.add("10002");
		defaultProjects.add("10003");
	}

	/**
	 * This method is called in the android lifecycle if the version of the database has changed.
	 *
	 * @param db the database object provided by the android application
	 * @param oldVersion the version number of the old database
	 * @param newVersion the version number of the new database
	 * methodtype command method
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(PersistenceHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion +
						", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(db);
	}
}
