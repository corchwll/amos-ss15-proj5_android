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

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestSharedPreferences implements SharedPreferences
{
	public Map<String,Object> prefs = new HashMap<>();

	@Override
	public Map<String,?> getAll()
	{
		return null;
	}

	@Nullable
	@Override
	public String getString(String s, String s1)
	{
		return null;
	}

	@Nullable
	@Override
	public Set<String> getStringSet(String s, Set<String> set)
	{
		return null;
	}

	@Override
	public int getInt(String s, int i)
	{
		return 0;
	}

	@Override
	public long getLong(String s, long l)
	{
		Long result = l;
		Object o = prefs.get(s);

		if(o != null)
		{
			result = (Long)o;
		}

		return result;
	}

	@Override
	public float getFloat(String s, float v)
	{
		return 0;
	}

	@Override
	public boolean getBoolean(String s, boolean b)
	{
		return false;
	}

	@Override
	public boolean contains(String s)
	{
		return false;
	}

	@Override
	public Editor edit()
	{
		return new TestEditor();
	}

	@Override
	public void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener onSharedPreferenceChangeListener)
	{

	}

	@Override
	public void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener onSharedPreferenceChangeListener)
	{

	}

	public class TestEditor implements SharedPreferences.Editor
	{
		@Override
		public Editor putString(String s, String s1)
		{
			return null;
		}

		@Override
		public Editor putStringSet(String s, Set<String> set)
		{
			return null;
		}

		@Override
		public Editor putInt(String s, int i)
		{
			return null;
		}

		@Override
		public Editor putLong(String s, long l)
		{
			Object o = new Long(l);
			prefs.put(s, o);
			return this;
		}

		@Override
		public Editor putFloat(String s, float v)
		{
			return null;
		}

		@Override
		public Editor putBoolean(String s, boolean b)
		{
			return null;
		}

		@Override
		public Editor remove(String s)
		{
			return null;
		}

		@Override
		public Editor clear()
		{
			return null;
		}

		@Override
		public boolean commit()
		{
			return false;
		}

		@Override
		public void apply()
		{

		}
	}
}
