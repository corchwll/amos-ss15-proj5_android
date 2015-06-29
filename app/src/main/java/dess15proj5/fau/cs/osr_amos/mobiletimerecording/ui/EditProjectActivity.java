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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.GPSPoint;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class EditProjectActivity extends AddProjectActivity
{
	private Project project;
	private ProjectsDAO projectsDAO;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getDataFromIntent();
	}

	private void getDataFromIntent()
	{
		Intent intent = getIntent();
		String project_id = intent.getStringExtra("project_id");
		try
		{
			projectsDAO = DataAccessObjectFactory.getInstance().createProjectsDAO(this);
			project = projectsDAO.load(project_id);
			setWidgetsWithDataFromIntent();
		} catch(SQLException e)
		{
			Toast.makeText(this, "Could not load project due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	private void setWidgetsWithDataFromIntent()
	{
		//id
		projectIdWidget.setText(project.getId());
		projectNameWidget.setText(project.getName());

		//name
		projectIdWidget.setFocusable(false);
		projectIdWidget.setClickable(false);

		//final date
		Date date = project.getFinalDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		selectedYear = cal.get(Calendar.YEAR);
		selectedMonth = cal.get(Calendar.MONTH);
		selectedDay = cal.get(Calendar.DAY_OF_MONTH);
		setDatePickerEditText(selectedYear, selectedMonth, selectedDay);

		//location
		lat = project.getPoint().getLatitude();
		lng = project.getPoint().getLongitude();
		locationEditText.setText(String.valueOf(lat) + "\n" + String.valueOf(lng));
	}

	/**
	 * This method sets an onclickListener to it. If EditText is clicked a new activity is started
	 *
	 * methodtype initialization method
	 */
	@Override
	protected void initLocationEditText()
	{
		locationEditText.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(getBaseContext(), EditProjectMapActivity.class);
				intent.putExtra("lat", lat);
				intent.putExtra("lng", lng);
				startActivityForResult(intent, AddProjectActivity.LOCATION_INTENT_IDENTIFIER);
				overridePendingTransition(R.animator.fade_in_right, R.animator.empty_animator);
			}
		});
	}

	/**
	 * Stores the edited project into database.
	 *
	 * @throws SQLException
	 * methodtype command method
	 */
	protected void writeProjectInDb() throws SQLException
	{
		project.setName(projectNameWidget.getText().toString());
		project.setFinalDate(getDate());
		project.setPoint(new GPSPoint(lat, lng));
		projectsDAO.update(project);
	}
}
