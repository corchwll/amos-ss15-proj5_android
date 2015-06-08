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

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.CSVCreator;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic.CSVMailer;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.UsersDAO;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SendCSVFragment extends Fragment
{
	private User user;

	/**
	 * This method is called in the android lifecycle when the view of the fragment is created.
	 *
	 * @param inflater this param contains the layout inflater which is used to generate the gui
	 * @param container the container is used by the layout inflater
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.send_csv, container, false);
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
		loadUserFromDB();
		Button sendCSVBtn = (Button) getActivity().findViewById(R.id.sendCSVBtn);
		sendCSVBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CSVCreator csvCreator = new CSVCreator(user, getActivity());
				try
				{
					csvCreator.createCSV(5, 2015);
				} catch(IOException e)
				{
					Toast.makeText(getActivity(), "Could not write file to internal storage!", Toast.LENGTH_SHORT).show();
				} catch(SQLException e)
				{
					Toast.makeText(getActivity(), "Database error!", Toast.LENGTH_SHORT).show();
				}
				Uri uri = Uri.fromFile(new File(getActivity().getExternalFilesDir(null), CSVCreator.getFileNameFor(5,
						2015)));
				CSVMailer csvMailer = new CSVMailer(new String[]{"amosteam5@gmail.com"},"CSV", uri,
						getActivity());
				csvMailer.send();
			}
		});
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
