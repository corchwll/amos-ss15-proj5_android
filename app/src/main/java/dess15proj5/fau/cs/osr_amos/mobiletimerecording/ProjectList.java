package dess15proj5.fau.cs.osr_amos.mobiletimerecording;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.ProjectTimer;

import java.sql.SQLException;
import java.util.List;

public class ProjectList extends ActionBarActivity
{
	private TableLayout tableLayout;
	private final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);

		tableLayout = (TableLayout)findViewById(R.id.project_list);
		loadProjectList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_project_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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

	private void addNewProject()
	{
		final EditText input = new EditText(this);
		new AlertDialog.Builder(ProjectList.this).setTitle("Update Status")
												 .setMessage("hi")
												 .setView(input)
												 .setPositiveButton("Ok", new DialogInterface.OnClickListener()
												 {
													 public void onClick(DialogInterface dialog, int whichButton)
													 {
														 try
														 {
															 Project project = createNewProject(input.getText()
																									 .toString());
															 createTableRow(project);
														 } catch(SQLException e)
														 {
															 Toast.makeText(context, "Could not create new project " +
																	 "due to database errors!", Toast.LENGTH_LONG);
														 }

													 }
												 })
												 .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
												 {
													 public void onClick(DialogInterface dialog, int whichButton)
													 {
														 // Do nothing.
													 }
												 })
												 .show();
	}

	private Project createNewProject(String projectName) throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance()
														 .createProjectsDAO(context);

		projectsDAO.open();
		Project project = projectsDAO.create(projectName);
		projectsDAO.close();

		return project;
	}

	private void loadProjectList()
	{
		try
		{
			List<Project> projects = loadAllProjects();
			for(Project project : projects)
			{
				createTableRow(project);
			}
		} catch(SQLException e)
		{
			Toast.makeText(context, "Could not load project list due to database errors!", Toast.LENGTH_LONG);
		}
	}

	private List<Project> loadAllProjects() throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance()
														 .createProjectsDAO(context);

		projectsDAO.open();
		List<Project> projects = projectsDAO.listAll();
		projectsDAO.close();

		return projects;
	}

	private void createTableRow(Project project)
	{
		TableRow row = (TableRow)LayoutInflater.from(ProjectList.this)
											   .inflate(R.layout.project_row, null);
		((TextView)row.findViewById(R.id.projectName)).setText(project.getName());
		initializeStartButton((Button)row.findViewById(R.id.startButton), (ProjectTimer)row.findViewById(R.id.timer));
		tableLayout.addView(row);
		tableLayout.requestLayout();
	}

	/**
	 * @methodtype command method
	 */
	private void initializeStartButton(final Button button, final ProjectTimer timer)
	{
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!timer.isRunning())
				{
					timer.start();
					button.setText("Stop");
				} else
				{
					timer.stop();
					button.setText("Start");
				}
			}
		});
	}
}
