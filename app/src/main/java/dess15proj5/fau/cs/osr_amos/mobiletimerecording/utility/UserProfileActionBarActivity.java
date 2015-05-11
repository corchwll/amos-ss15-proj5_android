package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.MainActivity;

import java.sql.SQLException;

public abstract class UserProfileActionBarActivity extends ActionBarActivity
{
//duplicated attributes!!
	protected EditText employeeId;
	protected EditText lastNameWidget;
	protected EditText firstNameWidget;
	protected EditText weeklyWorkingTimeWidget;
	protected EditText totalVacationTimeWidget;
	protected EditText currentVacationTimeWidget;
	protected EditText currentOvertimeWidget;
	protected MenuItem saveUserProfileBtn;

	protected UsersDAO userDAO;
	//copied
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getUserDAO();
	}


	//copied
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_user_profile_registration, menu);
		saveUserProfileBtn = menu.findItem(R.id.action_save_user_profile);
		return true;
	}

	//copiyed
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.action_save_user_profile:
				if(isEmployeeIdValid())
				{
					prepareDBConnection();
					getInputsFromWidgets();
					closeDBConnection();
					showProjectListActivity();
				}
				else
				{
					employeeId.setError("EmployeeID must be a five-digit number.");
					employeeId.requestFocus();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//copied
	private void getInputsFromWidgets()
	{
		Long employeeIdAsLong = Long.parseLong(getStringFromWidget(employeeId));
		String lastName = getStringFromWidget(lastNameWidget);
		String firstName = getStringFromWidget(firstNameWidget);

		int weeklyWorkingTime = getIntFromWidget(weeklyWorkingTimeWidget);
		int totalVacationTime = getIntFromWidget(totalVacationTimeWidget);
		int currentVacationTime = getIntFromWidget(currentVacationTimeWidget);
		int currentOvertime = getIntFromWidget(currentOvertimeWidget);
		runDBTransaction(employeeIdAsLong, lastName, firstName, weeklyWorkingTime, totalVacationTime,
				currentVacationTime, currentOvertime);
	}

	//copiey
	private int getIntFromWidget(EditText editText)
	{
		int integer = 0;
		try
		{
			integer = Integer.parseInt(editText.getText().toString());
		} catch(NumberFormatException nfe)
		{
		}
		return integer;
	}

	//copieyd
	private String getStringFromWidget(EditText editText)
	{
		return editText.getText().toString();
	}

	//cpiedy
	private void prepareDBConnection()
	{
		try
		{
			userDAO.open();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	//copiyed
	protected abstract void runDBTransaction(Long employeeIdAsLong, String lastName, String firstName, int
			weeklyWorkingTime, int totalVacationTime, int currentVacationTime, int currentOvertime);

	//copiyed
	private void closeDBConnection()
	{
		userDAO.close();
	}


	protected void showProjectListActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	//copied
	private void getUserDAO()
	{
		userDAO = DataAccessObjectFactory.getInstance().createUsersDAO(this);
	}

	//copied
	protected void getWidgets()
	{
		employeeId = (EditText) findViewById(R.id.employeeId);
		lastNameWidget = (EditText) findViewById(R.id.lastname);
		firstNameWidget = (EditText) findViewById(R.id.firstname);
		weeklyWorkingTimeWidget = (EditText) findViewById(R.id.weekly_working_time);
		totalVacationTimeWidget = (EditText) findViewById(R.id.total_vacation_time);
		currentVacationTimeWidget = (EditText) findViewById(R.id.current_vacation_time);
		currentOvertimeWidget = (EditText) findViewById(R.id.current_overtime);
	}
	//cpieyd
	protected boolean isEmployeeIdValid()
	{
		boolean isValid = false;
		String employeeIdAsString = employeeId.getText().toString();
		int lengthOfEmployeeId = employeeIdAsString.length();
		if (lengthOfEmployeeId == 5)
		{
			isValid = true;
		}
		return isValid;
	}
}
