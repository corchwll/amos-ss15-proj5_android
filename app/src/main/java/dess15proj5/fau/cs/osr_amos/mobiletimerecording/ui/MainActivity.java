package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AbstractUserProfileFragment;

public class MainActivity extends ActionBarActivity implements AbstractUserProfileFragment
		.UserProfileFragmentListener, ProjectsListFragment.ProjectsListFragmentListener
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
//					Change user profile
					case 3:
						EditUserProfileFragment editUserProfileFragment = new EditUserProfileFragment();
						showFragment(editUserProfileFragment, getResources().getString(R.string.settings));
						drawerLayout.closeDrawers();
						break;
//					Settings
					case 4:
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
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.frameLayout, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (actionBarDrawerToggle.onOptionsItemSelected(item))
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
		args.putLong("project_id", selectedProject.getId());
		args.putString("project_name", selectedProject.getName());
		selectedProjectFragment.setArguments(args);
		showFragment(selectedProjectFragment, getResources().getString(R.string.overview));
	}
}
