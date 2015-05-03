package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class EditProjectActivity extends ActionBarActivity
{
	private long projectId;
	private DatePicker datePicker;
	private NumberPicker numberPicker;
	private Button confirmButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_project);
		getDataFromIntent();
		getWidgetsAndInitialize();

		confirmButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int year = datePicker.getYear();
				int month = datePicker.getMonth();
				int day = datePicker.getDayOfMonth();
				int hours = numberPicker.getValue();
				//TODO hour set zero here
				Date date = createDate(year, month, day, 0);
				SessionsDAO sessionDAO = DataAccessObjectFactory.getInstance().createSessionsDAO(getBaseContext());
				try
				{
					sessionDAO.open();

				} catch(SQLException e)
				{
					e.printStackTrace();
				}
				Session session = sessionDAO.create(projectId, date);
				Date updatedDate = createDate(year, month, day, hours);
				session.setStopTime(updatedDate);
				sessionDAO.update(session);
				sessionDAO.close();
				finish();
			}

			private Date createDate(int year, int month, int day, int hours)
			{
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH, day);
				cal.set(Calendar.HOUR_OF_DAY, hours);
				return cal.getTime();
			}
		});
	}

	private void getDataFromIntent()
	{
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		projectId = data.getLong("project_id");
	}

	private void getWidgetsAndInitialize()
	{
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		numberPicker = (NumberPicker) findViewById(R.id.hoursToBeAdded);
		confirmButton = (Button) findViewById(R.id.confirmProjectChangeButton);

		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(24);
	}
}
