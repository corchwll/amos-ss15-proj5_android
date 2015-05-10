package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;

import java.sql.SQLException;
import java.util.List;

public class ProjectListActivity extends ActionBarActivity implements AddProjectDialogFragment.AddProjectDialogListener
{
	private ProjectArrayAdapter adapter;
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);

		setAdapterToProjectList();

		setAdapterToNavigationDrawer();

		loadProjectList();
	}

	private void setAdapterToProjectList()
	{
		adapter = new ProjectArrayAdapter(this);
		ListView projectList = (ListView) findViewById(R.id.project_list);
		projectList.setAdapter(adapter);
	}

	private void setAdapterToNavigationDrawer()
	{
		String[] drawerListItems = getResources().getStringArray(R.array.drawer_list_items);
		DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
		final ListView leftDrawer = (ListView) findViewById(R.id.left_drawer);
		leftDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerListItems));
		leftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				switch(position)
				{
					case 1:
						Intent intent = new Intent(getBaseContext(), ChangeUserProfileActivity.class);
						startActivity(intent);
				}
			}
		});
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				 R.string.drawer_open, R.string.drawer_close)
		{
			@Override
			public void onDrawerClosed(View drawerView)
			{
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
			}
		};
		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onDialogPositiveClick(DialogFragment fragment)
	{
		loadProjectList();
	}

	private void addNewProject()
	{
        DialogFragment newFragment = new AddProjectDialogFragment();
        newFragment.show(getFragmentManager(), "dialog");
	}

	private void loadProjectList()
	{
		try
		{
			adapter.clear();
			adapter.addAll(loadAllProjects());
			adapter.notifyDataSetChanged();
		} catch(SQLException e)
		{
			Toast.makeText(this, "Could not load project list due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	private List<Project> loadAllProjects() throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance()
														 .createProjectsDAO(this);

		projectsDAO.open();
		List<Project> projects = projectsDAO.listAll();
		projectsDAO.close();

		return projects;
	}
}
