package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;

import java.util.Date;

public class Session
{
	private long id;
	private long projectId;
	private Date startTime;
	private Date stopTime;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getProjectId()
	{
		return projectId;
	}

	public void setProjectId(long projectId)
	{
		this.projectId = projectId;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getStopTime()
	{
		return stopTime;
	}

	public void setStopTime(Date stopTime)
	{
		this.stopTime = stopTime;
	}
}
