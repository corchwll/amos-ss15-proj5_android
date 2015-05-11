package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.os.Bundle;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.UserProfileActionBarActivity;

import java.sql.SQLException;
import java.util.List;

public class ChangeUserProfileActivity extends UserProfileActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_user_profile);
		//TODO move it to the superclass
		getWidgets();
		getUserFromDatabase();
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
