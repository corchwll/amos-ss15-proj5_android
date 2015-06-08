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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.StringFormatterForPicker;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class AddProjectDialogFragment extends DialogFragment
{
	public interface AddProjectDialogListener
	{
		/**
		 * This method is used when a new project is added.
		 *
		 * methodtype callback method
		 */
		void onDialogPositiveClick();
	}

	AddProjectDialogListener callbackListener;
	private View view;
	private EditText projectIdWidget;
	private EditText projectNameWidget;
	private TextView addFinalDateQuestion;
	private CheckBox checkBox;
	private LinearLayout datePickerView;
	private boolean datePickerViewVisible = false;
	private EditText datePickerEditText;

	private Calendar cal = Calendar.getInstance();

	/**
	 * This method sets an AddProjectDialogListener to the callbackListener attribute.
	 *
	 * @param listener the AddProjectDialogListener that should be used
	 * methodtype set method
	 */
	public void setAddProjectDialogListener(AddProjectDialogListener listener)
	{
		callbackListener = listener;
	}

	/**
	 * This method is called in the android lifecycle when the fragment is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.add_project, null);

		setWidgets();
		setOnClickListenerToTextView();
		initCheckBox();
		setActualDateToAttributes();
		initDatePickerEditText();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view)
			   .setTitle("Please add new project")
			   .setPositiveButton("OK", new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialog, int which)
				   {
				   }
			   })
			   .setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialog, int which)
				   {
				   }
			   });

		final AlertDialog dialog = builder.create();
		dialog.show();

		// Overwrite onClick method to set error message if there is a SQLiteConstraintException (projectId
		// already in database)
		dialog.getButton(AlertDialog.BUTTON_POSITIVE)
			  .setOnClickListener(new View.OnClickListener()
			  {
				  @Override
				  public void onClick(View v)
				  {
					  boolean wantToCloseDialog = false;
					  try
					  {
						  String projectId = projectIdWidget.getText()
															.toString();
						  boolean isEmpty = projectId.isEmpty();
						  if(!isEmpty && projectId.length() == 5)
						  {
							  String newProjectNameAsString = projectNameWidget.getText()
																			   .toString();
							  try
							  {
								  createNewProject(projectId, newProjectNameAsString);
								  wantToCloseDialog = true;
							  } catch(SQLiteConstraintException e)
							  {
								  setErrorMessageToWidget(projectIdWidget, "ID is already registered in the database.");
							  }

							  if(wantToCloseDialog)
							  {
								  dialog.dismiss();
							  }

							  callbackListener.onDialogPositiveClick();
						  } else
						  {
							  setErrorMessageToWidget(projectIdWidget, "Your project ID must consist of 5 digits");
						  }
					  } catch(SQLException e)
					  {
						  Toast.makeText(getActivity(), "Could not create new project " + "due to database errors!",
								  Toast.LENGTH_SHORT).show();
					  }
				  }

				  private void setErrorMessageToWidget(EditText projectIdWidget, String message)
				  {
					  projectIdWidget.setError(message);
					  projectIdWidget.requestFocus();
				  }
			  });

		return dialog;
	}

	/**
	 * This method is used to set the widget attributes.
	 *
	 * methodtype set method
	 */
	private void setWidgets()
	{
		projectIdWidget = (EditText) view.findViewById(R.id.newProjectId);
		projectNameWidget = (EditText) view.findViewById(R.id.newProjectName);
		addFinalDateQuestion = (TextView) view.findViewById(R.id.addFinalDateQuestion);
		checkBox = (CheckBox) view.findViewById(R.id.checkboxFinalDate);
		datePickerView = (LinearLayout) view.findViewById(R.id.datePickerView);
		datePickerEditText = (EditText) view.findViewById(R.id.datePickerShowDate);
	}

	/**
	 * This method sets an onClickListener to the addFinalDateQuestion
	 *
	 * methodtype initialization method
	 */
	private void setOnClickListenerToTextView()
	{
		addFinalDateQuestion.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				performClick();
				boolean checkboxStatus = checkBox.isChecked();
				checkBox.setChecked(!checkboxStatus);
			}
		});
	}


	/**
	 * This method initializes the checkbox and sets an onclickListener.
	 *
	 * methodtype initialization method
	 */
	private void initCheckBox()
	{
		checkBox.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				performClick();
			}
		});
	}

	/**
	 * This method is fired if a user clicks the view
	 *
	 * methodtype helper method
	 */
	private void performClick()
	{
		if(!datePickerViewVisible)
		{
			datePickerView.setVisibility(View.VISIBLE);
			datePickerViewVisible = !datePickerViewVisible;
		} else
		{
			datePickerView.setVisibility(View.GONE);
			datePickerViewVisible = !datePickerViewVisible;
		}
	}

	/**
	 * This method sets the actual date to attributes.
	 *
	 * methodtype set method
	 */
	private void setActualDateToAttributes()
	{
		setDatePickerEditText(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * This method initializes the edit text of the date picker.
	 *
	 * methodtype initialization method
	 */
	private void initDatePickerEditText()
	{
		final DatePickerDialog.OnDateSetListener datePickerdialog = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, monthOfYear);
				cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				setDatePickerEditText(year, monthOfYear, dayOfMonth);
			}
		};
		datePickerEditText.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new DatePickerDialog(getActivity(), datePickerdialog, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
						cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	/**
	 * This method sets the edit text of the date picker.
	 *
	 * @param year the year of the date that should be set
	 * @param month the month of the date that should be set
	 * @param day the day of the date that should be set
	 * methodtype set method
	 */
	private void setDatePickerEditText(int year, int month, int day)
	{
		datePickerEditText.setText(
				StringFormatterForPicker.formatMonth(day) + "." + StringFormatterForPicker.formatMonth(month + 1) + "." +
						year);
	}

	/**
	 * This method creates a new project and stores it to the database.
	 *
	 * @param projectId the projectId for the new project
	 * @param projectName the name for the new project
	 * @throws SQLException
	 * methodtype command method
	 */
	private void createNewProject(String projectId, String projectName) throws SQLException
	{
		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance()
														 .createProjectsDAO(getActivity());
		Date date = new Date(0L);

		if(datePickerViewVisible)
		{
			date = cal.getTime();
		}
		projectsDAO.create(projectId, projectName, date, true, false, true);
	}
}
