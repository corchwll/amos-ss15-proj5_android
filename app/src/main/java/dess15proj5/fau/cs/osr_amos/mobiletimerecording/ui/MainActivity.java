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
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AbstractUserProfileFragment;

public class MainActivity extends ActionBarActivity implements  AbstractUserProfileFragment.UserProfileFragmentListener
{
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initNavigationDrawer();
		ProjectsListFragment projectsListFragment = new ProjectsListFragment();
		showFragment(projectsListFragment);
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
//					Project overview
					case 0:
						drawerLayout.closeDrawers();
						break;
//					Project list
					case 1:
						showProjectsListFragment();
						drawerLayout.closeDrawers();
						break;
//					Change user profile
					case 2:
						EditUserProfileFragment editUserProfileFragment = new EditUserProfileFragment();
						showFragment(editUserProfileFragment);
						drawerLayout.closeDrawers();
						break;
//					Settings
					case 3:
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

	private void showFragment(Fragment fragment)
	{
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

	private void showProjectsListFragment()
	{
		ProjectsListFragment projectsListFragment = new ProjectsListFragment();
		showFragment(projectsListFragment);
	}
}
