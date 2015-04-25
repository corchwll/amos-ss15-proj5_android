package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;

import java.util.Date;

public class Session
{
	private long id;
	private long project_id;
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

	public long getProject_id()
	{
		return project_id;
	}

	public void setProject_id(long project_id)
	{
		this.project_id = project_id;
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
