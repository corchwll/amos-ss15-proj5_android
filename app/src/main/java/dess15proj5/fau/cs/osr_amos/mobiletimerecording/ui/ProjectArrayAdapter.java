package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

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

		final Project project = getItem(position);
		TextView projectNameView = (TextView) projectRow.findViewById(R.id.projectName);
		projectNameView.setText(project.getName());
		projectNameView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ProjectsListFragment.onItemSelected(project);
			}
		});
		return projectRow;
	}
}
