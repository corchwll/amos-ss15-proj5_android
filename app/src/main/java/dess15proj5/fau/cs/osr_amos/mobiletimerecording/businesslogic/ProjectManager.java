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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.PersistenceHelper;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.ProjectArrayAdapter;

import java.sql.SQLException;
import java.util.List;

public class ProjectManager
{
	public static final int POSITION_OF_SPECIAL_PROJECTS_SEPARATOR = 0;
	public static final String SEPARATOR_ID = "-1";

	private ProjectArrayAdapter adapter;
	private Context context;

	public ProjectManager(ProjectArrayAdapter adapter, Context context)
	{
		this.adapter = adapter;
		this.context = context;
	}

	/**
	 * This method is used to load all projects for the project list into the adapter attribute.
	 *
	 * methodtype command method
	 */
	public void loadProjects()
	{
		try
		{
			List<Project> projectList = getProjectsFromDB();
			addProjectsToAdapter(projectList);

			SharedPreferences sharedPref = context.getSharedPreferences("gpsSettings", Context.MODE_PRIVATE);
			if(sharedPref.getBoolean("useGPS", false))
			{
				orderProjectListViaDistance(projectList);
			}
		} catch(SQLException e)
		{
			Toast.makeText(context, "Could not load project list due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * This method loads all projects from the database.
	 *
	 * @return the list of projects from the database
	 * @throws SQLException
	 * methodtype get method
	 */
	private List<Project> getProjectsFromDB() throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance().createProjectsDAO(context);
		return projectsDAO.listAll();
	}

	private void orderProjectListViaDistance(List<Project> projects)
	{

	}

	/**
	 * This method adds the projects from the database to the adapter.
	 *
	 * methodtype initialization method
	 */
	private void addProjectsToAdapter(List<Project> projects)
	{
		adapter.clear();
		addSeparatorsToProjectList(projects);
		adapter.setProjectList(projects);
		adapter.addAll(projects);
		adapter.notifyDataSetChanged();
	}

	/**
	 * This method adds separators to the given projectsList
	 *
	 * methodtype command method
	 */
	private void addSeparatorsToProjectList(List<Project> projectList)
	{
		projectList.add(POSITION_OF_SPECIAL_PROJECTS_SEPARATOR,
				getSeparatorProject(context.getResources().getString(R.string.separator_special_projects)));
		projectList.add(getPositionOfSeparatorAfterSpecialProjects(),
				getSeparatorProject(context.getResources().getString(R.string.separator_added_projects)));
	}

	/**
	 * Returns a project, that is used to display a separator
	 *
	 * @param caption sets the text, which is shown in the separator
	 * methodtype get method
	 */
	private Project getSeparatorProject(String caption)
	{
		Project separator = new Project();
		separator.setName(caption);
		separator.setId(SEPARATOR_ID);
		return separator;
	}

	/**
	 * Returns the position of the second separator, which is shown after the special projects
	 *
	 * methodtype get method
	 */
	private int getPositionOfSeparatorAfterSpecialProjects()
	{
		return PersistenceHelper.getDefaultProjectsAsList().size() + 1;
	}
}
