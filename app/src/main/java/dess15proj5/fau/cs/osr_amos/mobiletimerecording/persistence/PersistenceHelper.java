package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersistenceHelper extends SQLiteOpenHelper
{
	public static final String TABLE_PROJECTS = "projects";
	public static final String PROJECTS_ID = "id";
	public static final String PROJECTS_NAME = "name";
	public static final String PROJECTS_IS_DISPLAYED = "is_displayed";
	public static final String PROJECTS_IS_USED = "is_used";
	public static final String PROJECTS_IS_ARCHIVED = "is_archived";

	private static final String CREATE_PROJECTS =
			"create table " + TABLE_PROJECTS + "(" + PROJECTS_ID + " integer primary key not null, " +
					PROJECTS_NAME + " text, " + PROJECTS_IS_DISPLAYED + " integer, " + PROJECTS_IS_USED + " integer, " +
					PROJECTS_IS_ARCHIVED + " integer);";

	public static final String TABLE_SESSIONS = "sessions";
	public static final String SESSIONS_ID = "id";
	public static final String SESSIONS_PROJECT_ID = "project_id";
	public static final String SESSIONS_TIMESTAMP_START = "start_time";
	public static final String SESSIONS_TIMESTAMP_STOP = "stop_time";

	private static final String CREATE_SESSIONS =
			"create table " + TABLE_SESSIONS + "(" + SESSIONS_ID + " integer primary key autoincrement, " +
					SESSIONS_PROJECT_ID + " integer, " + SESSIONS_TIMESTAMP_START + " integer not null, " +
					SESSIONS_TIMESTAMP_STOP + " integer not null, " + "foreign key(" + SESSIONS_PROJECT_ID + ") " +
					"references " +
					TABLE_PROJECTS + "(" + PROJECTS_ID + "));";

	public static final String TABLE_USERS = "users";
	public static final String USERS_ID = "id";
	public static final String USERS_LAST_NAME = "last_name";
	public static final String USERS_FIRST_NAME = "first_name";
	public static final String USERS_WEEKLY_WORKING_TIME = "weekly_working_time";
	public static final String USERS_TOTAL_VACATION_TIME = "total_vacation_time";
	public static final String USERS_CURRENT_VACATION_TIME = "current_vacation_time";
	public static final String USERS_CURRENT_OVERTIME = "current_overtime";
	public static final String USERS_REGISTRATION_DATE = "registration_date";

	private static final String CREATE_USERS =
			"create table " + TABLE_USERS + "(" + USERS_ID + " integer primary key not null, " +
					USERS_LAST_NAME + " text, " + USERS_FIRST_NAME + " text, " + USERS_WEEKLY_WORKING_TIME + " " +
					"integer, " + USERS_TOTAL_VACATION_TIME + " integer, " + USERS_CURRENT_VACATION_TIME + " integer," +
					USERS_CURRENT_OVERTIME + " integer, " + USERS_REGISTRATION_DATE + " integer);";

	private static final String DATABASE_NAME = "mobile_time_recording";
	private static final int DATABASE_VERSION = 4;

	public PersistenceHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_PROJECTS);
		db.execSQL(CREATE_SESSIONS);
		db.execSQL(CREATE_USERS);
	}

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
