package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionsDAOImpl extends AbstractDAO implements SessionsDAO
{
	private String[] allColumns = {PersistenceHelper.SESSIONS_ID, PersistenceHelper.SESSIONS_PROJECT_ID,
			PersistenceHelper.SESSIONS_TIMESTAMP_START, PersistenceHelper.SESSIONS_TIMESTAMP_STOP};

	public SessionsDAOImpl(Context context)
	{
		persistenceHelper = new PersistenceHelper(context);
	}

	@Override
	public Session create(long projectId, Date startTime)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.SESSIONS_PROJECT_ID, projectId);
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_START, startTime.getTime());
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_STOP, startTime.getTime());
		long insertId = database.insert(PersistenceHelper.TABLE_SESSIONS, null, values);

		Cursor cursor = database.query(PersistenceHelper.TABLE_SESSIONS, allColumns, PersistenceHelper.SESSIONS_ID +
				" = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Session newSession = cursorToSession(cursor);
		cursor.close();
		return newSession;
	}

	@Override
	public void update(Session session)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.SESSIONS_ID, session.getId());
		values.put(PersistenceHelper.SESSIONS_PROJECT_ID, session.getProjectId());
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_START, session.getStartTime()
																	  .getTime());
		values.put(PersistenceHelper.SESSIONS_TIMESTAMP_STOP, session.getStopTime()
																	 .getTime());

		database.update(PersistenceHelper.TABLE_SESSIONS, values,
				PersistenceHelper.SESSIONS_ID + " = " + session.getId(), null);
	}

	@Override
	public Session load(long sessionId)
	{
		Cursor cursor = database.query(PersistenceHelper.TABLE_SESSIONS, allColumns, PersistenceHelper.SESSIONS_ID +
				" = " + sessionId, null, null, null, null);
		cursor.moveToFirst();
		Session session = cursorToSession(cursor);
		cursor.close();
		return session;
	}

	@Override
	public void delete(long sessionId)
	{
		database.delete(PersistenceHelper.TABLE_SESSIONS, PersistenceHelper.SESSIONS_ID + " = " + sessionId, null);
	}

	@Override
	public List<Session> listAll()
	{
		List<Session> sessions= new ArrayList<>();

		Cursor cursor = database.query(PersistenceHelper.TABLE_SESSIONS, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Session session = cursorToSession(cursor);
			sessions.add(session);
			cursor.moveToNext();
		}

		cursor.close();
		return sessions;
	}

	private Session cursorToSession(Cursor cursor)
	{
		Session session = new Session();
		session.setId(cursor.getLong(0));
		session.setProjectId(cursor.getLong(1));
		session.setStartTime(new Date(cursor.getLong(2)));
		session.setStopTime(new Date(cursor.getLong(3)));
		return session;
	}
}
