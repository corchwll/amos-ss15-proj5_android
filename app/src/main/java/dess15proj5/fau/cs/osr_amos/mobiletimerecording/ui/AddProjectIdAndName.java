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
import android.widget.EditText;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class AddProjectIdAndName extends AppCompatActivity
{
	private EditText projectIdWidget;
	private EditText projectNameWidget;
	private String projectId;
	private String projectName;

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
		setContentView(R.layout.add_project_name_id);
		initToolbar();
		if(getSupportActionBar() != null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}
		setWidgets();
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
				validateInputs();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void validateInputs()
	{
		projectName = projectNameWidget.getText().toString();
		projectId = projectIdWidget.getText().toString();

		if(projectId.length() == 5)
		{
			//Todo check if id already exists in database
			startNextActivity();
		}
		else
		{
			setErrorMessageToWidget(projectIdWidget, "Your project ID must consist of 5 digits");
		}
	}

	private void startNextActivity()
	{
		Intent intent = new Intent(getBaseContext(), AddProjectFinalDate.class);
		intent.putExtra("project_id", projectId);
		intent.putExtra("project_name", projectName);
		startActivity(intent);
		overridePendingTransition(R.animator.fade_in_right, R.animator.empty_animator);
	}

	private void setErrorMessageToWidget(EditText projectIdWidget, String message)
	{
		projectIdWidget.setError(message);
		projectIdWidget.requestFocus();
	}
}
