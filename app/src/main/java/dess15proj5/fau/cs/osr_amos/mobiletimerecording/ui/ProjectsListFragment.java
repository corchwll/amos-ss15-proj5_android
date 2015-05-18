package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;

import java.sql.SQLException;
import java.util.List;

public class ProjectsListFragment extends ListFragment implements AddProjectDialogFragment.AddProjectDialogListener
{
	private ListView projectList;
	private ProjectArrayAdapter adapter;
	private static ProjectsListFragmentListener listener;

	public interface ProjectsListFragmentListener
	{
		void projectSelected(Project selectedProject);
	}

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

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		projectList = (ListView) inflater.inflate(R.layout.projects_list_fragment, container, false);
		setAdapterToProjectList();
		loadProjectList();
		return projectList;
	}

	private void setAdapterToProjectList()
	{
		adapter = new ProjectArrayAdapter(getActivity());
		projectList.setAdapter(adapter);
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
			Toast.makeText(getActivity(), "Could not load project list due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	private List<Project> loadAllProjects() throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance().createProjectsDAO(getActivity());
		projectsDAO.open();
		List<Project> projects = projectsDAO.listAll();
		projectsDAO.close();
		return projects;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.menu_project_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.action_add_project:
				addNewProject();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void addNewProject()
	{
		AddProjectDialogFragment newFragment = new AddProjectDialogFragment();
		newFragment.setAddProjectDialogListener(this);
		newFragment.show(getFragmentManager(), "dialog");
	}

	@Override
	public void onDialogPositiveClick()
	{
		loadProjectList();
	}

	public static void onItemSelected(Project selectedProject)
	{
		listener.projectSelected(selectedProject);
	}
}
