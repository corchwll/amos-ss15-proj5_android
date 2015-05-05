package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;

import java.util.Date;

public class Session
{
	private long id;
	private long projectId;
	private Date startTime;
	private Date stopTime;

	/**
	 * Returns the session id.
	 *
	 * @methodtype get method
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the session id if greater than zero.
	 *
	 * @methodtype set method
	 * @pre id > 0
	 * @post session id successfully set.
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Returns the project id related with this session.
	 *
	 * @methodtype get method
	 */
	public long getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the related project id if greater than zero.
	 *
	 * @methodtype set method
	 * @pre projectId > 0
	 * @post project id successfully set.
	 */
	public void setProjectId(long projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Returns the starting time of this session.
	 *
	 * @methodtype get method
	 */
	public Date getStartTime()
	{
		return startTime;
	}

	/**
	 * Sets the starting time of this session if not null.
	 *
	 * @methodtype set method
	 * @pre startTime != null;
	 * @post startTime successfully set.
	 */
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * Returns the stopping time of this session.
	 *
	 * @methodtype get method
	 */
	public Date getStopTime()
	{
		return stopTime;
	}

	/**
	 * Sets the stopping time if not null.
	 *
	 * @methodtype set method
	 * @pre stopTime != null;
	 * @post stopTime successfully set.
	 */
	public void setStopTime(Date stopTime)
	{
		this.stopTime = stopTime;
	}
}
