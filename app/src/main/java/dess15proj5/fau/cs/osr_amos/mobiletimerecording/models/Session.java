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

public class Session
{
	private long id;
	private String projectId;
	private Date startTime;
	private Date stopTime;

	/**
	 * This method is used to construct an empty session object that will be filled by the DAOs.
	 *
	 * methodtype constructor
	 */
	public Session()
	{}

	/**
	 * This method is used to construct a Session object.
	 *
	 * @param id the id of the session
	 * @param projectId the projectID for which the session was recorded
	 * @param startTime the date when the session started
	 * @param stopTime the date when the session stopped
	 * methodtype constructor
	 */
	public Session(long id, String projectId, Date startTime, Date stopTime)
	{
		this.id = id;
		this.projectId = projectId;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}

	/**
	 * Returns the session id.
	 *
	 * methodtype get method
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the session id if greater than zero.
	 *
	 * methodtype set method
	 * pre id > 0
	 * post session id successfully set.
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Returns the project id related with this session.
	 *
	 * methodtype get method
	 */
	public String getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the related project id if greater than zero.
	 *
	 * methodtype set method
	 * pre projectId has to have 5 digits
	 * post project id successfully set.
	 */
	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Returns the starting time of this session.
	 *
	 * methodtype get method
	 */
	public Date getStartTime()
	{
		return startTime;
	}

	/**
	 * Sets the starting time of this session if not null.
	 *
	 * methodtype set method
	 * pre startTime != null;
	 * post startTime successfully set.
	 */
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * Returns the stopping time of this session.
	 *
	 * methodtype get method
	 */
	public Date getStopTime()
	{
		return stopTime;
	}

	/**
	 * Sets the stopping time if not null.
	 *
	 * methodtype set method
	 * pre stopTime != null;
	 * post stopTime successfully set.
	 */
	public void setStopTime(Date stopTime)
	{
		this.stopTime = stopTime;
	}
}
