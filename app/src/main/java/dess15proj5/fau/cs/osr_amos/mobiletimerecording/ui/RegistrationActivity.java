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
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AbstractUserProfileFragment;

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
