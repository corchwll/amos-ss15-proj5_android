package dess15proj5.fau.cs.osr_amos.mobiletimerecording;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AddProjectDialogFragment;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.ProjectButton;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.ProjectTimer;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ProjectList extends ActionBarActivity implements AddProjectDialogFragment.AddProjectDialogListener
{
	private TableLayout tableLayout;
	private List<Project> projects;
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

	@Override
	public void onDialogPositiveClick(DialogFragment fragment)
	{
		TableLayout tableRow = (TableLayout)findViewById(R.id.project_list);
		tableRow.removeAllViews();
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
			projects = loadAllProjects();
			for(Project project : projects)
			{
				createTableRow(project);
			}
		} catch(SQLException e)
		{
			Toast.makeText(context, "Could not load project list due to database errors!", Toast.LENGTH_LONG).show();
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

		ProjectButton button = ((ProjectButton)row.findViewById(R.id.startButton));
		button.setProject(project);
		initializeStartButton(button, (ProjectTimer)row.findViewById(R.id
				.timer));
		tableLayout.addView(row);
		tableLayout.requestLayout();
	}

	/**
	 * @methodtype command method
	 */
	private void initializeStartButton(final ProjectButton button, final ProjectTimer timer)
	{
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!timer.isRunning())
				{
					startNewSession(button, timer);
				} else
				{
					stopCurrentSession(button, timer);
				}
			}
		});
	}

	private void startNewSession(ProjectButton button, ProjectTimer timer)
	{
		try
		{
			SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance().createSessionsDAO(context);
			sessionsDAO.open();
			Session session = sessionsDAO.create(button.getProject().getId(), new Date());
			sessionsDAO.close();

			timer.start();
			button.setText("Stop");
			button.setCurrentSession(session);
		} catch(SQLException e)
		{
			Toast.makeText(context, "Could not start timer due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	private void stopCurrentSession(ProjectButton button, ProjectTimer timer)
	{
		try
		{
			button.getCurrentSession().setStopTime(new Date());

			SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance()
															 .createSessionsDAO(context);
			sessionsDAO.open();
			sessionsDAO.update(button.getCurrentSession());
			sessionsDAO.close();

			timer.stop();
			button.setText("Start");
		} catch(SQLException e)
		{
			Toast.makeText(context, "Could not stop timer due to database errors!", Toast.LENGTH_LONG).show();
		}
	}
}
