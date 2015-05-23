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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;

public class SelectedProjectFragment extends Fragment
{
	private String projectId;
	private String projectName;
	private Session session;
	private SessionArrayAdapter adapter;

	private TextView textView;
	private ProjectTimer timer;
	private Button startStopBtn;

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
		projectId = getArguments().getString("project_id");
		projectName = getArguments().getString("project_name");
	}

	private void saveArgumentsIntoSharedPreferences()
	{
		SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("project_id", projectId);
		editor.putString("project_name", projectName);
		editor.apply();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.selected_project, container, false);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		addSessionsToAdapter();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getArgumentsFromSharedPreferences();
		getWidgets();
		if(attributesAreNull())
		{
			setTextViewToNoProjectSelected();
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
						session = sessionsDAO.create(projectId, new Date());

						timer.start();
						button.setText("Stop");
					} catch(SQLException e)
					{
						Toast.makeText(getActivity(), "Could not start timer due to database errors!",
								Toast.LENGTH_LONG)
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
						sessionsDAO.update(session);

						timer.stop();
						button.setText("Start");

						addSessionsToAdapter();
					} catch(SQLException e)
					{
						Toast.makeText(getActivity(), "Could not stop timer due to database errors!", Toast.LENGTH_LONG)
							 .show();
					}
				}
			});
		}
		setAdapterToSessionListView();
		addSessionsToAdapter();
	}

	private void setAdapterToSessionListView()
	{
		ListView sessionListView = (ListView) getActivity().findViewById(R.id.sessionList);
		adapter = new SessionArrayAdapter(getActivity());
		sessionListView.setAdapter(adapter);
	}

	private void addSessionsToAdapter()
	{
			adapter.clear();
			adapter.addAll(getSessionsFromDB());
			adapter.notifyDataSetChanged();
	}

	private List<Session> getSessionsFromDB()
	{
		List<Session> sessions = null;
		try
		{
			SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance().createSessionsDAO(getActivity());
			sessions = sessionsDAO.listAllForProject(projectId);
		} catch(SQLException e)
		{
			Toast.makeText(getActivity(), "Could not load project list due to database errors!", Toast.LENGTH_LONG).show();
		}
		return sessions;
	}

	private void getArgumentsFromSharedPreferences()
	{
		SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		projectId = sharedPref.getString("project_id", null);
		projectName = sharedPref.getString("project_name", null);
	}

	private void getWidgets()
	{
		textView = (TextView)getActivity().findViewById(R.id.name_of_selected_project);
		timer = (ProjectTimer)getActivity().findViewById(R.id.timer);
		startStopBtn = (Button)getActivity().findViewById(R.id.startStopBtn);
	}

	private boolean attributesAreNull()
	{
		boolean attributesAreNull = false;
		if(projectId == null && projectName == null)
			attributesAreNull = true;
		return attributesAreNull;
	}

	private void setTextViewToNoProjectSelected()
	{
		textView.setText("No project selected. Please select one in the projects tab.");
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater()
					 .inflate(R.menu.menu_selected_project, menu);
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

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		if(projectId == null && projectName == null)
		{
			menu.getItem(0).setEnabled(false);
			menu.getItem(1).setEnabled(false);
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
		try
		{
			ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance()
															 .createProjectsDAO(getActivity());
			projectsDAO.delete(projectId);
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		setAttributesNull();
		disableMenuItems();
		showProjectsListFragment();
	}

	private void setAttributesNull()
	{
		projectId = null;
		projectName = null;
		saveArgumentsIntoSharedPreferences();
	}

	private void disableMenuItems()
	{
		getActivity().invalidateOptionsMenu();
	}

	private void showProjectsListFragment()
	{
		getFragmentManager().beginTransaction()
							.replace(R.id.frameLayout, new ProjectsListFragment())
							.commit();
	}

}
