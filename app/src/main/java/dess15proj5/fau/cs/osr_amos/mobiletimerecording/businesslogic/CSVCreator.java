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

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class CSVCreator
{
	private User user;
	private Context context;

	protected CSVCreator()
	{}

	public CSVCreator(User user, Context context)
	{
		this.user = user;
		this.context = context;
	}

	public void createCSV(int month, int year) throws IOException, SQLException
	{
		List<Project> projects = DataAccessObjectFactory.getInstance().createProjectsDAO(context).listAll();

		FileWriter fileWriter = new FileWriter("recordings_" + year + "_" + month);
		writeFileHeader(fileWriter, month, year);
		writeDataHeader(fileWriter, projects);
		writeData(fileWriter, projects, month, year);
	}

	/**
	 * This method writes the file header into the given FileWriter object for the given month and year. Month starts
	 * counting at zero like java.util.Calendar does it.
	 *
	 * @param fileWriter the stream for the csv file
	 * @param month the month for which the data is printed as csv file
	 * @param year the year for which the data is printed as csv file
	 * @throws IOException in case of error during writing
	 * methodtype command method
	 */
	protected void writeFileHeader(FileWriter fileWriter, int month, int year) throws IOException
	{
		fileWriter.write(user.getFirstName());
		fileWriter.write(",");
		fileWriter.write(user.getLastName());
		fileWriter.write(",");
		fileWriter.write(month + 1);
		fileWriter.write(",");
		fileWriter.write(year);
		fileWriter.write("\n");
		fileWriter.flush();
	}

	/**
	 * This method writes the data header into the given FileWriter object using the given projects in the list.
	 *
	 * @param fileWriter the stream for the csv file
	 * @param projects the projects for which data was recorded for this csv file
	 * @throws IOException in case of error during writing
	 * methodtype command method
	 */
	protected void writeDataHeader(FileWriter fileWriter, List<Project> projects) throws IOException
	{
		fileWriter.write("Date,");
		for(Project p : projects)
		{
			fileWriter.write(p.getId());
			fileWriter.write(",");
		}
		fileWriter.write("\n");
		fileWriter.flush();
	}

	protected void writeData(FileWriter fileWriter, List<Project> projects, int month, int year)
			throws IOException, SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1, 0, 0);
		Date start = cal.getTime();

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(start);
		cal2.add(Calendar.MONTH, 1);
		cal2.add(Calendar.DATE, -1);
		Date stop = cal2.getTime();

		Map<String,List<Session>> projectMap = getDataForProjectsSinceDate(projects, start);

		while(cal.getTime().before(stop))
		{
			fileWriter.write(cal.getTime().toString());

			for(Project p : projects)
			{
				int minutes = getTimeInMinutesForDate(projectMap.get(p.getId()), cal.getTime());
				fileWriter.write(minutes + ",");
			}

			fileWriter.write("\n");
			cal.add(Calendar.DATE, 1);
		}

		fileWriter.flush();
	}

	protected Map<String,List<Session>> getDataForProjectsSinceDate(List<Project> projects, Date date)
			throws IOException, SQLException
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
