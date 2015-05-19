package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.content.Context;

import java.sql.SQLException;

public abstract class DataAccessObjectFactory
{
	private static DataAccessObjectFactory instance = new SQLiteDataAccessObjectFactory();
	private ProjectsDAO projectsDAO;
	private SessionsDAO sessionsDAO;
	private UsersDAO usersDAO;

	public static DataAccessObjectFactory getInstance()
	{
		return instance;
	}

	public static void setInstance(DataAccessObjectFactory dAOFactory)
	{
		instance = dAOFactory;
	}

	public ProjectsDAO createProjectsDAO(Context context) throws SQLException
	{
		if(projectsDAO == null)
		{
			projectsDAO = instance.createProjectsDAO(context);
			projectsDAO.open();
		}

		return projectsDAO;
	}

	public SessionsDAO createSessionsDAO(Context context) throws SQLException
	{
		if(sessionsDAO == null)
		{
			sessionsDAO = instance.createSessionsDAO(context);
			sessionsDAO.open();
		}

		return sessionsDAO;
	}

	public UsersDAO createUsersDAO(Context context) throws SQLException
	{
		if(usersDAO == null)
		{
			usersDAO = instance.createUsersDAO(context);
			usersDAO.open();
		}

		return usersDAO;
	}
}
