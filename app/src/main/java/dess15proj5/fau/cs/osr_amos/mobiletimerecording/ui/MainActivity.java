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
		ProjectsListFragment.ProjectsListFragmentListener
{
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initNavigationDrawer();
		showProjectsListFragment();
	}

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

	private void showProjectsListFragment()
	{
		ProjectsListFragment projectsListFragment = new ProjectsListFragment();
		showFragment(projectsListFragment, getResources().getString(R.string.project_list));
	}

	private void showSelectedFragment()
	{
		SelectedProjectFragment selectedProjectFragment = new SelectedProjectFragment();
		showFragment(selectedProjectFragment, getResources().getString(R.string.overview));
	}

	private void showFragment(Fragment fragment, String title)
	{
		getSupportActionBar().setTitle(title);
		getFragmentManager().beginTransaction()
							.replace(R.id.frameLayout, fragment)
							.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(actionBarDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onUserProfileSaved()
	{
		showProjectsListFragment();
	}

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
}
