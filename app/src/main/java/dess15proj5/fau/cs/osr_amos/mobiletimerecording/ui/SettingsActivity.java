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
import android.view.MenuItem;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class SettingsActivity extends AppCompatActivity implements SettingsFragment.SettingsFragmentListener
{
	/**
	 * This method is called in the android lifecycle when the fragment is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		initToolbar();
		if(getSupportActionBar() != null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}
		showSettingsFragment();
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
	 * This method is used to display the settings fragment.
	 *
	 * methodtype command method
	 */
	private void showSettingsFragment()
	{
		getFragmentManager().beginTransaction()
							.replace(R.id.settingsFrameLayout, new SettingsFragment())
							.addToBackStack(null)
							.commit();
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
				onBackPressed();
			default:
				return super.onOptionsItemSelected(item);
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
		if(getFragmentManager().getBackStackEntryCount() == 1)
		{
			finishActivityAndShowAnimation();
		}
		else
		{
			getFragmentManager().popBackStack();
		}
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
	 * This method is called from a callback when the user presses a Settings Button
	 *
	 * methodtype command method
	 * @param activityClass Contains the next fragment to be shown
	 */
	@Override
	public void onSettingsButtonPressed(Class<?> activityClass)
	{
		Intent intent = new Intent(getBaseContext(), activityClass);
		startActivity(intent);
		overridePendingTransition(R.animator.fade_in_right, R.animator.empty_animator);
	}
}
