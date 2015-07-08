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

public class User
{
	private String employeeId;
	private String lastName;
	private String firstName;
	private int weeklyWorkingTime;
	private int totalVacationTime;
	private int currentVacationTime;
	private int currentOvertime;
	private Date registrationDate;

	/**
	 * Returns the user id.
	 *
	 * methodtype get method
	 */
	public String getEmployeeId()
	{
		return employeeId;
	}

	/**
	 * Sets the user id if greater than zero.
	 *
	 * methodtype set method
	 * pre employeeId has to have 5 digits;
	 * post user id successfully set.
	 */
	public void setEmployeeId(String employeeId)
	{
		assert employeeId.length() == 5: "user id has to have 5 digits!";
		this.employeeId = employeeId;
	}

	/**
	 * Returns the last name of the user.
	 *
	 * methodtype get method
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Sets the users last name if not null.
	 *
	 * methodtype set method
	 * pre lastName != null;
	 * post lastName successfully set.
	 */
	public void setLastName(String lastName)
	{
		assert lastName != null : "last name sould not be null!";
		this.lastName = lastName;
	}

	/**
	 * Returns the first name of the user.
	 *
	 * methodtype get method
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Sets the users first name if not null.
	 *
	 * methodtype set method
	 * pre firstName != null;
	 * post firstName successfully set.
	 */
	public void setFirstName(String firstName)
	{
		assert firstName != null : "first name should not be null!";
		this.firstName = firstName;
	}

	/**
	 * Returns the working time per week of the user.
	 *
	 * methodtype get method
	 */
	public int getWeeklyWorkingTime()
	{
		return weeklyWorkingTime;
	}

	/**
	 * Sets the working time per week of the user if greater than zero.
	 *
	 * methodtype set method
	 * pre weeklyWorkingTime > 0;
	 * post weeklyWorkingTime successfully set.
	 */
	public void setWeeklyWorkingTime(int weeklyWorkingTime)
	{
		assert weeklyWorkingTime > 0 : "working time should be positive at least!";
		this.weeklyWorkingTime = weeklyWorkingTime;
	}

	/**
	 * Returns the total vacation time per year of the user.
	 *
	 * methodtype get method
	 */
	public int getTotalVacationTime()
	{
		return totalVacationTime;
	}

	/**
	 * Sets the total vacation time per year of the user if greater than zero.
	 *
	 * methodtype set method
	 * pre totalVacationTime > 0;
	 * post totalVacationTime successfully set.
	 */
	public void setTotalVacationTime(int totalVacationTime)
	{
		assert totalVacationTime > 0 : "vacation time has to be greater than zero";
		this.totalVacationTime = totalVacationTime;
	}

	/**
	 * Returns the vacation time the user has already taken.
	 *
	 * methodtype get method
	 */
	public int getCurrentVacationTime()
	{
		return currentVacationTime;
	}

	/**
	 * Sets the vacation time the user has already taken if greater or equals zero.
	 *
	 * methodtype set method
	 * pre currentVacationTime >= 0;
	 * post currentVacationTime successfully set.
	 */
	public void setCurrentVacationTime(int currentVacationTime)
	{
		assert currentVacationTime >= 0 :
				"Current vacation time has to be positive or zero if the user wasn't in " + "vacation yet.";
		this.currentVacationTime = currentVacationTime;
	}

	/**
	 * Returns the current overtime of the user.
	 *
	 * methodtype get method
	 */
	public int getCurrentOvertime()
	{
		return currentOvertime;
	}

	/**
	 * Sets the users current overtime.
	 *
	 * methodtype set method
	 */
	public void setCurrentOvertime(int currentOvertime)
	{
		this.currentOvertime = currentOvertime;
	}

	/**
	 * Returns the registration date of the user.
	 *
	 * methodtype get method
	 */
	public Date getRegistrationDate()
	{
		return registrationDate;
	}

	/**
	 * Sets the users registration date if not null.
	 *
	 * methodtype set method
	 * pre registrationDate != null;
	 * post registrationDate successfully set.
	 */
	public void setRegistrationDate(Date registrationDate)
	{
		assert registrationDate != null: "registrationDate shouldn't be null!";
		this.registrationDate = registrationDate;
	}
}
