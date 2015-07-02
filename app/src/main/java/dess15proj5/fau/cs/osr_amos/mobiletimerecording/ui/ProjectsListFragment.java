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

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.ProjectManager;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

public class ProjectsListFragment extends ListFragment
{
	private ListView projectListView;
	private FloatingActionButton addProjectFAB;
	private ProjectArrayAdapter adapter;
	private static ProjectsListFragmentListener listener;
	private SearchView searchView;

	public interface ProjectsListFragmentListener
	{
		/**
		 * This method is called when a project from the project list was selected.
		 *
		 * @param selectedProject the selected project
		 * methodtype callback method
		 */
		void projectSelected(Project selectedProject);
	}

	/**
	 * This method is called in the android lifecycle when the fragment is displayed.
	 *
	 * @param activity the activity in which context the fragment is attached.
	 * methodtype initialization method
	 */
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			listener = (ProjectsListFragmentListener) activity;
		} catch(ClassCastException exception)
		{
			throw new RuntimeException("Activity must implement SelectedProjectFragmentListener");
		}
	}

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
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.projects_list_fragment, container, false);
		projectListView = (ListView) view.findViewById(android.R.id.list);
		addProjectFAB = (FloatingActionButton) view.findViewById(R.id.addProjectFAB);
		return view;
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
		ProjectManager manager = new ProjectManager(adapter, getActivity());
		manager.loadProjects();
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
		projectListView.setTextFilterEnabled(true);
		setAdapterToProjectList();
		setOnItemClickListenerToListView();
		ProjectManager manager = new ProjectManager(adapter, getActivity());
		manager.loadProjects();
		setClickListenerToFAB();
	}

	/**
	 * This method creates a new ProjectArrayAdapter and sets it to this project list.
	 *
	 * methodtype initialization method
	 */
	private void setAdapterToProjectList()
	{
		adapter = new ProjectArrayAdapter(getActivity());
		projectListView.setAdapter(adapter);
	}

	/**
	 * This method sets an onItemClickListener to the projectListView
	 *
	 * methodtype initialization method
	 */
	private void setOnItemClickListenerToListView()
	{
		projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
			{
				Project project = adapter.getItem(i);
				listener.projectSelected(project);
			}
		});
	}



	/**
	 * This method sets an onClickListener to the Floating Action Button to create a new project
	 *
	 * methodtype initialization method
	 */
	private void setClickListenerToFAB()
	{
		addProjectFAB.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				addNewProject();
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
		getActivity().getMenuInflater().inflate(R.menu.menu_project_list, menu);
		searchView = (SearchView) menu.getItem(0).getActionView();
		addTextChangeListenerToSearchView();
	}

	/**
	 * This method adds a TextChangeListener to the searchView
	 *
	 * methodtype initialization method
	 */
	private void addTextChangeListenerToSearchView()
	{
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				hideSoftwareKeyboard();
				return false;
			}

			/**
			 * Hides the software keyboard after the user clicked the search button
			 *
			 * methodtype command method
			 */
			private void hideSoftwareKeyboard()
			{
				InputMethodManager imm =
						(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				adapter.getFilter()
					   .filter(newText);
				return true;
			}
		});
	}

	/**
	 * This method is used to show an add new project dialog.
	 *
	 * methodtype command method
	 */
	private void addNewProject()
	{
		Intent intent = new Intent(getActivity(), AddProjectActivity.class);
		getActivity().startActivity(intent);
		getActivity().overridePendingTransition(R.animator.fade_in_right, R.animator.empty_animator);
	}
}
