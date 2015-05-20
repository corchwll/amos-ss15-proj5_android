package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class AddSessionActivity extends ActionBarActivity
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_session_to_project);
		getDataFromIntent();
		getWidgets();
		initEditTextFields();
		initDatePicker();
		initStartTimePicker();
		initStopTimePicker();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_add_session, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.cancel:
				finish();
				return true;
			case R.id.saveNewSession:
				validateStartAndStopTime();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void validateStartAndStopTime()
	{
		Date startDate = createDate(selectedYear, selectedMonth, selectedDay, startHour, startMinute);
		Date stopDate = createDate(selectedYear, selectedMonth, selectedDay, stopHour, stopMinute);
		if(startDate.compareTo(stopDate) < 0)
		{
			saveSessionInDatabase(startDate, stopDate);
		}
		else
		{
			//TODO change to DialogFragment
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setTitle("Error")
					.setMessage("Negative times are not allowed.")
					.setPositiveButton("OK", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	private void saveSessionInDatabase(Date startDate, Date stopDate)
	{
		try
		{
			SessionsDAO sessionDAO = DataAccessObjectFactory.getInstance().createSessionsDAO(getBaseContext());
			sessionDAO.create(projectId, startDate, stopDate);
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		finish();
	}

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

	private void getDataFromIntent()
	{
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		projectId = data.getString("project_id");
	}

	private void getWidgets()
	{
		datePickerEditText = (EditText) findViewById(R.id.datePickerEditText);
		startTimePickerEditText = (EditText) findViewById(R.id.startTimePickerEditText);
		stopTimePickerEditText = (EditText) findViewById(R.id.stopTimePickerEditText);
	}

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

	private void setDatePickerEditText(int year, int month, int day)
	{
		datePickerEditText.setText(formatInt(day) + "." + formatInt(month + 1) + "." + year);
	}

	private void setStartTimePickerEditText(int hour, int minute)
	{
		startTimePickerEditText.setText(formatInt(hour) + ":" + formatInt(minute));
	}

	private void setStopTimePickerEditText(int hour, int minute)
	{
		stopTimePickerEditText.setText(formatInt(hour) +":" + formatInt(minute));
	}

	private String formatInt(int i)
	{
		String returnString = Integer.toString(i);
		if (i < 10)
		{
			returnString = "0" + i;
		}
		return returnString;
	}

	private void initDatePicker()
	{
		final DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener()
		{
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				setDatePickerEditText(year, monthOfYear, dayOfMonth);
				selectedYear = year;
				selectedMonth = monthOfYear + 1;
				selectedDay = dayOfMonth;
			}
		};

		datePickerEditText.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new DatePickerDialog(AddSessionActivity.this, datePickerDialog, selectedYear,
						selectedMonth, selectedDay).show();
			}
		});
	}

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
				new TimePickerDialog(AddSessionActivity.this, startTimePickerDialog,
						startHour, startMinute, true).show();
			}
		});
	}

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
