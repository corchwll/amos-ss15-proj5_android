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
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class CSVCreator
{
	public static final String filePrefix = "recordings_";
	private static final String separator = ";";

	/**
	 * This method generates the file name for a given year and month.
	 *
	 * @param month the month for which the csv file should exist
	 * @param year the year for which the csv file should exist
	 * @return the file name based on the given year and month
	 * methodtype conversion method
	 */
	public static String getFileNameFor(int month, int year)
	{
		return filePrefix + year + "_" + month + ".csv";
	}

	private User user;
	private Context context;

	/**
	 * This constructor is only used for unit testing.
	 *
	 * methodtype constructor
	 */
	protected CSVCreator()
	{}

	/**
	 * This is the constructor for the creation of CSVCreator objects.
	 *
	 * @param user the user for which the data was recorded
	 * @param context the application context under which this object is created
	 * methodtype constructor
	 */
	public CSVCreator(User user, Context context)
	{
		this.user = user;
		this.context = context;
	}

	/**
	 * This method can be used to create a csv file for a given month and year.
	 *
	 * @param month the month for which the csv file should be created
	 * @param year the year for which the csv file should be created
	 * @throws IOException in case of error during file writing
	 * @throws SQLException in case of database error
	 * methodtype helper method
	 */
	public void createCSV(int month, int year) throws IOException, SQLException
	{
		List<Project> projects = DataAccessObjectFactory.getInstance().createProjectsDAO(context).listAll();

		File file = new File(context.getExternalFilesDir(null), getFileNameFor(month, year));
		FileOutputStream outputStream = new FileOutputStream(file);
		DataOutputStream out = new DataOutputStream(outputStream);

		writeFileHeader(out, month, year);
		writeDataHeader(out, projects);
		writeData(out, projects, month, year);
	}

	/**
	 * This method writes the file header into the given FileWriter object for the given month and year. Month starts
	 * counting at zero like java.util.Calendar does it.
	 *
	 * @param out the stream for the csv file
	 * @param month the month for which the data is printed as csv file
	 * @param year the year for which the data is printed as csv file
	 * @throws IOException in case of error during writing
	 * methodtype command method
	 */
	protected void writeFileHeader(DataOutputStream out, int month, int year) throws IOException
	{
		out.writeBytes(user.getFirstName());
		out.writeBytes(separator);
		out.writeBytes(user.getLastName());
		out.writeBytes(separator);
		out.writeBytes(month + 1 + "");
		out.writeBytes(separator);
		out.writeBytes(year + "");
		out.writeBytes("\n");
		out.flush();
	}

	/**
	 * This method writes the data header into the given FileWriter object using the given projects in the list.
	 *
	 * @param out the stream for the csv file
	 * @param projects the projects for which data was recorded for this csv file
	 * @throws IOException in case of error during writing
	 * methodtype command method
	 */
	protected void writeDataHeader(DataOutputStream out, List<Project> projects) throws IOException
	{
		out.writeBytes("Date" + separator);
		for(int i = 0; i < projects.size(); i++)
		{
			if(i > 0)
			{
				out.writeBytes(separator);
			}
			out.writeBytes(projects.get(i)
								   .getId());
		}
		out.writeBytes("\n");
		out.flush();
	}

	/**
	 * This method is used to write the recorded data into the csv file. There will be one column for every projectID
	 * and each line contains the minutes worked for each date.
	 *
	 * @param out the stream for the csv file
	 * @param projects the projects for which data was recorded for this csv file
	 * @param month the month for which the csv file should be created
	 * @param year the year for which the csv file should be created
	 * @throws IOException in case of error during writing
	 * @throws SQLException in case of error during database queries
	 * methodtype command method
	 */
	protected void writeData(DataOutputStream out, List<Project> projects, int month, int year)
			throws IOException, SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1, 0, 0);
		Date start = cal.getTime();

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(start);
		cal2.add(Calendar.MONTH, 1);
		cal2.add(Calendar.MINUTE, -1);
		Date stop = cal2.getTime();

		Map<String,List<Session>> projectMap = getDataForProjectsSinceDate(projects, start);

		while(cal.getTime().before(stop))
		{
			out.writeBytes(cal.getTime()
							  .toString() + separator);

			for(int i = 0; i < projects.size(); i++)
			{
				if(i > 0)
				{
					out.writeBytes(separator);
				}
				int minutes = getTimeInMinutesForDate(projectMap.get(projects.get(i).getId()), cal.getTime());
				out.writeBytes(minutes + "");
			}

			out.writeBytes("\n");
			cal.add(Calendar.DATE, 1);
		}

		out.flush();
	}

	/**
	 * This method is used to get the data which was recorded for the requested projects since the given date.
	 *
	 * @param projects the projects for which the recorded sessions should be loaded
	 * @param date the date since when the data has to be collected
	 * @return a map containing the projectID and the sessions belonging to this ID
	 * @throws SQLException in case of error during database query
	 * methodtype get method
	 */
	protected Map<String,List<Session>> getDataForProjectsSinceDate(List<Project> projects, Date date)
			throws SQLException
	{
		Map<String,List<Session>> projectMap = new HashMap<>();

		for(Project p : projects)
		{
			List<Session> sessions = DataAccessObjectFactory.getInstance()
															.createSessionsDAO(context)
															.listAllForProjectSinceDate(p.getId(), date);
			projectMap.put(p.getId(), sessions);
		}

		return projectMap;
	}

	/**
	 * This method is used to sum up the minutes of all sessions recorded on the given date.
	 *
	 * @param sessions the sessions which should be checked if any time was recorded for the given date
	 * @param date the date for which the minutes should be summed up
	 * @return the minutes worked on this date
	 * methodtype helper method
	 */
	protected int getTimeInMinutesForDate(List<Session> sessions, Date date)
	{
		int result = 0;

		Calendar calStop = Calendar.getInstance();
		calStop.setTime(date);
		calStop.add(Calendar.DATE, 1);

		for(int i = 0; i < sessions.size(); i++)
		{
			Session s = sessions.get(i);
			if(s.getStartTime().after(date) && s.getStopTime().before(calStop.getTime()))
			{
				result += (s.getStopTime().getTime() - s.getStartTime().getTime())/(1000*60);
			} else if(s.getStartTime().after(calStop.getTime()))
			{
				i = sessions.size(); //skip sessions after given date because they are ordered
			}
		}

		return result;
	}
}
