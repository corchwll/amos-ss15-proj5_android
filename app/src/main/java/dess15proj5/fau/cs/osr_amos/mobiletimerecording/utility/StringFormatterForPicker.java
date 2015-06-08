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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

public class StringFormatterForPicker
{
	/**
	 * This method is used to convert an integer to a string in a given formatting.
	 *
	 * @param month the integer that should be converted
	 * @return the string representation of the given integer
	 * methodtype conversion method
	 */
	public static String formatMonth(int month)
	{
		String returnString = Integer.toString(month);
		if (month < 10)
		{
			returnString = "0" + month;
		}
		return returnString;
	}
}
