package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class SettingsFragment extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_fragment);
		PreferenceScreen preferenceScreen = (PreferenceScreen) findPreference("changeUserProfile");
		preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
		{
			@Override
			public boolean onPreferenceClick(Preference preference)
			{
				getFragmentManager().beginTransaction().replace(R.id.settingsFrameLayout, new EditUserProfileFragment()).commit();
				return false;
			}
		});
	}
}
