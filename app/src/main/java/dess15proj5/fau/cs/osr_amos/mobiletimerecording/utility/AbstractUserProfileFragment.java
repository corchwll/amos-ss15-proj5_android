package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;

import java.sql.SQLException;

public abstract class AbstractUserProfileFragment extends Fragment
{
	public interface UserProfileFragmentListener
	{
		void onUserProfileSaved();
	}

	private UserProfileFragmentListener listener;

	protected EditText employeeIdWidget;
	protected EditText lastNameWidget;
	protected EditText firstNameWidget;
	protected EditText weeklyWorkingTimeWidget;
	protected EditText totalVacationTimeWidget;
	protected EditText currentVacationTimeWidget;
	protected EditText currentOvertimeWidget;
	protected MenuItem saveUserProfileBtn;

	protected String employeeId;
	protected String lastName;
	protected String firstName;
	protected String weeklyWorkingTime;
	protected String totalVacationTime;
	protected String currentVacationTime;
	protected String currentOvertime;

	protected UsersDAO userDAO;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			listener = (UserProfileFragmentListener) activity;
		} catch(ClassCastException exception)
		{
			throw new RuntimeException("Activity must implement UserProfileFragmentListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getUserDAO();
	}

	private void getUserDAO()
	{
		try
		{
			userDAO = DataAccessObjectFactory.getInstance()
											 .createUsersDAO(getActivity());
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.menu_user_profile_registration, menu);
		saveUserProfileBtn = menu.findItem(R.id.action_save_user_profile);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.action_save_user_profile:
				getInputsFromWidgets();
				if(allInputFieldsAreFilledOut() && UserInputIsValidated())
				{
					writeIntoDatabase();
					listener.onUserProfileSaved();
				}
				return true;
			case R.id.cancel:
				//TODO RegistrationActivity can skip registration
				listener.onUserProfileSaved();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void writeIntoDatabase()
	{
		int weeklyWorkingTimeAsInt = Integer.parseInt(weeklyWorkingTime);
		int totalVacationTimeAsInt = Integer.parseInt(totalVacationTime);
		int currentVacationTimeAsInt = Integer.parseInt(currentVacationTime);
		int currentOvertimeAsInt = Integer.parseInt(currentOvertime);
		runDBTransaction(employeeId, lastName, firstName, weeklyWorkingTimeAsInt, totalVacationTimeAsInt,
				currentVacationTimeAsInt, currentOvertimeAsInt);
	}

	protected void getWidgets(View view)
	{
		employeeIdWidget = (EditText) view.findViewById(R.id.employeeId);
		lastNameWidget = (EditText) view.findViewById(R.id.lastname);
		firstNameWidget = (EditText) view.findViewById(R.id.firstname);
		weeklyWorkingTimeWidget = (EditText) view.findViewById(R.id.weekly_working_time);
		totalVacationTimeWidget = (EditText) view.findViewById(R.id.total_vacation_time);
		currentVacationTimeWidget = (EditText) view.findViewById(R.id.current_vacation_time);
		currentOvertimeWidget = (EditText) view.findViewById(R.id.current_overtime);
	}

	protected abstract void runDBTransaction(String employeeId, String lastName, String firstName, int
			weeklyWorkingTime, int totalVacationTime, int currentVacationTime, int currentOvertime);

	private void getInputsFromWidgets()
	{
		employeeId = getStringFromWidget(employeeIdWidget);
		lastName = getStringFromWidget(lastNameWidget);
		firstName = getStringFromWidget(firstNameWidget);
		weeklyWorkingTime = getStringFromWidget(weeklyWorkingTimeWidget);
		totalVacationTime = getStringFromWidget(totalVacationTimeWidget);
		currentVacationTime = getStringFromWidget(currentVacationTimeWidget);
		currentOvertime = getStringFromWidget(currentOvertimeWidget);
	}

	private String getStringFromWidget(EditText editText)
	{
		return editText.getText().toString();
	}

	private boolean allInputFieldsAreFilledOut()
	{
		boolean allFieldsFilledOut = true;
		if(!checkIfStringIsNotNull(employeeId, employeeIdWidget))
			allFieldsFilledOut = false;
		if(!checkIfStringIsNotNull(lastName, lastNameWidget))
			allFieldsFilledOut = false;
		if(!checkIfStringIsNotNull(firstName, firstNameWidget))
			allFieldsFilledOut = false;
		if(!checkIfStringIsNotNull(weeklyWorkingTime, weeklyWorkingTimeWidget))
			allFieldsFilledOut = false;
		if(!checkIfStringIsNotNull(totalVacationTime, totalVacationTimeWidget))
			allFieldsFilledOut = false;
		if(!checkIfStringIsNotNull(currentVacationTime, currentVacationTimeWidget))
			allFieldsFilledOut = false;
		if(!checkIfStringIsNotNull(currentOvertime, currentOvertimeWidget))
			allFieldsFilledOut = false;
		return allFieldsFilledOut;
	}

	private boolean checkIfStringIsNotNull(String s, EditText editText)
	{
		boolean stringIsNotNull = true;
		if(s.isEmpty())
		{
			editText.setError("Please fill out field");
			stringIsNotNull = false;
		}
		return stringIsNotNull;
	}

	protected boolean UserInputIsValidated()
	{
		boolean isValid = true;
		if(!employeeIdIsValid())
			isValid = false;
		if(!weeklyWorkingTimeIsValid())
			isValid = false;
		if(!totalVacationTimeIsValid())
			isValid = false;
		if(!currentVacationTimeIsValid())
			isValid = false;
		return isValid;
	}

	private boolean employeeIdIsValid()
	{
		boolean isValid = true;
		int lengthOfEmployeeId = employeeId.length();
		if(lengthOfEmployeeId != 5)
		{
			employeeIdWidget.setError("Your project ID must consist of 5 digits");
			isValid = false;
		}
		return isValid;
	}

	private boolean weeklyWorkingTimeIsValid()
	{
		boolean isValid = true;
		int weeklyWorkingTimeAsInt = Integer.parseInt(weeklyWorkingTime);
		if(weeklyWorkingTimeAsInt < 10 || weeklyWorkingTimeAsInt > 50)
		{
			weeklyWorkingTimeWidget.setError("10 <= weekly working time <= 50");
			isValid = false;
		}
		return isValid;
	}

	private boolean totalVacationTimeIsValid()
	{
		boolean isValid = true;
		int totalVacationTimeAsInt = Integer.parseInt(totalVacationTime);
		if(totalVacationTimeAsInt > 40)
		{
			totalVacationTimeWidget.setError("Total vacation time <= 40");
			isValid = false;
		}
		return isValid;
	}

	private boolean currentVacationTimeIsValid()
	{
		boolean isValid = true;
		int currentVacationTimeAsInt = Integer.parseInt(currentVacationTime);
		if(currentVacationTimeAsInt > 40)
		{
			currentVacationTimeWidget.setError("Current vacation time <= 40");
			isValid = false;
		}
		return isValid;
	}
}
