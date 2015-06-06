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
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVCreator
{
	private User user;
	private Context context;

	public CSVCreator(User user, Context context)
	{
		this.user = user;
		this.context = context;
	}

	public void createCSV()
	{

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
}
