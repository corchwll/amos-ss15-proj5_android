package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersDAOImpl extends AbstractDAO implements UsersDAO
{
	private String[] allColumns =
			{PersistenceHelper.USERS_ID, PersistenceHelper.USERS_LAST_NAME, PersistenceHelper.USERS_FIRST_NAME,
					PersistenceHelper.USERS_WEEKLY_WORKING_TIME, PersistenceHelper.USERS_TOTAL_VACATION_TIME,
					PersistenceHelper.USERS_CURRENT_VACATION_TIME, PersistenceHelper.USERS_CURRENT_OVERTIME,
					PersistenceHelper.USERS_REGISTRATION_DATE};

	public UsersDAOImpl(Context context)
	{
		persistenceHelper = new PersistenceHelper(context);
	}

	@Override
	public User create(long employeeId, String lastName, String firstName, int weeklyWorkingTime, int totalVacationTime,
					   int currentVacationTime, int currentOvertime, Date registrationDate)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.USERS_ID, employeeId);
		values.put(PersistenceHelper.USERS_LAST_NAME, lastName);
		values.put(PersistenceHelper.USERS_FIRST_NAME, firstName);
		values.put(PersistenceHelper.USERS_WEEKLY_WORKING_TIME, weeklyWorkingTime);
		values.put(PersistenceHelper.USERS_TOTAL_VACATION_TIME, totalVacationTime);
		values.put(PersistenceHelper.USERS_CURRENT_VACATION_TIME, currentVacationTime);
		values.put(PersistenceHelper.USERS_CURRENT_OVERTIME, currentOvertime);
		values.put(PersistenceHelper.USERS_REGISTRATION_DATE, registrationDate.getTime());
		long insertId = database.insert(PersistenceHelper.TABLE_USERS, null, values);

		Cursor cursor = database.query(PersistenceHelper.TABLE_USERS, allColumns, PersistenceHelper.USERS_ID +
				" = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		User newUser = cursorToUser(cursor);
		cursor.close();
		return newUser;
	}

	@Override
	public void update(User user)
	{
		ContentValues values = new ContentValues();
		values.put(PersistenceHelper.USERS_ID, user.getEmployeeId());
		values.put(PersistenceHelper.USERS_LAST_NAME, user.getLastName());
		values.put(PersistenceHelper.USERS_FIRST_NAME, user.getFirstName());
		values.put(PersistenceHelper.USERS_WEEKLY_WORKING_TIME, user.getWeeklyWorkingTime());
		values.put(PersistenceHelper.USERS_TOTAL_VACATION_TIME, user.getTotalVacationTime());
		values.put(PersistenceHelper.USERS_CURRENT_VACATION_TIME, user.getCurrentVacationTime());
		values.put(PersistenceHelper.USERS_CURRENT_OVERTIME, user.getCurrentOvertime());

		database.update(PersistenceHelper.TABLE_USERS, values,
				PersistenceHelper.USERS_ID + " = " + user.getEmployeeId(), null);
	}

	@Override
	public User load(long userId)
	{
		Cursor cursor =	database.query(PersistenceHelper.TABLE_USERS, allColumns, PersistenceHelper.USERS_ID +
						" = " + userId,	null, null, null, null);
		cursor.moveToFirst();
		User user = cursorToUser(cursor);
		cursor.close();
		return user;
	}

	@Override
	public void delete(long userId)
	{
		database.delete(PersistenceHelper.TABLE_USERS, PersistenceHelper.USERS_ID + " = " + userId, null);
	}

	@Override
	public List<User> listAll()
	{
		List<User> users = new ArrayList<>();

		Cursor cursor = database.query(PersistenceHelper.TABLE_USERS, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			User user = cursorToUser(cursor);
			users.add(user);
			cursor.moveToNext();
		}

		cursor.close();
		return users;
	}

	private User cursorToUser(Cursor cursor)
	{
		User user = new User();
		user.setEmployeeId(cursor.getLong(0));
		user.setLastName(cursor.getString(1));
		user.setFirstName(cursor.getString(2));
		user.setWeeklyWorkingTime(cursor.getInt(3));
		user.setTotalVacationTime(cursor.getInt(4));
		user.setCurrentVacationTime(cursor.getInt(5));
		user.setCurrentOvertime(cursor.getInt(6));
		user.setRegistrationDate(new Date(cursor.getLong(7)));
		return user;
	}
}
