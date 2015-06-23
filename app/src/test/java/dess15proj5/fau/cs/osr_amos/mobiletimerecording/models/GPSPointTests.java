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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GPSPointTests
{
	private double lat;
	private double lon;
	private GPSPoint point;

	@Before
	public void setUp() throws Exception
	{
		lat = 30.4;
		lon = 40.4;
		point = new GPSPoint(lat, lon);
	}

	@After
	public void tearDown() throws Exception
	{
		lat = 0.0;
		lon = 0.0;
		point = null;
	}

	@Test
	public void testGetLatitude() throws Exception
	{
		double latReturned = point.getLatitude();

		assertTrue("latitude should be " + lat + ", but was " + latReturned, latReturned == lat);
	}

	@Test
	public void testSetLatitude_Valid_SuccessfulSet() throws Exception
	{
		double newLat = 20.0;
		point.setLatitude(newLat);

		assertTrue("latitude should be " + newLat + ", but was " + point.getLatitude(), point.getLatitude() == newLat);
	}

	@Test
	public void testSetLatitude_BelowMinus90_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			point.setLatitude(-100.4);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testSetLatitude_Above90_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			point.setLatitude(100.4);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testGetLongitude() throws Exception
	{
		double lonReturned = point.getLongitude();

		assertTrue("longitude should be " + lon + ", but was " + lonReturned, lonReturned == lon);
	}

	@Test
	public void testSetLongitude_Valid_SuccessfulSet() throws Exception
	{
		double newLon = 20.0;
		point.setLongitude(newLon);

		assertTrue("longitude should be " + newLon + ", but was " + point.getLongitude(), point.getLongitude() == newLon);
	}

	@Test
	public void testSetLongitude_BelowMinus180_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			point.setLongitude(-200.5);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}

	@Test
	public void testSetLongitude_Above180_AssertionThrown() throws Exception
	{
		boolean wasThrown = false;
		try
		{
			point.setLongitude(200.6);
		} catch(AssertionError e)
		{
			wasThrown = true;
		}

		assertTrue("exception should be thrown, but was not", wasThrown);
	}
}
