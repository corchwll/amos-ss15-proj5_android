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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.SessionsDAO;

import java.sql.SQLException;

public class DeleteSessionDialogFragment extends DialogFragment
{
	public interface DeleteSessionDialogFragmentListener
	{
		/**
		 * This method is used when a session is deleted.
		 *
		 * methodtype callback method
		 */
		void onSessionDeleted();
	}

	private long sessionId;
	private DeleteSessionDialogFragmentListener listener;

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
			listener = (DeleteSessionDialogFragmentListener)activity;
		} catch (ClassCastException exception)
		{
			throw new RuntimeException("Activity must implement DeleteSessionDialogFragmentListener");
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
		sessionId = getArguments().getLong("session_id");
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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.delete_session_answer)
			   .setPositiveButton(R.string.delete_session_ok, new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialogInterface, int i)
				   {
					   try
					   {
						   SessionsDAO sessionsDAO = DataAccessObjectFactory.getInstance()
																			.createSessionsDAO(getActivity());
						   sessionsDAO.delete(sessionId);
						   listener.onSessionDeleted();
					   } catch(SQLException e)
					   {
						   Toast.makeText(getActivity(), "Could not delete session due to database errors!",
								   Toast.LENGTH_LONG)
								.show();
					   }
				   }
			   })
			   .setNegativeButton(R.string.delete_session_cancel, new DialogInterface.OnClickListener()
			   {
				   @Override
				   public void onClick(DialogInterface dialogInterface, int i)
				   {
					   dismiss();
				   }
			   });
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}
}
