package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;

import java.sql.SQLException;
import java.util.Date;

public class ProjectArrayAdapter extends ArrayAdapter<Project>
{
	public ProjectArrayAdapter(Context context)
	{
		super(context, R.layout.project_row);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View projectRow = inflater.inflate(R.layout.project_row, parent, false);

		final TextView projectNameView = (TextView)projectRow.findViewById(R.id.projectName);
		final ProjectButton button = (ProjectButton)projectRow.findViewById(R.id.startButton);
		final ProjectTimer timer = (ProjectTimer)projectRow.findViewById(R.id.timer);

		Project project = getItem(position);
		projectNameView.setText(project.getName());
		button.setProject(project);
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
		return projectRow;
	}

	private void startNewSession(ProjectButton button, ProjectTimer timer)
	{
		try
		{
			SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance().createSessionsDAO(getContext());
			sessionsDAO.open();
			Session session = sessionsDAO.create(button.getProject().getId(), new Date());
			sessionsDAO.close();

			timer.start();
			button.setText("Stop");
			button.setCurrentSession(session);
		} catch(SQLException e)
		{
			Toast.makeText(getContext(), "Could not start timer due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	private void stopCurrentSession(ProjectButton button, ProjectTimer timer)
	{
		try
		{
			button.getCurrentSession().setStopTime(new Date());

			SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance()
															 .createSessionsDAO(getContext());
			sessionsDAO.open();
			sessionsDAO.update(button.getCurrentSession());
			sessionsDAO.close();

			timer.stop();
			button.setText("Start");
		} catch(SQLException e)
		{
			Toast.makeText(getContext(), "Could not stop timer due to database errors!", Toast.LENGTH_LONG).show();
		}
	}
}
