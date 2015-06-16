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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.PersistenceHelper;
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
	private ListView sessionListView;

	private TextView projectNameTextView;
	private ProjectTimer timer;
	private Button startStopBtn;

	private boolean firstTimeStartup = true;
	private View selectedView;
	private int selectedPosition;

	/**
	 * This method is called in the android lifecycle when the fragment is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		if(getArguments() != null)
		{
			setArgumentsFromBundle();
			saveArgumentsIntoSharedPreferences();
		}
	}

	/**
	 * This method extracts the arguments from the bundle and sets the attributes.
	 *
	 * methodtype command method
	 */
	private void setArgumentsFromBundle()
	{
		projectId = getArguments().getString("project_id");
		projectName = getArguments().getString("project_name");
	}

	/**
	 * This method saves the arguments into shared preferences.
	 *
	 * methodtype command method
	 */
	private void saveArgumentsIntoSharedPreferences()
	{
		SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("project_id", projectId);
		editor.putString("project_name", projectName);
		editor.apply();
	}

	/**
	 * This method is called in the android lifecycle when the view of the fragment is created.
	 *
	 * @param inflater this param contains the layout inflater which is used to generate the gui
	 * @param container the container is used by the layout inflater
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.selected_project, container, false);
	}

	/**
	 * This method is called in the android lifecycle when the application is resumed.
	 *
	 * methodtype command method
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		addSessionsToAdapter();
	}

	/**
	 * This method is called in the android lifecycle when the activity is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setArgumentsFromSharedPreferences();
		setWidgets();
		if(attributesAreNull())
		{
			setTextViewToNoProjectSelected();
		}
		else
		{
			projectNameTextView.setText(projectName);
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
		setClickListenerToListView();
		addSessionsToAdapter();
		setClickListenerToFAB();
	}

	/**
	 * This method is used to set an adapter to a session list view.
	 *
	 * methodtype set method
	 */
	private void setAdapterToSessionListView()
	{
		sessionListView = (ListView) getActivity().findViewById(R.id.sessionList);
		adapter = new SessionArrayAdapter(getActivity(), getFragmentManager());
		sessionListView.setAdapter(adapter);
	}

	/**
	 * This method is used to set a click listener to the list view.
	 *
	 * methodtype set method
	 */
	private void setClickListenerToListView()
	{
		sessionListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
			{
				if(firstTimeStartup)
				{
					selectedView = view;
					firstTimeStartup = false;
				}
				adapter.setSelectedItemPosition(i);
				setPreviousBtnInvisible();
				SessionArrayAdapter.ViewHolder viewHolder = (SessionArrayAdapter.ViewHolder) view.getTag();
				viewHolder.deleteSessionBtn.setVisibility(View.VISIBLE);
				selectedView = view;
				selectedPosition = i;
			}
		});
	}

	/**
	 * This method is used to set the sessionDeleteBtn invisible
	 *
	 * methodtype set method
	 */
	private void setPreviousBtnInvisible()
	{
		if(selectedView != null)
		{
			View view = getViewByPosition(selectedPosition, sessionListView);
			if(view != null)
			{
				(((LinearLayout)view).getChildAt(4)).setVisibility(View.GONE);
			}
		}
	}

	/**
	 * This method is used to get a view of the selected Item
	 *
	 * @param pos specify the position of the session_row which is to be returned
	 * @param listView consist several sessions_rows
	 * @return the session_row on the given position
	 * methodtype get method
	 */
	public View getViewByPosition(int pos, ListView listView)
	{
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition )
		{
			return null;
		} else
		{
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	/**
	 * This method is used to add sessions to the adapter attribute.
	 *
	 * methodtype command method
	 */
	private void addSessionsToAdapter()
	{
		if(!attributesAreNull())
		{
			adapter.clear();
			adapter.addAll(getSessionsFromDB());
			adapter.notifyDataSetChanged();
		}
		else
		{
			setTextViewToNoProjectSelected();
		}
	}

	/**
	 * This method is used to load sessions belonging to the selected project from database.
	 *
	 * @return a list containing all sessions belonging to the selected project
	 * methodtype get method
	 */
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

	/**
	 * This method sets the attributes based on the arguments saved in shared preferences.
	 *
	 * methodtype set method
	 */
	private void setArgumentsFromSharedPreferences()
	{
		SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		projectId = sharedPref.getString("project_id", null);
		projectName = sharedPref.getString("project_name", null);
	}

	/**
	 * This method initializes the widget attributes
	 *
	 * methodtype initialization method
	 */
	private void setWidgets()
	{
		projectNameTextView = (TextView)getActivity().findViewById(R.id.name_of_selected_project);
		timer = (ProjectTimer)getActivity().findViewById(R.id.timer);
		startStopBtn = (Button)getActivity().findViewById(R.id.startStopBtn);
	}

	/**
	 * This method checks whether the attributes are null or not.
	 *
	 * @return true if the attributes are null, false if not
	 * methodtype boolean query method
	 */
	private boolean attributesAreNull()
	{
		boolean attributesAreNull = false;
		if(projectId == null && projectName == null)
			attributesAreNull = true;
		return attributesAreNull;
	}

	/**
	 * This method displays in the text view that no project is selected if there was no project selected.
	 *
	 * methodtype set method
	 */
	private void setTextViewToNoProjectSelected()
	{
		projectNameTextView.setText("No project selected. Please select one in the projects tab.");
	}

	/**
	 * This method sets an onClickListener to the Floating Action Button to create a new session
	 *
	 * methodtype initialization method
	 */
	private void setClickListenerToFAB()
	{
		ImageButton addSessionFAB = (ImageButton) getActivity().findViewById(R.id.addSessionFAB);
		addSessionFAB.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				createAddSessionActivity();
			}
		});
	}

	/**
	 * This method is called in the android lifecycle when a menu is created.
	 *
	 * @param menu the menu item which has to be created
	 * @param inflater contains the information for the layout of the menu
	 * methodtype initialization method
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater()
					 .inflate(R.menu.menu_selected_project, menu);
	}

	/**
	 * This method is called in the android lifecycle when a menu item is clicked on.
	 *
	 * @param item the item which was targeted
	 * @return true if there was an item clicked
	 * methodtype boolean query method
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.deleteProject:
				initConfirmationDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This method is used in the android lifecycle when the option menu is prepared.
	 *
	 * @param menu the menu that is prepared
	 * methodtype initialization method
	 */
	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		if(projectId == null && projectName == null || isSpecialProject())
		{
			menu.getItem(0).setEnabled(false);
		}
	}

	/**
	 * This method checks if the selected project is a special project
	 *
	 * methodtype boolean query method
	 */
	private boolean isSpecialProject()
	{
		boolean isSpecialProject = false;
		List<String> specialProjects = PersistenceHelper.getDefaultProjectsAsList();
		for(String specialProjectId: specialProjects)
		{
			if(specialProjectId.equals(projectId))
			{
				isSpecialProject = true;
			}
		}
		return isSpecialProject;
	}

	/**
	 * This method is used to create the activity to add new sessions.
	 *
	 * methodtype initialization method
	 */
	private void createAddSessionActivity()
	{
		Intent intent = new Intent(getActivity(), AddSessionActivity.class);
		intent.putExtra("project_id", projectId);
		getActivity().startActivity(intent);
		getActivity().overridePendingTransition(R.animator.fade_in_right, R.animator.empty_animator);
	}

	/**
	 * This method initializes a dialog to confirm the deletion of the selected project
	 *
	 * methodtype initialization method
	 */
	private void initConfirmationDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setMessage(getResources().getString(R.string.confirmDeletionDialog) + " " + projectName + "?")
				.setPositiveButton("Delete", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						deleteProject();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
		final AlertDialog dialog = builder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener()
		{
			@Override
			public void onShow(DialogInterface arg0)
			{
				dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources()
						.getColor(R.color.bluePrimaryColor));
				dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources()
						.getColor(R.color.bluePrimaryColor));
			}
		});
		dialog.show();
	}

	/**
	 * This method is used to delete projects from database.
	 *
	 * methodtype command method
	 */
	private void deleteProject()
	{
		try
		{
			ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance()
															 .createProjectsDAO(getActivity());
			projectsDAO.delete(projectId);
		} catch(SQLException e)
		{
			Toast.makeText(getActivity(), "Could not get ProjectsDAO due to database errors!", Toast.LENGTH_SHORT)
				 .show();
		}
		setAttributesNull();
		showProjectsListFragment();
	}

	/**
	 * This method is used to set the attributes to null in case of deletion of the selected project.
	 *
	 * methodtype command method
	 */
	private void setAttributesNull()
	{
		projectId = null;
		projectName = null;
		saveArgumentsIntoSharedPreferences();
	}

	/**
	 * This method is used to show the project list fragment.
	 *
	 * methodtype command method
	 */
	private void showProjectsListFragment()
	{
		getFragmentManager().beginTransaction()
							.replace(R.id.frameLayout, new ProjectsListFragment())
							.commit();
	}
}
