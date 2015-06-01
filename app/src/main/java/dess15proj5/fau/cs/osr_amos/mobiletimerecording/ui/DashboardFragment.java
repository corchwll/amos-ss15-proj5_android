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
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.DashboardInformation;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;

import java.sql.SQLException;

public class DashboardFragment extends Fragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.dashboard_fragment, container, false);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		TextView overtime = (TextView) getActivity().findViewById(R.id.overtime_dashboard);
		TextView holidays = (TextView) getActivity().findViewById(R.id.holidays_dashboard);

		UsersDAO userDAO = null;
		try
		{
			userDAO = DataAccessObjectFactory.getInstance().createUsersDAO(getActivity().getBaseContext());
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		User user = userDAO.load();

		DashboardInformation dashboardInformation = new DashboardInformation(user ,getActivity()
				.getBaseContext());
		try
		{
			overtime.setText(dashboardInformation.getOvertime() + " ");
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		try
		{
			holidays.setText(dashboardInformation.getLeftVacationDays() + " / " + user.getTotalVacationTime());
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
