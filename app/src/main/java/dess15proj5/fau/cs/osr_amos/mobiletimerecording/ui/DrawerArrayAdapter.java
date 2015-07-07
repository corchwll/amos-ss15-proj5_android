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

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class DrawerArrayAdapter extends ArrayAdapter<String>
{
	public DrawerArrayAdapter(Context context)
	{
		super(context, R.layout.drawer_list_item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.drawer_list_item, parent, false);

		ImageView drawerIcon = (ImageView) view.findViewById(R.id.drawerIcon);
		TextView drawerListItem = (TextView) view.findViewById(R.id.drawer_list_item);

		switch(position)
		{
			//Time Recording
			case 0:
				drawerIcon.setImageResource(R.drawable.ic_timer_white);
				drawerListItem.setText(getItem(position));
				break;
			//Projects
			case 1:
				drawerIcon.setImageResource(R.drawable.ic_list_white);
				drawerListItem.setText(getItem(position));
				break;
			//Dashboard
			case 2:
				drawerIcon.setImageResource(R.drawable.ic_dashboard_white);
				drawerListItem.setText(getItem(position));
				break;
			//Settings
			case 3:
				drawerIcon.setImageResource(R.drawable.ic_settings_white);
				drawerListItem.setText(getItem(position));
				break;
		}
		return view;
	}
}
