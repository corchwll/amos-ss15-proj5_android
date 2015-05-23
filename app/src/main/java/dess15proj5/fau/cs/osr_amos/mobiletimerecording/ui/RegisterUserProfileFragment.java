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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

import java.util.Date;

public class RegisterUserProfileFragment extends AbstractUserProfileFragment
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
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.user_profile_registration, container, false);
		setWidgets(scrollView);
		return scrollView;
	}

	/**
	 * This method is used in the android lifecycle when the option menu is prepared.
	 *
	 * @param menu the menu that is prepared
	 * methodtype initialization method
	 */
	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		menu.getItem(0).setVisible(false);
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
		Date date = new Date();
		userDAO.create(employeeId, lastName, firstName, weeklyWorkingTime, totalVacationTime,
				currentVacationTime, currentOvertime, date);
	}
}
