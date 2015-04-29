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

	private static final String CREATE_PROJECTS =
			"create table " + TABLE_PROJECTS + "(" + PROJECTS_ID + " integer primary key not null, " +
					PROJECTS_NAME + " text);";

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

	private static final String DATABASE_NAME = "mobile_time_recording";
	private static final int DATABASE_VERSION = 2;

	public PersistenceHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_PROJECTS);
		db.execSQL(CREATE_SESSIONS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(PersistenceHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion +
						", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
		onCreate(db);
	}
}
