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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;

import java.sql.SQLException;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements AbstractUserProfileFragment.UserProfileFragmentListener
{
	UsersDAO userDAO;

	/**
	 * This method is called in the android lifecycle when the fragment is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initToolbar();
		try
		{
			userDAO = DataAccessObjectFactory.getInstance().createUsersDAO(this);
			checkIfUserExistsInDatabase();
		} catch(SQLException e)
		{
			Toast.makeText(this, "Could not get UserDAO due to database errors!", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * This method initializes the toolbar.
	 *
	 * methodtype initialization method
	 */
	private void initToolbar()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	/**
	 * This method checks whether a default user exists in database or not. If the user does not exist a registration
	 * process is started.
	 *
	 * methodtype command method
	 */
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

	/**
	 * This method is used to start the main activity.
	 *
	 * method command method
	 */
	private void startMainActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	/**
	 * This method is called from a callback when the user profile is saved.
	 *
	 * methodtype command method
	 */
	@Override
	public void onUserProfileSaved()
	{
		checkIfUserExistsInDatabase();
	}
}
