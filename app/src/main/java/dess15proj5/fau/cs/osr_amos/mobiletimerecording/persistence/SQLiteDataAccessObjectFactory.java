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
	public ProjectsDAO createProjectsDAO(Context context)
	{
		return new ProjectsDAOImpl(context);
	}

	@Override
	public SessionsDAO createSessionsDAO(Context context)
	{
		return new SessionsDAOImpl(context);
	}

	@Override
	public UsersDAO createUsersDAO(Context context)
	{
		return new UsersDAOImpl(context);
	}
}
