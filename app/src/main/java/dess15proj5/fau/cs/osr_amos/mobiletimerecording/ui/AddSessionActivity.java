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

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.SessionValidator;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.StringFormatterForPicker;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class AddSessionActivity extends AppCompatActivity
{
	private String projectId;
	private int startHour = 8;
	private int startMinute = 0;
	private int stopHour = 16;
	private int stopMinute = 0;
	private int selectedYear;
	private int selectedMonth;
	private int selectedDay;

	private EditText datePickerEditText;
	private EditText startTimePickerEditText;
	private EditText stopTimePickerEditText;

	/**
	 * This method is called in the android lifecycle when the activity is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_session_to_project);
		initToolbar();
		if(getSupportActionBar() != null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}
		setDataFromIntent();
		setWidgets();
		initEditTextFields();
		initDatePicker();
		initStartTimePicker();
		initStopTimePicker();
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
				validateStartAndStopTime();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This method is used to validate the start and the stop time of a new session. It checks whether the start time
	 * is before the stop time and if not it displays an error message.
	 *
	 * methodtype assertion method
	 */
	private void validateStartAndStopTime()
	{
		Date startDate = createDate(selectedYear, selectedMonth, selectedDay, startHour, startMinute);
		Date stopDate = createDate(selectedYear, selectedMonth, selectedDay, stopHour, stopMinute);
		if(startDate.compareTo(stopDate) < 0)
		{
			SessionValidator sessionValidator  = SessionValidator.getInstance(this);
			Session newSession = new Session(0L, null, startDate, stopDate);
			try
			{
				if(!sessionValidator.isOverlapping(newSession))
				{
					Session cutOffSession = sessionValidator.cutWorkingTime(newSession);
					showToastIfSessionWasCutOff(stopDate, cutOffSession);
					saveSessionInDatabase(cutOffSession.getStartTime(), cutOffSession.getStopTime());
					sessionValidator.checkDay(cutOffSession);
				}
				else
				{
					Toast.makeText(this, getResources().getString(R.string.warningAlreadyRecordedSession),
							Toast.LENGTH_SHORT).show();
				}
			} catch(SQLException e)
			{
				Toast.makeText(this, "Could not validate or cut session due to database errors!", Toast.LENGTH_SHORT)
					 .show();
			}
		} else
		{
			showErrorDialog();
		}
	}

	/**
	 * This method shows a toast if the session was cut off.
	 *
	 * methodtype helper method
	 */
	private void showToastIfSessionWasCutOff(Date stopDate, Session cutOffSession)
	{
		if(!stopDate.equals(cutOffSession.getStopTime()))
		{
			Toast.makeText(this, getResources().getString(R.string.warningSessionWasCutOff),Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * This method shows a error dialog.
	 *
	 * methodtype helper method
	 */
	private void showErrorDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setTitle("Error")
				.setMessage(getResources().getString(R.string.errorMessageAddSession))
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
		final AlertDialog dialog = builder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener()
		{
			@Override
			public void onShow(DialogInterface arg0)
			{
				dialog.getButton(AlertDialog.BUTTON_POSITIVE)
					  .setTextColor(getResources().getColor(R.color.bluePrimaryColor));
			}
		});
		dialog.show();
	}

	/**
	 * This method is used to store a session into the database.
	 *
	 * @param startDate the start time of the new session
	 * @param stopDate the stop time of the new session
	 * methodtype command method
	 */
	private void saveSessionInDatabase(Date startDate, Date stopDate)
	{
		try
		{
			SessionsDAO sessionDAO = DataAccessObjectFactory.getInstance()
															.createSessionsDAO(getBaseContext());
			sessionDAO.create(projectId, startDate, stopDate);
		} catch(SQLException e)
		{
			Toast.makeText(this, "Could not get SessionsDAO due to database errors!",
					Toast.LENGTH_SHORT).show();
		}
		finishActivityAndShowAnimation();
	}

	/**
	 * This method creates a Date object based on the given information.
	 *
	 * @param year the year for the new date
	 * @param month the month for the new date
	 * @param day the day for the new date
	 * @param hours the hours for the new date
	 * @param minute the minutes for the new date
	 * @return the specified date object
	 * methodtype conversion method
	 */
	private Date createDate(int year, int month, int day, int hours, int minute)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * This method sets the value of the attribute projectId based on the intent of this activity.
	 *
	 * methodtype command method
	 */
	private void setDataFromIntent()
	{
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		projectId = data.getString("project_id");
	}

	/**
	 * This method sets the widgets used for the date picker.
	 *
	 * methodtype set method
	 */
	private void setWidgets()
	{
		datePickerEditText = (EditText)findViewById(R.id.datepickerEditText);
		startTimePickerEditText = (EditText)findViewById(R.id.startTimePickerEditText);
		stopTimePickerEditText = (EditText)findViewById(R.id.stopTimePickerEditText);
	}

	/**
	 * This method initializes the edit text fields.
	 *
	 * methodtype initialization method
	 */
	private void initEditTextFields()
	{
		Calendar currentDateAndTime = Calendar.getInstance();
		selectedYear = currentDateAndTime.get(Calendar.YEAR);
		selectedMonth = currentDateAndTime.get(Calendar.MONTH);
		selectedDay = currentDateAndTime.get(Calendar.DAY_OF_MONTH);

		setDatePickerEditText(selectedYear, selectedMonth, selectedDay);
		setStartTimePickerEditText(startHour, startMinute);
		setStopTimePickerEditText(stopHour, stopMinute);
	}

	/**
	 * This method is used to set the edit text of the date picker to the given date.
	 *
	 * @param year the year the date picker should display
	 * @param month the month the date picker should display
	 * @param day the day the date picker should display
	 * methodtype set method
	 */
	private void setDatePickerEditText(int year, int month, int day)
	{
		datePickerEditText.setText(
				StringFormatterForPicker.formatMonth(day) + "." + StringFormatterForPicker.formatMonth(month + 1) + "." +
						year);
	}

	/**
	 * This method sets the edit text for the start time picker based on the given time
	 *
	 * @param hour the hour the time picker should display
	 * @param minute the minute the time picker should display
	 * methodtype set method
	 */
	private void setStartTimePickerEditText(int hour, int minute)
	{
		startTimePickerEditText.setText(
				StringFormatterForPicker.formatMonth(hour) + ":" + StringFormatterForPicker.formatMonth(minute));
	}

	/**
	 * This method sets the edit text for the stop time picker based on the given time
	 *
	 * @param hour the hour the time picker should display
	 * @param minute the minute the time picker should display
	 * methodtype set method
	 */
	private void setStopTimePickerEditText(int hour, int minute)
	{
		stopTimePickerEditText.setText(
				StringFormatterForPicker.formatMonth(hour) + ":" + StringFormatterForPicker.formatMonth(minute));
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
				new DatePickerDialog(AddSessionActivity.this, datePickerDialog, selectedYear, selectedMonth,
						selectedDay).show();
			}
		});
	}

	/**
	 * This method initializes the start time picker and sets an onclickListener to the startTimePickerEditText.
	 *
	 * methodtype initialization method
	 */
	private void initStartTimePicker()
	{
		final TimePickerDialog.OnTimeSetListener startTimePickerDialog = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				setStartTimePickerEditText(hourOfDay, minute);
				startHour = hourOfDay;
				startMinute = minute;
			}
		};

		startTimePickerEditText.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new TimePickerDialog(AddSessionActivity.this, startTimePickerDialog, startHour, startMinute,
						true).show();
			}
		});
	}

	/**
	 * This method initializes the stop time picker and sets an onclickListener to the stopTimePickerEditText.
	 *
	 * methodtype initialization method
	 */
	private void initStopTimePicker()
	{
		final TimePickerDialog.OnTimeSetListener stopTimePickerDialog = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				setStopTimePickerEditText(hourOfDay, minute);
				stopHour = hourOfDay;
				stopMinute = minute;
			}
		};

		stopTimePickerEditText.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new TimePickerDialog(AddSessionActivity.this, stopTimePickerDialog, stopHour, stopMinute, true).show();
			}
		});
	}
}
