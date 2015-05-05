package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
	EditText employeeId;
	EditText lastNameWidget;
	EditText firstNameWidget;
	EditText weeklyWorkingTimeWidget;
	EditText totalVacationTimeWidget;
	EditText currentVacationTimeWidget;
	EditText currentOvertimeWidget;
	Button createUserButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		getWidgets();
		setClickListener();
		addTextChangeListener();
		checkIfUserExistsInDatabase();
	}

	private void getWidgets()
	{
		employeeId = (EditText) findViewById(R.id.employeeId);
		lastNameWidget = (EditText) findViewById(R.id.lastname);
		firstNameWidget = (EditText) findViewById(R.id.firstname);
		weeklyWorkingTimeWidget = (EditText) findViewById(R.id.weekly_working_time);
		totalVacationTimeWidget = (EditText) findViewById(R.id.total_vacation_time);
		currentVacationTimeWidget = (EditText) findViewById(R.id.current_vacation_time);
		currentOvertimeWidget = (EditText) findViewById(R.id.current_overtime);
		createUserButton = (Button) findViewById(R.id.createUserButton);
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
		createUserButton.setEnabled(false);
		createUserButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				createUserProfile();
				showProjectListActivity();
			}

			private void createUserProfile()
			{
				UsersDAO userDAO = DataAccessObjectFactory.getInstance().createUsersDAO(getBaseContext());
				try
				{
					userDAO.open();
				} catch(SQLException e)
				{
					e.printStackTrace();
				}

				Long employeeIdAsLong = Long.parseLong(getStringFromWidget(employeeId));
				String lastName = getStringFromWidget(lastNameWidget);
				String firstName = getStringFromWidget(firstNameWidget);

				int weeklyWorkingTime = getIntFromWidget(weeklyWorkingTimeWidget);
				int totalVacationTime = getIntFromWidget(totalVacationTimeWidget);
				int currentVacationTime = getIntFromWidget(currentVacationTimeWidget);
				int currentOvertime = getIntFromWidget(currentOvertimeWidget);

				userDAO.create(employeeIdAsLong, lastName, firstName, weeklyWorkingTime, totalVacationTime,
						currentVacationTime, currentOvertime, new Date());
				userDAO.close();
			}

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

			private String getStringFromWidget(EditText editText)
			{
				return editText.getText().toString();
			}
		});
	}

	private void addTextChangeListener()
	{
		employeeId.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }

			@Override
			public void afterTextChanged(Editable s)
			{
				String employeeIdAsString = createUserButton.getText().toString();
				if (!employeeIdAsString.isEmpty())
				{
					createUserButton.setEnabled(true);
				}
			}
		});
	}

}
