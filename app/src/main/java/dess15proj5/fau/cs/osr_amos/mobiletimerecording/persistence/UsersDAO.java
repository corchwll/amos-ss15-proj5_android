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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.User;

import java.util.Date;
import java.util.List;

public interface UsersDAO extends DAO
{
	/**
	 * This method inserts the given information into the users table and creates an object of type user.
	 *
	 * @param employeeId the id of the new user object
	 * @param lastName the last name of the new user object
	 * @param firstName the first name of the new user object
	 * @param weeklyWorkingTime the hours the new user has to work per week
	 * @param totalVacationTime the amount of days the new user can take off per year
	 * @param currentVacationTime the amount of days the new user has already taken off this year
	 * @param currentOvertime the current overtime the user has
	 * @param registrationDate the registration date when the user started to use the application
	 * @return the required user object is returned
	 * methodtype conversion method (since the given information is converted into an object of type user)
	 */
	User create(String employeeId, String lastName, String firstName, int weeklyWorkingTime,
					   int totalVacationTime, int currentVacationTime, int currentOvertime, Date registrationDate);

	/**
	 * This method is used to update a given user in the database.
	 *
	 * @param user the user which has to be updated.
	 * methodtype command method
	 */
	void update(User user);

	/**
	 * This method loads the default user from the database.
	 *
	 * @return the default user
	 * methodtype query method
	 */
	User load();

	/**
	 * This method loads the user with the given id from the database.
	 *
	 * @param userId the id of the user that should be loaded from database
	 * @return the user matching the given id
	 * methodtype query method
	 */
	User load(String userId);

	/**
	 * This method deletes the user with the given id from the database.
	 *
	 * @param userId the id of the user that should be deleted
	 * methodtype command method
	 */
	void delete(String userId);

	/**
	 * This method loads all users from the database.
	 *
	 * @return a list containing all users
	 * methodtype query method
	 */
	List<User> listAll();
}
