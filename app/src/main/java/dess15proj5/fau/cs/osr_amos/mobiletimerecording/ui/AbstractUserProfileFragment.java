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
		/**
		 * This method is used when the user profile is saved.
		 *
		 * methodtype callback method
		 */
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

	/**
	 * This method is called in the android lifecycle when the fragment is displayed.
	 *
	 * @param activity the activity in which context the fragment is attached.
	 * methodtype initialization method
	 */
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

	/**
	 * This method is called in the android lifecycle when the fragment is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getUserDAO();
	}

	/**
	 * This method is used to initialize the userDAO attribute.
	 *
	 * methodtype initialization method
	 */
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

	/**
	 * This method is called in the android lifecycle when a menu is created.
	 *
	 * @param menu the menu item which has to be created
	 * @param inflater contains the information for the layout of the menu
	 * methodtype initialization method
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.menu_user_profile_registration, menu);
		saveUserProfileBtn = menu.findItem(R.id.action_save_user_profile);
	}

	/**
	 * This method is called in the android lifecycle when a menu item is clicked on.
	 *
	 * @param item the item which was targeted
	 * @return true if there was an item clicked
	 * methodtype boolean query method
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.action_save_user_profile:
				extractInputsFromWidgets();
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

	/**
	 * This method is used to if an user has to be stored into the database.
	 *
	 * methodtype command method
	 */
	private void writeIntoDatabase()
	{
		int weeklyWorkingTimeAsInt = Integer.parseInt(weeklyWorkingTime);
		int totalVacationTimeAsInt = Integer.parseInt(totalVacationTime);
		int currentVacationTimeAsInt = Integer.parseInt(currentVacationTime);
		int currentOvertimeAsInt = Integer.parseInt(currentOvertime);
		runDBTransaction(employeeId, lastName, firstName, weeklyWorkingTimeAsInt, totalVacationTimeAsInt,
				currentVacationTimeAsInt, currentOvertimeAsInt);
	}

	/**
	 * This method is used to set the widget attributes.
	 *
	 * @param view the view in which the widgets have to be searched.
	 * methodtype set method
	 */
	protected void setWidgets(View view)
	{
		employeeIdWidget = (EditText) view.findViewById(R.id.employeeId);
		lastNameWidget = (EditText) view.findViewById(R.id.lastname);
		firstNameWidget = (EditText) view.findViewById(R.id.firstname);
		weeklyWorkingTimeWidget = (EditText) view.findViewById(R.id.weekly_working_time);
		totalVacationTimeWidget = (EditText) view.findViewById(R.id.total_vacation_time);
		currentVacationTimeWidget = (EditText) view.findViewById(R.id.current_vacation_time);
		currentOvertimeWidget = (EditText) view.findViewById(R.id.current_overtime);
	}

	/**
	 * This abstract method can be used in subclasses to specify how an user object is stored into the database.
	 *
	 * @param employeeId the id of the user that has to be stored
	 * @param lastName the last name of the user that has to be stored
	 * @param firstName the first name of the user that has to be stored
	 * @param weeklyWorkingTime the hours a user has to work per week
	 * @param totalVacationTime the amount of days a user can take off per year
	 * @param currentVacationTime the amount of days a user has left to take off this year
	 * @param currentOvertime the overtime the user actual has
	 * methodtype command method
	 */
	protected abstract void runDBTransaction(String employeeId, String lastName, String firstName, int
			weeklyWorkingTime, int totalVacationTime, int currentVacationTime, int currentOvertime);

	/**
	 * This method sets the attributes for the user object based on the information given in the input widgets.
	 *
	 * methodtype set method
	 */
	private void extractInputsFromWidgets()
	{
		employeeId = getStringFromWidget(employeeIdWidget);
		lastName = getStringFromWidget(lastNameWidget);
		firstName = getStringFromWidget(firstNameWidget);
		weeklyWorkingTime = getStringFromWidget(weeklyWorkingTimeWidget);
		totalVacationTime = getStringFromWidget(totalVacationTimeWidget);
		currentVacationTime = getStringFromWidget(currentVacationTimeWidget);
		currentOvertime = getStringFromWidget(currentOvertimeWidget);
	}

	/**
	 * This method is used to convert the information from an edit text into a string.
	 *
	 * @param editText the edit text that contains the required information
	 * @return a string representation of the information from the edit text
	 * methodtype conversion method
	 */
	private String getStringFromWidget(EditText editText)
	{
		return editText.getText().toString();
	}

	/**
	 * This method checks whether all mandatory input fields are filled from the user.
	 *
	 * @return true if every mandatory field is filled, false if not
	 * methodtype boolean query method
	 */
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

	/**
	 * This method checks if a given string is not null and if its null it displays it in the given edit text field.
	 *
	 * @param s the string that should not be null
	 * @param editText the edit text which has to display the warning
	 * @return true if the given string is not null, false if it is null
	 * methodtype boolean query method
	 */
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

	/**
	 * This method checks whether the user input is validated or not.
	 *
	 * @return true if every mandatory field is valid, false if not
	 * methodtype boolean query method
	 */
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

	/**
	 * This method checks whether the attribute employeeId is valid or not.
	 *
	 * @return true if it's valid, false if not
	 * methodtype boolean query method
	 */
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

	/**
	 * This method checks whether the attribute weeklyWorkingTime is valid or not.
	 *
	 * @return true if it's valid, false if not
	 * methodtype boolean query method
	 */
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

	/**
	 * This method checks whether the attribute totalVacationTime is valid or not.
	 *
	 * @return true if it's valid, false if not
	 * methodtype boolean query method
	 */
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

	/**
	 * This method checks whether the attribute currentVacationTime is valid or not.
	 *
	 * @return true if it's valid, false if not
	 * methodtype boolean query method
	 */
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
