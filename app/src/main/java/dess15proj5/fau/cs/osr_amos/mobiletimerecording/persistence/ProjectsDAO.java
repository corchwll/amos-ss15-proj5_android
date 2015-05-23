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

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.util.Date;
import java.util.List;

public interface ProjectsDAO extends DAO
{
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
	Project create(String projectId, String projectName, Date finalDate, boolean isUsed, boolean isArchived,
						  boolean isDisplayed);

	/**
	 * This method is used to update a given project in the database.
	 *
	 * @param project the project which has to be updated.
	 * methodtype command method
	 */
	void update(Project project);

	/**
	 * This method loads the project with the given id from the database.
	 *
	 * @param projectId the id of the project that should be loaded from database
	 * @return the project matching the given id
	 * methodtype query method
	 */
	Project load(String projectId);

	/**
	 * This method deletes the project with the given id from the database.
	 *
	 * @param projectId the id of the project that should be deleted
	 * methodtype command method
	 */
	void delete(String projectId);

	/**
	 * This method loads all projects from the database that are not archived.
	 *
	 * @return a list containing all projects
	 * methodtype query method
	 */
	List<Project> listAll();
}
