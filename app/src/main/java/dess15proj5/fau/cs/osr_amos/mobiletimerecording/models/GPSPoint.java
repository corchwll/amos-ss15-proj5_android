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

	public GPSPoint(double latitude, double longitude)
	{
		assert latitude >= -90.0 && latitude <= 90.0 : "latitude has to be between -90.0 and 90.0";
		assert longitude >= -180.0 && latitude <= 180.0 : "latitude has to be between -180.0 and 180.0";
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		assert latitude >= -90.0 && latitude <= 90.0 : "latitude has to be between -90.0 and 90.0";
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		assert longitude >= -180.0 && latitude <= 180.0 : "latitude has to be between -180.0 and 180.0";
		this.longitude = longitude;
	}
}
