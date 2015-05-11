package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.UserProfileActionBarActivity;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RegistrationActivity extends UserProfileActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile_registration);
		//TODO move it to the superclass
		getWidgets();
		checkIfUserExistsInDatabase();
	}

	private void checkIfUserExistsInDatabase()
	{
		try
		{
			userDAO.open();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}

		List<User> users = userDAO.listAll();
		if(!users.isEmpty())
		{
			showProjectListActivity();
		}
		userDAO.close();
	}

	@Override
	protected void runDBTransaction(Long employeeIdAsLong, String lastName, String firstName, int weeklyWorkingTime,
									int totalVacationTime, int currentVacationTime, int currentOvertime)
	{
		Date date = new Date();
		userDAO.create(employeeIdAsLong, lastName, firstName, weeklyWorkingTime, totalVacationTime,
				currentVacationTime, currentOvertime, date);
	}
}
