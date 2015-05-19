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
