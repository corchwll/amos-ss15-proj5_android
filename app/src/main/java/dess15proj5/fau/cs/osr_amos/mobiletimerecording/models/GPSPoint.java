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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;

public class GPSPoint
{
	private double latitude;
	private double longitude;

	/**
	 * This method is used to construct an object of type GPSPoint.
	 *
	 * @param latitude the latitude value for the point
	 * @param longitude the longitude value for the point
	 * methodtype constructor
	 */
	public GPSPoint(double latitude, double longitude)
	{
		assert latitude >= -90.0 && latitude <= 90.0 : "latitude has to be between -90.0 and 90.0";
		assert longitude >= -180.0 && latitude <= 180.0 : "latitude has to be between -180.0 and 180.0";
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * This method returns the value of the latitude attribute.
	 *
	 * @return the double value of the latitude
	 * methodtype get method
	 */
	public double getLatitude()
	{
		return latitude;
	}

	/**
	 * This method sets the value of the latitude attribute if valid value is used.
	 *
	 * @param latitude the value for the latitude attribute
	 * methodtype set method
	 * pre latitude >= -90.0 && latitude <= 90.0
	 * post latitude value successfully set
	 */
	public void setLatitude(double latitude)
	{
		assert latitude >= -90.0 && latitude <= 90.0 : "latitude has to be between -90.0 and 90.0";
		this.latitude = latitude;
	}

	/**
	 * This method returns the value of the longitude attribute.
	 *
	 * @return the double value of the longitude
	 * methodtype get method
	 */
	public double getLongitude()
	{
		return longitude;
	}

	/**
	 * This method sets the value of the longitude attribute if valid value is used.
	 *
	 * @param longitude the value for the longitude attribute
	 * methodtype set method
	 * pre longitude >= -180.0 && longitude <= 180.0
	 * post longitude value successfully set
	 */
	public void setLongitude(double longitude)
	{
		assert longitude >= -180.0 && latitude <= 180.0 : "latitude has to be between -180.0 and 180.0";
		this.longitude = longitude;
	}
}
