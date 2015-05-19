package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AbstractUserProfileFragment;

public class SettingsActivity extends ActionBarActivity implements AbstractUserProfileFragment.UserProfileFragmentListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		showSettingsFragment();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void showSettingsFragment()
	{
		getFragmentManager().beginTransaction()
							.replace(R.id.settingsFrameLayout, new SettingsFragment())
							.commit();
	}

	@Override
	public void onUserProfileSaved()
	{
		showSettingsFragment();
	}
}
