package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsDAOImpl extends AbstractDAO implements ProjectsDAO
{
	private String[] allColumns =
			{PersistenceHelper.PROJECTS_ID, PersistenceHelper.PROJECTS_NAME, PersistenceHelper.PROJECTS_IS_DISPLAYED,
					PersistenceHelper.PROJECTS_IS_USED, PersistenceHelper.PROJECTS_IS_ARCHIVED};

	public ProjectsDAOImpl(Context context)
	{
		persistenceHelper = new PersistenceHelper(context);
	}

	@Override
	public Project create(String projectId, String projectName, boolean isDisplayed, boolean isUsed, boolean isArchived)
	{
		ContentValues values = new ContentValues();
        values.put(PersistenceHelper.PROJECTS_ID, projectId);
		values.put(PersistenceHelper.PROJECTS_NAME, projectName);
		values.put(PersistenceHelper.PROJECTS_IS_DISPLAYED, isDisplayed);
		values.put(PersistenceHelper.PROJECTS_IS_USED, isUsed);
		values.put(PersistenceHelper.PROJECTS_IS_ARCHIVED, isArchived);
		database.insert(PersistenceHelper.TABLE_PROJECTS, null, values);

		Cursor cursor = database.query(PersistenceHelper.TABLE_PROJECTS, allColumns,
				PersistenceHelper.PROJECTS_ID + " = '" + projectId + "'", null, null, null, null);
		cursor.moveToFirst();
		Project newProject = cursorToProject(cursor);
		cursor.close();
		return newProject;
	}

	@Override
	public void update(Project project)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.PROJECTS_ID, project.getId());
		values.put(PersistenceHelper.PROJECTS_NAME, project.getName());
		values.put(PersistenceHelper.PROJECTS_IS_DISPLAYED, project.isDisplayed());
		values.put(PersistenceHelper.PROJECTS_IS_USED, project.isUsed());
		values.put(PersistenceHelper.PROJECTS_IS_ARCHIVED, project.isArchived());

		database.update(PersistenceHelper.TABLE_PROJECTS, values,
				PersistenceHelper.PROJECTS_ID + " = " + project.getId(), null);
	}

	@Override
	public Project load(String projectId)
	{
		Cursor cursor = database.query(PersistenceHelper.TABLE_PROJECTS, allColumns,
				PersistenceHelper.PROJECTS_ID + " = " + projectId, null, null, null, null);
		cursor.moveToFirst();
		Project project = cursorToProject(cursor);
		cursor.close();
		return project;
	}

	@Override
	public void delete(String projectId)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.PROJECTS_IS_ARCHIVED, isNotADefaultProject(projectId));

		database.update(PersistenceHelper.TABLE_PROJECTS, values, PersistenceHelper.PROJECTS_ID + " = '" +
							projectId + "'", null);
	}

	private boolean isNotADefaultProject(String projectId)
	{
		boolean result = true;

		for(String s : PersistenceHelper.defaultProjects)
		{
			if(s.equals(projectId))
			{
				result = false;
				break;
			}
		}

		return result;
	}

	@Override
	public List<Project> listAll()
	{
		List<Project> projects = new ArrayList<>();

		Cursor cursor = database.query(PersistenceHelper.TABLE_PROJECTS, allColumns,
				PersistenceHelper.PROJECTS_IS_ARCHIVED + " <> 1", null, null, null, null);

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Project project = cursorToProject(cursor);
			projects.add(project);
			cursor.moveToNext();
		}

		cursor.close();
		return projects;
	}

	private Project cursorToProject(Cursor cursor)
	{
		Project project = new Project();
		project.setId(cursor.getString(0));
		project.setName(cursor.getString(1));
		project.setIsDisplayed(cursor.getInt(2) == 1);
		project.setIsUsed(cursor.getInt(3) == 1);
		project.setIsArchived(cursor.getInt(4) == 1);
		return project;
	}
}
