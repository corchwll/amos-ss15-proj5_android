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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;

import java.util.List;

public class EditUserProfileFragment extends AbstractUserProfileFragment
{
	/**
	 * This method is called in the android lifecycle when the view of the fragment is created.
	 *
	 * @param inflater this param contains the layout inflater which is used to generate the gui
	 * @param container the container is used by the layout inflater
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.change_user_profile, container, false);
		setWidgets(scrollView);
		loadUserFromDatabase();
		return scrollView;
	}

	/**
	 * This method is used to store an user object to the database.
	 *
	 * @param employeeId the id of the user that has to be stored
	 * @param lastName the last name of the user that has to be stored
	 * @param firstName the first name of the user that has to be stored
	 * @param weeklyWorkingTime the hours a user has to work per week
	 * @param totalVacationTime the amount of days a user can take off per year
	 * @param currentVacationTime the amount of days a user has left to take off this year
	 * @param currentOvertime the overtime the user actual has
	 * methodtype command method
	 */
	@Override
	protected void runDBTransaction(String employeeId, String lastName, String firstName, int weeklyWorkingTime,
									int totalVacationTime, int currentVacationTime, int currentOvertime)
	{
		User user = userDAO.load();
		user.setEmployeeId(employeeId);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setWeeklyWorkingTime(weeklyWorkingTime);
		user.setTotalVacationTime(totalVacationTime);
		user.setCurrentVacationTime(currentVacationTime);
		user.setCurrentOvertime(currentOvertime);
		userDAO.update(user);
	}

	/**
	 * This method is used to load the default user from database and fill the widgets with the information.
	 *
	 * methodtype initialization method
	 */
	private void loadUserFromDatabase()
	{
		List<User> users = userDAO.listAll();
		User user = users.get(0);
		fillWidgetsWithValuesFromDatabase(user);
	}

	/**
	 * This method fills the widgets with the information from the default user.
	 *
	 * @param user the default user object
	 * methodtype initialization method
	 */
	private void fillWidgetsWithValuesFromDatabase(User user)
	{
		employeeIdWidget.setText(user.getEmployeeId());
		lastNameWidget.setText(user.getLastName());
		firstNameWidget.setText(user.getFirstName());
		weeklyWorkingTimeWidget.setText(Integer.toString(user.getWeeklyWorkingTime()));
		totalVacationTimeWidget.setText(Integer.toString(user.getTotalVacationTime()));
		currentVacationTimeWidget.setText(Integer.toString(user.getCurrentVacationTime()));
		currentOvertimeWidget.setText(Integer.toString(user.getCurrentOvertime()));
	}
}
