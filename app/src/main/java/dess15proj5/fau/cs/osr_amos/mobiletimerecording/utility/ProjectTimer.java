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

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;


public class ProjectTimer extends Chronometer
{
	private boolean isRunning = false;

	/**
	 * This is the constructor for ProjectTimer.
	 *
	 * @param context the application context under which this object is created
	 * methodtype constructor
	 */
	public ProjectTimer(Context context)
	{
		super(context);
	}

	/**
	 * This is the constructor for ProjectTimer.
	 *
	 * @param context the application context under which this object is created
	 * @param attrs the attribute set for the creation of this timer
	 * methodtype constructor
	 */
	public ProjectTimer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * This is the constructor for ProjectTimer.
	 *
	 * @param context the application context under which this object is created
	 * @param attrs the attribute set for the creation of this timer
	 * @param defStyle the defStyle for this timer
	 * methodtype constructor
	 */
	public ProjectTimer(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * This method is used to start the time recording.
	 *
	 * methodtype command method
	 */
	@Override
	public void start()
	{
		this.setBase(SystemClock.elapsedRealtime());

		super.start();
		isRunning = true;
	}

	/**
	 * This method is used to stop the time recording.
	 *
	 * methodtype command method
	 */
	@Override
	public void stop()
	{
		super.stop();
		isRunning = false;
	}

	/**
	 * This method is used to check whether time recording is running or not.
	 *
	 * @return true if time recording is running, false if not
	 * methodtype boolean query method
	 */
	public boolean isRunning()
	{
		return isRunning;
	}
}
