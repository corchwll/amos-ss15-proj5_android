package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class ProjectListActivity extends ActionBarActivity implements AddProjectDialogFragment.AddProjectDialogListener
{
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
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
						Intent intent = new Intent(getBaseContext(), ChangeUserProfileActivity.class);
						startActivity(intent);
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

	private void showProjectsListFragment()
	{
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		ProjectsListFragment projectsListFragment = new ProjectsListFragment();
		fragmentTransaction.replace(R.id.frameLayout, projectsListFragment);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_project_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }

		switch(item.getItemId())
		{
			case R.id.action_add_project:
				addNewProject();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onDialogPositiveClick(DialogFragment fragment)
	{
		showProjectsListFragment();
	}

	private void addNewProject()
	{
        DialogFragment newFragment = new AddProjectDialogFragment();
        newFragment.show(getFragmentManager(), "dialog");
	}
}
