package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		adapter = new ProjectArrayAdapter(this);
		ListView projectList = (ListView) findViewById(R.id.project_list);
		projectList.setAdapter(adapter);
		loadProjectList();
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
		switch(item.getItemId())
		{
			case R.id.action_add_project:
				addNewProject();
				return true;
			case R.id.action_change_user_profile:
				Intent intent = new Intent(this, ChangeUserProfileActivity.class);
				startActivity(intent);
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
