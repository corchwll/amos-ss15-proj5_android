package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.content.Context;

public abstract class DataAccessObjectFactory
{
	private static DataAccessObjectFactory instance = new SQLiteDataAccessObjectFactory();

	public static DataAccessObjectFactory getInstance()
	{
		return instance;
	}

	public static void setInstance(DataAccessObjectFactory dAOFactory)
	{
		instance = dAOFactory;
	}

	public ProjectsDAO createProjectsDAO(Context context)
	{
		return instance.createProjectsDAO(context);
	}

	public SessionsDAO createSessionsDAO(Context context)
	{
		return instance.createSessionsDAO(context);
	}

	public UsersDAO createUsersDAO(Context context)
	{
		return instance.createUsersDAO(context);
	}
}
