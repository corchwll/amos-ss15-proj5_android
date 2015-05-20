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

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;

import java.sql.SQLException;
import java.util.List;

public class RegistrationActivity extends ActionBarActivity implements AbstractUserProfileFragment.UserProfileFragmentListener
{
	UsersDAO userDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		try
		{
			userDAO = DataAccessObjectFactory.getInstance().createUsersDAO(this);
			checkIfUserExistsInDatabase();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void checkIfUserExistsInDatabase()
	{
		List<User> users = userDAO.listAll();
		if(!users.isEmpty())
		{
			startMainActivity();
		}
		else
		{
			RegisterUserProfileFragment fragment = new RegisterUserProfileFragment();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frameLayout, fragment);
			fragmentTransaction.commit();
		}
	}

	private void startMainActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void onUserProfileSaved()
	{
		startMainActivity();
	}
}
