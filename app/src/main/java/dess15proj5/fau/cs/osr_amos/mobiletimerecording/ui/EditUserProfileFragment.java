package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AbstractUserProfileFragment;

import java.util.List;

public class EditUserProfileFragment extends AbstractUserProfileFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.change_user_profile, container, false);
		//TODO is it ok to call this methods here? same in RegisterUserProfileFragment
		getWidgets(scrollView);
		getUserFromDatabase();
		return scrollView;
	}

	@Override
	protected void runDBTransaction(String employeeId, String lastName, String firstName, int weeklyWorkingTime,
									int totalVacationTime, int currentVacationTime, int currentOvertime)
	{
		User user = userDAO.load();
		user.setEmployeeId(employeeId);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setWeeklyWorkingTime(weeklyWorkingTime);
		user.setTotalVacationTime(totalVacationTime);
		user.setCurrentVacationTime(currentVacationTime);
		user.setCurrentOvertime(currentOvertime);
		userDAO.update(user);
	}

	private void getUserFromDatabase()
	{
		List<User> users = userDAO.listAll();
		User user = users.get(0);
		fillWidgetsWithValuesFromDatabase(user);
	}

	private void fillWidgetsWithValuesFromDatabase(User user)
	{
		employeeIdWidget.setText(user.getEmployeeId());
		lastNameWidget.setText(user.getLastName());
		firstNameWidget.setText(user.getFirstName());
		weeklyWorkingTimeWidget.setText(Integer.toString(user.getWeeklyWorkingTime()));
		totalVacationTimeWidget.setText(Integer.toString(user.getTotalVacationTime()));
		currentVacationTimeWidget.setText(Integer.toString(user.getCurrentVacationTime()));
		currentOvertimeWidget.setText(Integer.toString(user.getCurrentOvertime()));
	}
}
