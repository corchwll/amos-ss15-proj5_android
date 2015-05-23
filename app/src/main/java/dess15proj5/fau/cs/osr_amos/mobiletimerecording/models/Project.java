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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;

import java.util.Date;

public class Project
{
	private String id;
	private String name;
	private Date finalDate;
	private boolean isDisplayed;
	private boolean isUsed;
	private boolean isArchived;

	/**
	 * Returns the project id.
	 *
	 * methodtype get method
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Sets the project id to the given one if greater than zero.
	 *
	 * methodtype set method
	 * pre id > 0
	 * post id successfully set
	 */
	public void setId(String id)
	{
		assert id.length() == 5: "project id has to have 5 digits.";
		this.id = id;
	}

	/**
	 * Returns the name of the project.
	 *
	 * methodtype get method
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the project if not null.
	 *
	 * methodtype set method
	 * pre name != null
	 * post name successfully set
	 */
	public void setName(String name)
	{
		assert name != null: "project name should not be null!";
		this.name = name;
	}

	/**
	 * Returns the final date of the project.
	 *
	 * methodtype get method
	 */
	public Date getFinalDate()
	{
		return finalDate;
	}

	/**
	 * Sets the final date of the project if not null.
	 *
	 * methodtype set method
	 * pre finalDate != null
	 * post finalDate successfully set
	 */
	public void setFinalDate(Date finalDate)
	{
		assert finalDate != null: "finalDate should not be null!";
		this.finalDate = finalDate;
	}

	/**
	 * Returns whether this project is displayed or not.
	 *
	 * methodtype boolean query method
	 */
	public boolean isDisplayed()
	{
		return isDisplayed;
	}

	/**
	 * Sets whether this project is displayed or not.
	 *
	 * methodtype set method
	 */
	public void setIsDisplayed(boolean isDisplayed)
	{
		this.isDisplayed = isDisplayed;
	}

	/**
	 * Returns whether this project is used or not.
	 *
	 * methodtype boolean query method
	 */
	public boolean isUsed()
	{
		return isUsed;
	}

	/**
	 * Sets whether this project is used or not.
	 *
	 * methodtype set method
	 */
	public void setIsUsed(boolean isUsed)
	{
		this.isUsed = isUsed;
	}

	/**
	 * Returns whether this project is archived or not.
	 *
	 * methodtype boolean query method
	 */
	public boolean isArchived()
	{
		return isArchived;
	}

	/**
	 * Sets whether this project is archived or not.
	 *
	 * methodtype set method
	 */
	public void setIsArchived(boolean isArchived)
	{
		this.isArchived = isArchived;
	}
}
