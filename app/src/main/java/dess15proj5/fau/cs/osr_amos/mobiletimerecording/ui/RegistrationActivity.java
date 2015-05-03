package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RegistrationActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		setClickListener();
		checkIfUserExistsInDatabase();
	}

	private void checkIfUserExistsInDatabase()
	{
		UsersDAO userDao = DataAccessObjectFactory.getInstance().createUsersDAO(this);

		try
		{
			userDao.open();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}

		List<User> users = userDao.listAll();
		if(!users.isEmpty())
		{
			showProjectListActivity();
		}
	}

	private void showProjectListActivity()
	{
		Intent intent = new Intent(this, ProjectListActivity.class);
		startActivity(intent);
	}

	private void setClickListener()
	{
		Button createUserButton = (Button) findViewById(R.id.createUserButton);
		createUserButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				createUserInDatabase();
				showProjectListActivity();
			}

			private void createUserInDatabase()
			{
				UsersDAO userDAO = DataAccessObjectFactory.getInstance().createUsersDAO(getBaseContext());
				try
				{
					userDAO.open();
				} catch(SQLException e)
				{
					e.printStackTrace();
				}

				//TODO will be refactored
				EditText employeeIdAsEditText = (EditText) findViewById(R.id.employeeId);
				Long employeeId = Long.parseLong(employeeIdAsEditText.getText()
																	 .toString());
				String lastName = ((EditText) findViewById(R.id.lastname)).getText().toString();
				String firstName = ((EditText) findViewById(R.id.firstname)).getText().toString();
				int weeklyWorkingTime = tryParseInt(((EditText)findViewById((R.id.weekly_working_time))).getText()
																										.toString());
				int totalVacationTime = tryParseInt(((EditText)findViewById((R.id.total_vacation_time))).getText()
																										.toString());
				int currentVacationTime = tryParseInt(((EditText)findViewById((R.id.current_vacation_time))).getText()
																											.toString());
				int currentOvertime = tryParseInt(((EditText)findViewById((R.id.current_overtime))).getText()
																										.toString());
				Date registrationDate = new Date();

				userDAO.create(employeeId, lastName, firstName, weeklyWorkingTime, totalVacationTime,
						currentVacationTime, currentOvertime, registrationDate);
				userDAO.close();
			}

			private int tryParseInt(String integer)
			{
				try
				{
					return Integer.parseInt(integer);
				} catch(NumberFormatException nfe)
				{
					return 0;
				}
			}
		});
	}
}
