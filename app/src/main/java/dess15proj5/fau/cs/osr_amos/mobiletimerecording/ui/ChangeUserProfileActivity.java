package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.UserProfileActionBarActivity;

public class ChangeUserProfileActivity extends UserProfileActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_user_profile);
		//TODO move it to the superclass
		getWidgets();
	}

	@Override
	protected void runDBTransaction(Long employeeIdAsLong, String lastName, String firstName, int weeklyWorkingTime,
									int totalVacationTime, int currentVacationTime, int currentOvertime)
	{
		//copiye
	}


}
