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

	public ProjectTimer(Context context)
	{
		super(context);
	}

	public ProjectTimer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public ProjectTimer(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public void start()
	{
		this.setBase(SystemClock.elapsedRealtime());

		super.start();
		isRunning = true;
	}

	@Override
	public void stop()
	{
		super.stop();
		isRunning = false;
	}

	public boolean isRunning()
	{
		return isRunning;
	}
}
