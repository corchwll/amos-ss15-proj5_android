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

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

public class MainActivity extends ActionBarActivity implements AbstractUserProfileFragment.UserProfileFragmentListener,
		ProjectsListFragment.ProjectsListFragmentListener, DeleteSessionDialogFragment.DeleteSessionDialogFragmentListener
{
	private ActionBarDrawerToggle actionBarDrawerToggle;

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
		setContentView(R.layout.main_activity);
		initNavigationDrawer();
		showProjectsListFragment();
	}

	/**
	 * This method initializes the navigation drawer.
	 *
	 * methodtype initialization method
	 */
	private void initNavigationDrawer()
	{
		String[] drawerListItems = getResources().getStringArray(R.array.drawer_list_items);
		final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
		final ListView leftDrawer = (ListView) findViewById(R.id.left_drawer);
		leftDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerListItems));
		leftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				switch(position)
				{
					//					Time Recording
					case 0:
						showSelectedFragment();
						drawerLayout.closeDrawers();
						break;
					//					Projects
					case 1:
						showProjectsListFragment();
						drawerLayout.closeDrawers();
						break;
					//					Dashboard
					case 2:
						drawerLayout.closeDrawers();
						break;
					//					Settings
					case 3:
						Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
						startActivity(intent);
						drawerLayout.closeDrawers();
						break;
				}
			}
		});
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
		actionBarDrawerToggle.syncState();
		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	/**
	 * This method creates the ProjectListFragment and then displays it.
	 *
	 * methodtype command method
	 */
	private void showProjectsListFragment()
	{
		ProjectsListFragment projectsListFragment = new ProjectsListFragment();
		showFragment(projectsListFragment, getResources().getString(R.string.project_list));
	}

	/**
	 * This method creates the SelectedFragment and then displays it.
	 *
	 * methodtype command method
	 */
	private void showSelectedFragment()
	{
		SelectedProjectFragment selectedProjectFragment = new SelectedProjectFragment();
		showFragment(selectedProjectFragment, getResources().getString(R.string.overview));
	}

	/**
	 * This method is used to replace the current fragment with a new one so that the new one can be displayed.
	 *
	 * @param fragment the new fragment that should be shown
	 * @param title the title for the action bar
	 * methodtype command method
	 */
	private void showFragment(Fragment fragment, String title)
	{
		getSupportActionBar().setTitle(title);
		getFragmentManager().beginTransaction()
							.replace(R.id.frameLayout, fragment)
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
		if(actionBarDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * This method should be called when an user profile is saved. It will then load the ProjectListFragment.
	 *
	 * methodtype command method
	 */
	@Override
	public void onUserProfileSaved()
	{
		showProjectsListFragment();
	}

	/**
	 * This method is called after a project from the project list was selected. The fragment will then show the time
	 * recording button and information about the chosen project.
	 *
	 * @param selectedProject the selected project from the list
	 * methodtype command method
	 */
	@Override
	public void projectSelected(Project selectedProject)
	{
		SelectedProjectFragment selectedProjectFragment = new SelectedProjectFragment();
		Bundle args = new Bundle();
		args.putString("project_id", selectedProject.getId());
		args.putString("project_name", selectedProject.getName());
		selectedProjectFragment.setArguments(args);
		getSupportActionBar().setTitle(getResources().getString(R.string.overview));
		getFragmentManager().beginTransaction()
							.replace(R.id.frameLayout, selectedProjectFragment)
							.commit();
	}

	/**
	 * This method is called by a callback if the ok button of the delete session dialog is clicked.
	 *
	 * methodtype command method
	 */
	@Override
	public void onSessionDeleted()
	{
		showSelectedFragment();
	}
}
