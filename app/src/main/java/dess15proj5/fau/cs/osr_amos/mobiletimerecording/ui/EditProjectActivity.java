package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class EditProjectActivity extends ActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_project);

		Intent intent = getIntent();
		Bundle daten = intent.getExtras();
		final long projectId = daten.getLong("project_id");

		final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
		final EditText hoursToBeAdded = (EditText) findViewById(R.id.hoursToBeAdded);
		Button confirmButton = (Button) findViewById(R.id.confirmProjectChangeButton);
		confirmButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int year = datePicker.getYear();
				int month = datePicker.getMonth();
				int day = datePicker.getDayOfMonth();
				int hours = Integer.parseInt(hoursToBeAdded.getText()
														   .toString());
				//TODO hour set zero here
				Date date = createDate(year, month, day, 0);
				SessionsDAO sessionDAO = DataAccessObjectFactory.getInstance().createSessionsDAO(getBaseContext());
				try
				{
					sessionDAO.open();
					Session session = sessionDAO.create(projectId, date);
					Date updatedDate = createDate(year, month, day, hours);
					session.setStopTime(updatedDate);
					sessionDAO.update(session);
					sessionDAO.close();
				} catch(SQLException e)
				{
					e.printStackTrace();
				}
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
}
