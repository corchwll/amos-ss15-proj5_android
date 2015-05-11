package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.AbstractUserProfileFragment;

import java.sql.SQLException;
import java.util.List;

public class EditUserProfileFragment extends AbstractUserProfileFragment
{

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.change_user_profile, container, false);
		getWidgets(scrollView);
		getUserFromDatabase();
		return scrollView;
	}

	@Override
	protected void runDBTransaction(Long employeeIdAsLong, String lastName, String firstName, int weeklyWorkingTime,
									int totalVacationTime, int currentVacationTime, int currentOvertime)
	{
		List<User> userList = userDAO.listAll();
		//TODO only get one user
		User user = userList.get(0);
		user.setEmployeeId(employeeIdAsLong);
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
		try
		{
			userDAO.open();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}

		List<User> users = userDAO.listAll();
		userDAO.close();
		User user = users.get(0);
		fillWidgetsWithValuesFromDatabase(user);
	}

	private void fillWidgetsWithValuesFromDatabase(User user)
	{
		employeeId.setText(Long.toString(user.getEmployeeId()));
		lastNameWidget.setText(user.getLastName());
		firstNameWidget.setText(user.getFirstName());
		weeklyWorkingTimeWidget.setText(Integer.toString(user.getWeeklyWorkingTime()));
		totalVacationTimeWidget.setText(Integer.toString(user.getTotalVacationTime()));
		currentVacationTimeWidget.setText(Integer.toString(user.getCurrentVacationTime()));
		currentOvertimeWidget.setText(Integer.toString(user.getCurrentOvertime()));
	}
}
