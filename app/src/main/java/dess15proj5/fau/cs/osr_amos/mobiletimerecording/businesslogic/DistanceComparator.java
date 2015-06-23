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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.businesslogic;

import android.location.Location;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Project>
{
	private Location currentLocation;

	public DistanceComparator(Location currentLocation)
	{
		this.currentLocation = currentLocation;
	}

	@Override
	public int compare(Project project, Project t1)
	{
		float[] resultsForProject = null;
		Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
								project.getPoint().getLatitude(), project.getPoint().getLongitude(), resultsForProject);

		float[] resultsForT1 = null;
		Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
				t1.getPoint().getLatitude(), t1.getPoint().getLongitude(), resultsForT1);

		return (int)(resultsForProject[0] - resultsForT1[0]);
	}
}
