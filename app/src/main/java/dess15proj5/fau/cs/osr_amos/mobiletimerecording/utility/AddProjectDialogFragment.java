package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_project, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Please add new project")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            EditText newProjectId = (EditText)view.findViewById(R.id.addProjectId);
                            Long newProjectIdAsLong = Long.parseLong(newProjectId.getText()
																				 .toString());
                            EditText newProjectName = (EditText) view.findViewById(R.id.addProjectName);
                            String newProjectNameAsString = newProjectName.getText().toString();
                            createNewProject(newProjectIdAsLong, newProjectNameAsString);
                        } catch (SQLException e) {
                            Toast.makeText(getActivity(), "Could not create new project " +
                                    "due to database errors!", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );
                return builder.create();
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
