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

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.DashboardInformation;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;

import java.sql.SQLException;

public class DashboardFragment extends Fragment
{
	private User user;
	private DashboardInformation dashboardInformation;
	private TextView overtime;
	private TextView vacation;

	/**
	 * This method is called in the android lifecycle when the fragment is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.dashboard_fragment, container, false);
	}

	/**
	 * This method is called in the android lifecycle when the fragment is started.
	 *
	 * methodtype initialization method
	 */
	@Override
	public void onStart()
	{
		super.onStart();
		overtime = (TextView) getActivity().findViewById(R.id.overtime_dashboard);
		vacation = (TextView) getActivity().findViewById(R.id.vacation_dashboard);

		loadUserFromDB();
		createDashboardInstance();
		initOvertimeToTextView();
		initVacationToTextView();
	}

	/**
	 * This method load the user from db and stores it in the local attribute user
	 *
	 * methodtype command method
	 */
	private void loadUserFromDB()
	{
		UsersDAO usersDAO = null;
		try
		{
			usersDAO = DataAccessObjectFactory.getInstance().createUsersDAO(getActivity().getBaseContext());
		} catch(SQLException e)
		{
			Toast.makeText(getActivity(), "Could not get UsersDAO due to database errors!",
					Toast.LENGTH_SHORT).show();
		}
		if(usersDAO != null)
		{
			user = usersDAO.load();
		}
	}

	/**
	 * This method creates a dashboardInformation instance
	 *
	 * methodtype helper method
	 */
	private void createDashboardInstance()
	{
		dashboardInformation = new DashboardInformation(user ,getActivity()
				.getBaseContext());
	}

	/**
	 * This method initializes the overtime TextView
	 *
	 * methodtype initialization method
	 */
	private void initOvertimeToTextView()
	{
		try
		{
			overtime.setText(dashboardInformation.getOvertime() + " ");
		} catch(SQLException e)
		{
			Toast.makeText(getActivity(), "Could not get overtime due to database errors!",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * This method initializes the vacation TextView
	 *
	 * methodtype initialization method
	 */
	private void initVacationToTextView()
	{
		try
		{
			vacation.setText(dashboardInformation.getLeftVacationDays() + " / " + user.getTotalVacationTime());
		} catch(SQLException e)
		{
			Toast.makeText(getActivity(), "Could not get vacation due to database errors!",
					Toast.LENGTH_SHORT).show();
		}
	}

}
