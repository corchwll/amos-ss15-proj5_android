package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.ProjectTimer;

import java.sql.SQLException;
import java.util.Date;

public class ProjectArrayAdapter extends ArrayAdapter<Project>
{
	private Project project;
	private Session session;

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
		final Button editProjectButton = (Button) projectRow.findViewById(R.id.editProjectButton);
		final Button startSessionButton = (Button)projectRow.findViewById(R.id.startButton);
		final ProjectTimer timer = (ProjectTimer)projectRow.findViewById(R.id.timer);

		project = getItem(position);
		projectNameView.setText(project.getName());

		editProjectButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getContext(), EditProjectActivity.class);
				intent.putExtra("project_id", project.getId());
				getContext().startActivity(intent);
			}
		});

		startSessionButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!timer.isRunning())
				{
					startNewSession(startSessionButton, timer);
				} else
				{
					stopCurrentSession(startSessionButton, timer);
				}
			}
		});
		return projectRow;
	}

	private void startNewSession(Button button, ProjectTimer timer)
	{
		try
		{
			SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance().createSessionsDAO(getContext());
			sessionsDAO.open();
			session = sessionsDAO.create(project.getId(), new Date());
			sessionsDAO.close();

			timer.start();
			button.setText("Stop");
		} catch(SQLException e)
		{
			Toast.makeText(getContext(), "Could not start timer due to database errors!", Toast.LENGTH_LONG).show();
		}
	}

	private void stopCurrentSession(Button button, ProjectTimer timer)
	{
		try
		{
			session.setStopTime(new Date());

			SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance()
															 .createSessionsDAO(getContext());
			sessionsDAO.open();
			sessionsDAO.update(session);
			sessionsDAO.close();

			timer.stop();
			button.setText("Start");
		} catch(SQLException e)
		{
			Toast.makeText(getContext(), "Could not stop timer due to database errors!", Toast.LENGTH_LONG).show();
		}
	}
}
