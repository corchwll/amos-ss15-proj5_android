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
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.StringFormatterForPicker;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class AddProjectActivity extends AppCompatActivity
{
	public final static int LOCATION_INTENT_IDENTIFIER = 1;
	protected EditText projectIdWidget;
	protected EditText projectNameWidget;
	protected EditText datePickerEditText;
	protected EditText locationEditText;
	protected double lat;
	protected double lng;
	protected int selectedYear;
	protected int selectedMonth;
	protected int selectedDay;
	private boolean isDateSet = false;

	/**
	 * This method is called in the android lifecycle when the activity is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_project);
		initToolbar();
		if(getSupportActionBar() != null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}
		setWidgets();
		initDatePicker();
		initLocationEditText();
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
	 * This method is used to set the widget attributes.
	 *
	 * methodtype set method
	 */
	private void setWidgets()
	{
		projectIdWidget = (EditText)findViewById(R.id.newProjectId);
		projectNameWidget = (EditText)findViewById(R.id.newProjectName);
		datePickerEditText = (EditText) findViewById(R.id.datePickerAddProject);
		locationEditText = (EditText) findViewById(R.id.locationEditText);
	}

	/**
	 * This method initializes the date picker and sets an onclickListener to the datePickerEditText.
	 *
	 * methodtype initialization method
	 */
	private void initDatePicker()
	{
		final DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener()
		{
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				isDateSet = true;
				setDatePickerEditText(year, monthOfYear, dayOfMonth);
				selectedYear = year;
				selectedMonth = monthOfYear;
				selectedDay = dayOfMonth;
			}
		};

		datePickerEditText.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Calendar cal = Calendar.getInstance();
				new DatePickerDialog(AddProjectActivity.this, datePickerDialog, cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	/**
	 * This method is used to set the edit text of the date picker to the given date.
	 *
	 * @param year the year the date picker should display
	 * @param month the month the date picker should display
	 * @param day the day the date picker should display
	 * methodtype set method
	 */
	protected void setDatePickerEditText(int year, int month, int day)
	{
		datePickerEditText.setText(
				StringFormatterForPicker.formatMonth(day) + "." + StringFormatterForPicker.formatMonth(month + 1) +
						"." + year);
	}

	/**
	 * This method sets an onclickListener to it. If EditText is clicked a new activity is started
	 *
	 * methodtype initialization method
	 */
	protected void initLocationEditText()
	{
		locationEditText.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(getBaseContext(), AddProjectMapActivity.class);
				startActivityForResult(intent, LOCATION_INTENT_IDENTIFIER);
				overridePendingTransition(R.animator.fade_in_right, R.animator.empty_animator);
			}
		});
	}

	/**
	 * Called when an activity you launched exits, giving you the requestCode you started it with, the resultCode it
	 * returned, and any additional data from it.
	 *
	 * methodtype command method
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
			case LOCATION_INTENT_IDENTIFIER:
				if(resultCode == Activity.RESULT_OK)
				{
					lat = data.getDoubleExtra("lat", 1000.0);
					lng = data.getDoubleExtra("lng", 1000.0);
					locationEditText.setText(String.valueOf(lat) + "\n" + String.valueOf(lng));
				}
				break;
		}
	}

	/**
	 *  Called when the activity has detected the user's press of the back key.
	 *
	 * methodtype command method
	 */
	@Override
	public void onBackPressed()
	{
		finishActivityAndShowAnimation();
	}

	/**
	 *  Finish the activity and shows an animation
	 *
	 * methodtype command method
	 */
	private void finishActivityAndShowAnimation()
	{
		super.finish();
		overridePendingTransition(R.animator.empty_animator, R.animator.fade_out_right);
	}

	/**
	 * This method is called in the android lifecycle when a menu is created.
	 *
	 * @param menu the menu item which has to be created
	 * methodtype initialization method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_add, menu);
		return true;
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
			case android.R.id.home:
				finishActivityAndShowAnimation();
				return true;
			case R.id.action_save_new_item:
				validateInputs();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This method is used to validate the projectID
	 *
	 * methodtype assertion method
	 */
	private void validateInputs()
	{
		String projectId = projectIdWidget.getText().toString();

		if(projectId.length() == 5)
		{
			try
			{
				writeProjectInDb();
				finishActivityAndShowAnimation();
			} catch(SQLiteConstraintException e)
			{
				setErrorMessageToWidget(projectIdWidget, "ID is already registered in the database.");
			} catch(SQLException e)
			{
				Toast.makeText(this, "Could not create new project due to database errors!",
						Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			setErrorMessageToWidget(projectIdWidget, "Your project ID must consist of 5 digits");
		}
	}

	/**
	 * This method creates a new project and stores it to the database.
	 *
	 * @throws SQLException
	 * methodtype command method
	 */
	protected void writeProjectInDb() throws SQLException
	{
		String projectId = projectIdWidget.getText().toString();
		String projectName = projectNameWidget.getText().toString();

		Date date = getDate();

		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance().createProjectsDAO(getBaseContext());
		projectsDAO.create(projectId, projectName, date, true, false, true, lat, lng);
	}

	/**
	 * Initialize a date object with class members and returns it.
	 *
	 * methodtype get method
	 */
	protected Date getDate()
	{
		Calendar cal = Calendar.getInstance();
		if(isDateSet)
		{
			cal.set(Calendar.YEAR, selectedYear);
			cal.set(Calendar.MONTH, selectedMonth);
			cal.set(Calendar.DAY_OF_MONTH, selectedDay);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		}
		else
		{
			cal.setTime(new Date(Long.MAX_VALUE));
		}
		return cal.getTime();
	}

	/**
	 * Sets an error message to a widget
	 *
	 * @param projectIdWidget EditText on which the error message should appear
	 * @param message given message that should be shown
	 *
	 * methodtype helper method
	 */
	protected void setErrorMessageToWidget(EditText projectIdWidget, String message)
	{
		projectIdWidget.setError(message);
		projectIdWidget.requestFocus();
	}
}
