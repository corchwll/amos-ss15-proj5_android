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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class AddProjectFinalDate extends AppCompatActivity
{
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
		setContentView(R.layout.add_project_finaldate);
		initToolbar();
		if(getSupportActionBar() != null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}
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
		inflater.inflate(R.menu.menu_next, menu);
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
			case R.id.action_next:
				startNextActivity();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void startNextActivity()
	{
		Intent intent = getIntent();
		String projectId = intent.getStringExtra("project_id");
		String projectName = intent.getStringExtra("project_name");

		DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAddProject);
		int year = datePicker.getYear();
		int month = datePicker.getMonth();
		int day = datePicker.getDayOfMonth();

		Intent newIntent = new Intent(getBaseContext(), AddProjectMapActivity.class);
		newIntent.putExtra("project_id", projectId);
		newIntent.putExtra("project_name", projectName);
		newIntent.putExtra("final_date_year", year);
		newIntent.putExtra("final_date_month", month);
		newIntent.putExtra("final_date_day", day);
		startActivity(newIntent);
		overridePendingTransition(R.animator.fade_in_right, R.animator.empty_animator);
	}
}
