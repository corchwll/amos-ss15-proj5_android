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
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class SettingsFragment extends PreferenceFragment
{
	public interface SettingsFragmentListener
	{
		/**
		 * This method is used when the button changeUserProfile is pressed
		 *
		 * methodtype callback method
		 */
		void onChangeUserProfilePressed();
	}

	SettingsFragmentListener listener;

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
			listener = (SettingsFragmentListener)activity;
		} catch (ClassCastException exception)
		{
			throw new RuntimeException("Activity must implement SettingsFragmentListener");
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
		setHasOptionsMenu(true);
		addPreferencesFromResource(R.xml.settings_fragment);
		PreferenceScreen preferenceScreen = (PreferenceScreen) findPreference("changeUserProfile");
		preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
		{
			@Override
			public boolean onPreferenceClick(Preference preference)
			{
				listener.onChangeUserProfilePressed();
				return false;
			}
		});
	}

}
