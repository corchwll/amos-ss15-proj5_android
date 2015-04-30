package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;

import java.util.Date;
import java.util.List;

public interface UsersDAO
{
	public User create(long employeeId, String lastName, String firstName, int weeklyWorkingTime,
					   int totalVacationTime, int currentVacationTime, int currentOvertimer, Date registrationDate);
	public void update(User user);
	public User load(long userId);
	public void delete(long userId);
	public List<User> listAll();
}
