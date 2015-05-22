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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.user_profile_registration, container, false);
		getWidgets(scrollView);
		return scrollView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		menu.getItem(0).setVisible(false);
	}

	@Override
	protected void runDBTransaction(String employeeId, String lastName, String firstName, int weeklyWorkingTime,
									int totalVacationTime, int currentVacationTime, int currentOvertime)
	{
		Date date = new Date();
		userDAO.create(employeeId, lastName, firstName, weeklyWorkingTime, totalVacationTime,
				currentVacationTime, currentOvertime, date);
	}
}
