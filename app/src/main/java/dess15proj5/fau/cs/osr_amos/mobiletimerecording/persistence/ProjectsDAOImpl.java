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
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.GPSPoint;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectsDAOImpl extends AbstractDAO implements ProjectsDAO
{
	//an array of strings, containing all column names of the projects table
	private String[] allColumns =
			{PersistenceHelper.PROJECTS_ID, PersistenceHelper.PROJECTS_NAME, PersistenceHelper.PROJECTS_FINAL_DATE,
					PersistenceHelper.PROJECTS_IS_DISPLAYED, PersistenceHelper.PROJECTS_IS_USED,
					PersistenceHelper.PROJECTS_IS_ARCHIVED, PersistenceHelper.PROJECTS_LATITUDE,
					PersistenceHelper.PROJECTS_LONGITUDE};

	private String allDefaultIDs;

	/**
	 * Constructs a concrete ProjectsDAO object.
	 *
	 * @param context the application context under which the object is constructed
	 * methodtype constructor
	 */
	public ProjectsDAOImpl(Context context)
	{
		persistenceHelper = new PersistenceHelper(context);
		allDefaultIDs = "(";

		List<String> defaultProjects = PersistenceHelper.getDefaultProjectsAsList();
		for(int i = 0; i < defaultProjects.size(); i++)
		{
			if(i == 0)
			{
				allDefaultIDs += "'" + defaultProjects.get(i) + "'";
			} else
			{
				allDefaultIDs += ",'" + defaultProjects.get(i) + "'";
			}
		}

		allDefaultIDs += ")";
	}

	/**
	 * This method inserts the given information into the projects table and creates an object of type project.
	 *
	 * @param projectId the project id of the required project
	 * @param projectName the project name of the required project
	 * @param finalDate the optional final date for the required project
	 * @param isUsed a boolean whether the required project is used or not
	 * @param isArchived a boolean whether the required project is archived or not
	 * @param isDisplayed a boolean whether the required project is displayed or not
	 * @return the required project object is returned
	 * methodtype conversion method (since the given information is converted into an object of type project)
	 */
	@Override
	public Project create(String projectId, String projectName, Date finalDate, boolean isUsed, boolean isArchived,
						  boolean isDisplayed)
	{
		return create(projectId, projectName, finalDate, isUsed, isArchived, isDisplayed, 1000.0, 1000.0);
	}

	/**
	 * This method inserts the given information into the projects table and creates an object of type project.
	 *
	 * @param projectId the project id of the required project
	 * @param projectName the project name of the required project
	 * @param finalDate the optional final date for the required project
	 * @param isUsed a boolean whether the required project is used or not
	 * @param isArchived a boolean whether the required project is archived or not
	 * @param isDisplayed a boolean whether the required project is displayed or not
	 * @param latitude a double containing the latitude value of the projects location
	 * @param longitude  a double containing the longitude value of the projects location
	 * @return the required project object is returned
	 * methodtype conversion method (since the given information is converted into an object of type project)
	 */
	@Override
	public Project create(String projectId, String projectName, Date finalDate, boolean isUsed, boolean isArchived,
						  boolean isDisplayed, double latitude, double longitude)
	{
		//preparation and insert of the new project
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.PROJECTS_ID, projectId);
		values.put(PersistenceHelper.PROJECTS_NAME, projectName);
		values.put(PersistenceHelper.PROJECTS_FINAL_DATE, finalDate.getTime());
		values.put(PersistenceHelper.PROJECTS_IS_DISPLAYED, isDisplayed);
		values.put(PersistenceHelper.PROJECTS_IS_USED, isUsed);
		values.put(PersistenceHelper.PROJECTS_IS_ARCHIVED, isArchived);
		values.put(PersistenceHelper.PROJECTS_LATITUDE, latitude);
		values.put(PersistenceHelper.PROJECTS_LONGITUDE, longitude);
		database.insertOrThrow(PersistenceHelper.TABLE_PROJECTS, null, values);

		//retrieving the new project from database and constructing the object
		Cursor cursor = database.query(PersistenceHelper.TABLE_PROJECTS, allColumns,
				PersistenceHelper.PROJECTS_ID + " = '" + projectId + "'", null, null, null, null);
		cursor.moveToFirst();
		Project newProject = cursorToProject(cursor);
		cursor.close();

		return newProject;
	}

	/**
	 * This method is used to update a given project in the database.
	 *
	 * @param project the project which has to be updated.
	 * methodtype command method
	 */
	@Override
	public void update(Project project)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.PROJECTS_ID, project.getId());
		values.put(PersistenceHelper.PROJECTS_NAME, project.getName());
		values.put(PersistenceHelper.PROJECTS_FINAL_DATE, project.getFinalDate().getTime());
		values.put(PersistenceHelper.PROJECTS_IS_DISPLAYED, project.isDisplayed());
		values.put(PersistenceHelper.PROJECTS_IS_USED, project.isUsed());
		values.put(PersistenceHelper.PROJECTS_IS_ARCHIVED, project.isArchived());
		values.put(PersistenceHelper.PROJECTS_LATITUDE, project.getPoint()
															   .getLatitude());
		values.put(PersistenceHelper.PROJECTS_LONGITUDE, project.getPoint()
																.getLongitude());

		database.update(PersistenceHelper.TABLE_PROJECTS, values,
				PersistenceHelper.PROJECTS_ID + " = " + project.getId(), null);
	}

	/**
	 * This method loads the project with the given id from the database.
	 *
	 * @param projectId the id of the project that should be loaded from database
	 * @return the project matching the given id
	 * methodtype query method
	 */
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

	/**
	 * This method deletes the project with the given id from the database.
	 *
	 * @param projectId the id of the project that should be deleted
	 * methodtype command method
	 */
	@Override
	public void delete(String projectId)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.PROJECTS_IS_ARCHIVED, isNotADefaultProject(projectId));

		database.update(PersistenceHelper.TABLE_PROJECTS, values, PersistenceHelper.PROJECTS_ID + " = '" +
							projectId + "'", null);
	}

	/**
	 * This method checks whether a given project id belongs to one of the default projects or not.
	 *
	 * @param projectId the project id that has to be checked
	 * @return true if the project is not a default project and false if it is one
	 * methodtype boolean query method
	 */
	private boolean isNotADefaultProject(String projectId)
	{
		boolean result = true;

		List<String> defaultProjects = PersistenceHelper.getDefaultProjectsAsList();
		for(String s : defaultProjects)
		{
			if(s.equals(projectId))
			{
				result = false;
				break;
			}
		}

		return result;
	}

	/**
	 * This method loads all projects from the database that are not archived.
	 *
	 * @return a list containing all projects
	 * methodtype query method
	 */
	@Override
	public List<Project> listAll()
	{
		List<Project> projects = new ArrayList<>();

		//Select all default projects only
		Cursor cursor = database.query(PersistenceHelper.TABLE_PROJECTS, allColumns,
				PersistenceHelper.PROJECTS_IS_ARCHIVED + " <> 1 AND " + PersistenceHelper.PROJECTS_ID + " IN " +
						allDefaultIDs, null, null, null, PersistenceHelper.PROJECTS_NAME);

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Project project = cursorToProject(cursor);
			projects.add(project);
			cursor.moveToNext();
		}
		cursor.close();

		//Select all other projects
		cursor = database.query(PersistenceHelper.TABLE_PROJECTS, allColumns,
				PersistenceHelper.PROJECTS_IS_ARCHIVED + " <> 1 AND " + PersistenceHelper.PROJECTS_ID + " NOT IN " +
				allDefaultIDs, null, null, null, PersistenceHelper.PROJECTS_NAME);

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

	/**
	 * This method converts a database cursor containing a row of the projects table into a concrete project object.
	 *
	 * @param cursor the database cursor containing a row of the projects table
	 * @return the project object representing one row of the projects table
	 * methodtype conversion method
	 */
	private Project cursorToProject(Cursor cursor)
	{
		Project project = new Project();
		project.setId(cursor.getString(0));
		project.setName(cursor.getString(1));
		project.setFinalDate(new Date(cursor.getLong(2)));
		//to convert an integer from the database into a boolean one needs to compare the values with 1
		project.setIsDisplayed(cursor.getInt(3) == 1);
		project.setIsUsed(cursor.getInt(4) == 1);
		project.setIsArchived(cursor.getInt(5) == 1);

		double latitude = cursor.getDouble(6);
		double longitude = cursor.getDouble(7);
		project.setPoint(new GPSPoint(latitude, longitude));

		return project;
	}
}
