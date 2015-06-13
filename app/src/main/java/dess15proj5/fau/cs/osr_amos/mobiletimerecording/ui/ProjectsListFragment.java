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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.PersistenceHelper;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;

import java.sql.SQLException;
import java.util.List;

public class ProjectsListFragment extends ListFragment implements AddProjectDialogFragment.AddProjectDialogListener
{
	public static final int POSITION_OF_SPECIAL_PROJECTS_SEPARATOR = 0;
	public static final String SEPARATOR_ID = "-1";

	private ListView projectListView;
	private ImageButton addProjectFAB;
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
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.projects_list_fragment,
				container, false);
		projectListView = (ListView) view.findViewById(android.R.id.list);
		addProjectFAB = (ImageButton) view.findViewById(R.id.addProjectFAB);
		return view;
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
		addProjectsToAdapter();
		addOnClickListenerToFAB();
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
	 * This method adds the projects from the database to the adapter.
	 *
	 * methodtype initialization method
	 */
	private void addProjectsToAdapter()
	{
		try
		{
			adapter.clear();
			List<Project> projectList = getProjectsFromDB();
			addSeparatorsToProjectList(projectList);
			adapter.setProjectList(projectList);
			adapter.addAll(projectList);
			adapter.notifyDataSetChanged();
		} catch(SQLException e)
		{
			Toast.makeText(getActivity(), "Could not load project list due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * This method adds separators to the given projectsList
	 *
	 * methodtype command method
	 */
	private void addSeparatorsToProjectList(List<Project> projectList)
	{
		projectList.add(POSITION_OF_SPECIAL_PROJECTS_SEPARATOR, getSeparatorProject(getResources()
				.getString(R.string.separator_special_projects)));
		projectList.add(getPositionOfSeparatorAfterSpecialProjects(), getSeparatorProject(getResources()
				.getString(R.string.separator_added_projects)));
	}

	/**
	 * Returns a project, that is used to display a separator
	 *
	 * @param caption sets the text, which is shown in the separator
	 * methodtype get method
	 */
	private Project getSeparatorProject(String caption)
	{
		Project separator = new Project();
		separator.setName(caption);
		separator.setId(SEPARATOR_ID);
		return separator;
	}

	/**
	 * Returns the position of the second separator, which is shown after the special projects
	 *
	 * methodtype get method
	 */
	private int getPositionOfSeparatorAfterSpecialProjects()
	{
		return PersistenceHelper.getDefaultProjectsAsList().size() + 1;
	}

	/**
	 * This method loads all projects from the database.
	 *
	 * @return the list of projects from the database
	 * @throws SQLException
	 * methodtype get method
	 */
	private List<Project> getProjectsFromDB() throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance().createProjectsDAO(getActivity());
		return projectsDAO.listAll();
	}

	/**
	 * This method sets an onClickListener to the Floating Action Button to create a new project
	 *
	 * methodtype initialization method
	 */
	private void addOnClickListenerToFAB()
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
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				adapter.getFilter().filter(newText);
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
		AddProjectDialogFragment newFragment = new AddProjectDialogFragment();
		newFragment.setAddProjectDialogListener(this);
		newFragment.show(getFragmentManager(), "dialog");
	}

	/**
	 * This method is called by a callback if the ok button of the add projects dialog is clicked.
	 *
	 * methodtype command method
	 */
	@Override
	public void onDialogPositiveClick()
	{
		addProjectsToAdapter();
	}
}
