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
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.CSVCreator;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.CSVMailer;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.StringFormatterForPicker;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DatePickerWithoutDaysDialogFragment extends DialogFragment
{
	private User user;
	private static final String[] recipients = new String[]{"amosteam5@gmail.com"};

	/**
	 * This method is called in the android lifecycle when the fragment is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		loadUserFromDB();

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogLayout = inflater.inflate(R.layout.date_picker_without_days, null);
		final DatePicker datePicker = (DatePicker) dialogLayout.findViewById(R.id.datePickerWithoutDays);
		datePicker.findViewById(Resources.getSystem()
										 .getIdentifier("day", "id", "android")).setVisibility(View.GONE);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(getResources().getString(R.string.datePickerDialogFragmentMessage))
			   .setView(dialogLayout)
			   .setPositiveButton(getResources().getString(R.string.sendCSV), new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialogInterface, int i)
				   {
					   createCSV();
					   sendCSV();
				   }

				   /**
					* Creates a CSV file with the selected month and year of the datePicker
					*
					* methodtype command method
					*/
				   private void createCSV()
				   {
					   CSVCreator csvCreator = new CSVCreator(user, getActivity());
					   try
					   {
						   csvCreator.createCSV(datePicker.getMonth(), datePicker.getYear());
					   } catch(IOException e)
					   {
						   Toast.makeText(getActivity(), "Could not write file to internal storage!", Toast.LENGTH_SHORT).show();
					   } catch(SQLException e)
					   {
						   Toast.makeText(getActivity(), "Database error!", Toast.LENGTH_SHORT).show();
					   }
				   }

				   /**
					* send the created CSV file to the recipients.
					*
					* methodtype command method
					*/
				   private void sendCSV()
				   {
					   int month = datePicker.getMonth();
					   int year = datePicker.getYear();

					   Uri uri = Uri.fromFile(new File(getActivity().getExternalFilesDir(null),
							   CSVCreator.getFileNameFor(month, year)));
					   CSVMailer csvMailer = new CSVMailer(recipients, "CSV for " + StringFormatterForPicker
							   .formatMonth(month) + "." + year, uri, getActivity ());
					   csvMailer.send();
				   }
			   })
			   .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialogInterface, int i)
				   {
					   dismiss();
				   }
			   });
		final AlertDialog dialog = builder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener()
		{
			@Override
			public void onShow(DialogInterface arg0) {
				dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.bluePrimaryColor));
				dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.bluePrimaryColor));
			}
		});
		return dialog;
	}

	/**
	 * This method load the user from db and stores it in the local attribute user
	 *
	 * methodtype command method
	 */
	private void loadUserFromDB()
	{
		UsersDAO usersDAO = null;
		try
		{
			usersDAO = DataAccessObjectFactory.getInstance().createUsersDAO(getActivity().getBaseContext());
		} catch(SQLException e)
		{
			Toast.makeText(getActivity(), "Could not get UsersDAO due to database errors!", Toast.LENGTH_SHORT).show();
		}
		if(usersDAO != null)
		{
			user = usersDAO.load();
		}
	}
}
