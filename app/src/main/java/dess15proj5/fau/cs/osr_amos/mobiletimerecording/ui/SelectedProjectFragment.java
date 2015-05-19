package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.ProjectTimer;

import java.sql.SQLException;
import java.util.Date;

public class SelectedProjectFragment extends Fragment
{
	private SharedPreferences sharedPref;
	private Long projectId;
	private String projectName;
	private Session session;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		if(getArguments() != null)
		{
			getArgumentsFromBundle();
			saveArgumentsIntoSharedPreferences();
		}
	}

	private void getArgumentsFromBundle()
	{
		projectId = getArguments().getLong("project_id");
		projectName = getArguments().getString("project_name");
	}

	private void saveArgumentsIntoSharedPreferences()
	{
		sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putLong("project_id", projectId);
		editor.putString("project_name", projectName);
		editor.commit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.selected_project, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getArgumentsFromSharedPreferences();

		TextView textView = (TextView) getActivity().findViewById(R.id.name_of_selected_project);
		final ProjectTimer timer = (ProjectTimer)getActivity().findViewById(R.id.timer);
		final Button startStopBtn = (Button)getActivity().findViewById(R.id.startStopBtn);

		if(projectId == -1L && projectName == null)
		{
			textView.setText("No project selected. Please select one in the projects tab.");
		}
		else
		{
			textView.setText(projectName);
			startStopBtn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if(!timer.isRunning())
					{
						startNewSession(startStopBtn, timer);
					} else
					{
						stopCurrentSession(startStopBtn, timer);
					}
				}

				private void startNewSession(Button button, ProjectTimer timer)
				{
					try
					{
						SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance()
																		 .createSessionsDAO(getActivity());
						sessionsDAO.open();
						session = sessionsDAO.create(projectId, new Date());
						sessionsDAO.close();

						timer.start();
						button.setText("Stop");
					} catch(SQLException e)
					{
						Toast.makeText(getActivity(), "Could not start timer due to database errors!", Toast.LENGTH_LONG)
							 .show();
					}
				}

				private void stopCurrentSession(Button button, ProjectTimer timer)
				{
					try
					{
						session.setStopTime(new Date());

						SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance()
																		 .createSessionsDAO(getActivity());
						sessionsDAO.open();
						sessionsDAO.update(session);
						sessionsDAO.close();

						timer.stop();
						button.setText("Start");
					} catch(SQLException e)
					{
						Toast.makeText(getActivity(), "Could not stop timer due to database errors!", Toast.LENGTH_LONG)
							 .show();
					}
				}
			});
		}
	}

	private void getArgumentsFromSharedPreferences()
	{
		SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		projectId = sharedPref.getLong("project_id", -1L);
		projectName = sharedPref.getString("project_name", null);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.menu_selected_project, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.addSession:
				createAddSessionActivity();
				return true;
			case R.id.deleteProject:
				deleteProject();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void createAddSessionActivity()
	{
		Intent intent = new Intent(getActivity(), AddSessionActivity.class);
		intent.putExtra("project_id", projectId);
		getActivity().startActivity(intent);
	}

	private void deleteProject()
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance().createProjectsDAO(getActivity());
		try
		{
			projectsDAO.open();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		projectsDAO.delete(projectId);
		projectsDAO.close();
	}
}
