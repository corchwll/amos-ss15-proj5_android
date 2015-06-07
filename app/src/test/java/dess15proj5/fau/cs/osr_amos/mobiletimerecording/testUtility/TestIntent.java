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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.testUtility;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TestIntent extends Intent
{
	Map<Object,Object> extras = new HashMap<>();

	public TestIntent(String action)
	{
		super(action);
	}

	@Override
	public ComponentName resolveActivity(PackageManager pm)
	{
		return new ComponentName("testCase", "test");
	}

	@NonNull
	@Override
	public Intent putExtra(String name, String value)
	{
		extras.put(name, value);
		return super.putExtra(name, value);
	}

	@NonNull
	@Override
	public Intent putExtra(String name, String[] value)
	{
		extras.put(name, value);
		return super.putExtra(name, value);
	}

	@NonNull
	@Override
	public Intent putExtra(String name, Parcelable value)
	{
		extras.put(name, value);
		return super.putExtra(name, value);
	}

	public Map<Object,Object> getExtrasMap()
	{
		return extras;
	}
}
