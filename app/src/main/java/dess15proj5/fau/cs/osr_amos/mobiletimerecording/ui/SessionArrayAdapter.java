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

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class SessionArrayAdapter extends ArrayAdapter<Session>
{
	private FragmentManager fm;
	private int selectedItemPosition = 0;

	/**
	 * This is the constructor for SessionArrayAdapter.
	 *
	 * @param context the application context under which this object is created
	 * methodtype constructor
	 */
	public SessionArrayAdapter(Context context, FragmentManager fm)
	{
		super(context, R.layout.session_row);
		this.fm = fm;
	}

	static class ViewHolder
	{
		TextView date;
		TextView startTime;
		TextView stopTime;
		TextView duration;
		ImageView deleteSessionBtn;
	}

	/**
	 * This method returns the view on the requested position.
	 *
	 * @param position the position of the requested view
	 * @param convertView the view which contains the requested view on the given position
	 * @param parent the parent of the convertView
	 * @return the requested view from the given position
	 * methodtype get method
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final ViewHolder viewHolder;
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.session_row, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.date = (TextView) convertView.findViewById(R.id.showDateSessionRow);
			viewHolder.startTime = (TextView) convertView.findViewById(R.id.startTimeSessionRow);
			viewHolder.stopTime = (TextView) convertView.findViewById(R.id.stopTimeSessionRow);
			viewHolder.duration = (TextView) convertView.findViewById(R.id.durationSessionRow);
			viewHolder.deleteSessionBtn = (ImageView) convertView.findViewById(R.id.delete_session_btn);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
			if(selectedItemPosition == position)
			{
				viewHolder.deleteSessionBtn.setVisibility(View.VISIBLE);
			}
			else
			{
				viewHolder.deleteSessionBtn.setVisibility(View.GONE);
			}
		}
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.GERMANY);
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

		final Session session = getItem(position);
		long startTimeAsLong = session.getStartTime().getTime();
		long stopTimeAsLong = session.getStopTime().getTime();

		viewHolder.date.setText(sdfDate.format(startTimeAsLong));
		viewHolder.startTime.setText(sdfTime.format(startTimeAsLong));
		viewHolder.stopTime.setText(sdfTime.format(stopTimeAsLong));
		sdfTime.setTimeZone(TimeZone.getTimeZone("GMT"));
		viewHolder.duration.setText(sdfTime.format(stopTimeAsLong - startTimeAsLong));
		viewHolder.deleteSessionBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				DeleteSessionDialogFragment fragment = new DeleteSessionDialogFragment();
				Bundle args = new Bundle();
				args.putLong("session_id", session.getId());
				fragment.setArguments(args);
				fragment.show(fm, "dialog");
			}
		});
		return convertView;
	}

	public void setSelectedItemPosition(int selectedItemPosition)
	{
		this.selectedItemPosition = selectedItemPosition;
	}
}
