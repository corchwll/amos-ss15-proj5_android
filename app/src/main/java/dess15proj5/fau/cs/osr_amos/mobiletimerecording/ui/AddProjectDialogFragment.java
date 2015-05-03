package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;

import java.sql.SQLException;

public class AddProjectDialogFragment extends DialogFragment
{
	public interface AddProjectDialogListener
	{
		public void onDialogPositiveClick(DialogFragment fragment);
	}

	AddProjectDialogListener callbackListener;
	private View view;
	private EditText projectIdWidget;
	private EditText projectNameWidget;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			callbackListener = (AddProjectDialogListener)activity;
		} catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement AddProjectDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.add_project, null);

		getWidgets();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view)
			   .setTitle("Please add new project")
			   .setPositiveButton("OK", new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialog, int which) { }
			   })
			   .setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialog, int which) { }
			   });

		final AlertDialog dialog = builder.create();
		dialog.show();

//		Overwrite onClick method to set error message if there is a SQLiteConstraintException (projectId
// already in database)
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				boolean wantToCloseDialog = false;
				try
				{
//					TODO NumberFormatException
					Long newProjectIdAsLong = Long.parseLong(projectIdWidget.getText()
																			.toString());
					String newProjectNameAsString = projectNameWidget.getText()
																	 .toString();
					try
					{
						createNewProject(newProjectIdAsLong, newProjectNameAsString);
						wantToCloseDialog = true;
					} catch(CursorIndexOutOfBoundsException e)
					{
						projectIdWidget.setError("ID is already registered in the database.");
					}

					if(wantToCloseDialog)
					{
						dialog.dismiss();
					}

					callbackListener.onDialogPositiveClick(AddProjectDialogFragment.this);
				} catch(SQLException e)
				{
					Toast.makeText(getActivity(), "Could not create new project " + "due to database errors!",
							Toast.LENGTH_LONG)
						 .show();
				}
			}
		});
		return dialog;
	}

	private void getWidgets()
	{
		projectIdWidget = (EditText) view.findViewById(R.id.newProjectId);
		projectNameWidget = (EditText) view.findViewById(R.id.newProjectName);
	}

	private void createNewProject(long projectId, String projectName) throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance()
														 .createProjectsDAO(getActivity());

		projectsDAO.open();
		projectsDAO.create(projectId, projectName);
		projectsDAO.close();
	}
}
