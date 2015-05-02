package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public abstract class AbstractDAO
{
	protected SQLiteDatabase database;
	protected PersistenceHelper persistenceHelper;

	public void open() throws SQLException
	{
		database = persistenceHelper.getWritableDatabase();
	}

	public void close()
	{
		persistenceHelper.close();
	}
}
