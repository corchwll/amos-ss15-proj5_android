package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;

import java.util.Date;

public class User
{
	private long employeeId;
	private String lastName;
	private String firstName;
	private int weeklyWorkingTime;
	private int totalVacationTime;
	private int currentVacationTime;
	private int currentOvertime;
	private Date registrationDate;

	public long getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId(long employeeId)
	{
		this.employeeId = employeeId;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public int getWeeklyWorkingTime()
	{
		return weeklyWorkingTime;
	}

	public void setWeeklyWorkingTime(int weeklyWorkingTime)
	{
		this.weeklyWorkingTime = weeklyWorkingTime;
	}

	public int getTotalVacationTime()
	{
		return totalVacationTime;
	}

	public void setTotalVacationTime(int totalVacationTime)
	{
		this.totalVacationTime = totalVacationTime;
	}

	public int getCurrentVacationTime()
	{
		return currentVacationTime;
	}

	public void setCurrentVacationTime(int currentVacationTime)
	{
		this.currentVacationTime = currentVacationTime;
	}

	public int getCurrentOvertime()
	{
		return currentOvertime;
	}

	public void setCurrentOvertime(int currentOvertime)
	{
		this.currentOvertime = currentOvertime;
	}

	public Date getRegistrationDate()
	{
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate)
	{
		this.registrationDate = registrationDate;
	}
}
