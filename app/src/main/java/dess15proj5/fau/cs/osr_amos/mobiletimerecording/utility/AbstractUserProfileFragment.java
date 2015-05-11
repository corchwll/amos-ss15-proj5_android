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

	protected EditText employeeId;
	protected EditText lastNameWidget;
	protected EditText firstNameWidget;
	protected EditText weeklyWorkingTimeWidget;
	protected EditText totalVacationTimeWidget;
	protected EditText currentVacationTimeWidget;
	protected EditText currentOvertimeWidget;
	protected MenuItem saveUserProfileBtn;

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
		setHasOptionsMenu(true);
		getUserDAO();
		super.onCreate(savedInstanceState);
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
				if(isEmployeeIdValid())
				{
					prepareDBConnection();
					getInputsFromWidgets();
					closeDBConnection();
					listener.onUserProfileSaved();
				} else
				{
					employeeId.setError("EmployeeID must be a five-digit number.");
					employeeId.requestFocus();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	protected void getWidgets(View view)
	{
		employeeId = (EditText) view.findViewById(R.id.employeeId);
		lastNameWidget = (EditText) view.findViewById(R.id.lastname);
		firstNameWidget = (EditText) view.findViewById(R.id.firstname);
		weeklyWorkingTimeWidget = (EditText) view.findViewById(R.id.weekly_working_time);
		totalVacationTimeWidget = (EditText) view.findViewById(R.id.total_vacation_time);
		currentVacationTimeWidget = (EditText) view.findViewById(R.id.current_vacation_time);
		currentOvertimeWidget = (EditText) view.findViewById(R.id.current_overtime);
	}

	private void getUserDAO()
	{
		userDAO = DataAccessObjectFactory.getInstance().createUsersDAO(getActivity());
	}

	protected abstract void runDBTransaction(Long employeeIdAsLong, String lastName, String firstName, int
			weeklyWorkingTime, int totalVacationTime, int currentVacationTime, int currentOvertime);

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

	private void closeDBConnection()
	{
		userDAO.close();
	}

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
