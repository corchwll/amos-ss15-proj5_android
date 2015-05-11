package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AbstractUserProfileFragment;

import java.util.Date;

public class RegisterUserProfileFragment extends AbstractUserProfileFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.user_profile_registration, container, false);
		getWidgets(scrollView);
		return scrollView;
	}

	@Override
	protected void runDBTransaction(Long employeeIdAsLong, String lastName, String firstName, int weeklyWorkingTime,
									int totalVacationTime, int currentVacationTime, int currentOvertime)
	{
		Date date = new Date();
		userDAO.create(employeeIdAsLong, lastName, firstName, weeklyWorkingTime, totalVacationTime,
				currentVacationTime, currentOvertime, date);
	}
}
