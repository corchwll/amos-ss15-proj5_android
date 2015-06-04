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
import android.widget.Filter;
import android.widget.TextView;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectArrayAdapter extends ArrayAdapter<Project>
{
	public static final int ITEM_VIEW_TYPE_PROJECT = 0;
	public static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
	public static final int ITEM_VIEW_TYPE_COUNT = 2;

	private ArrayList<Project> originalList;
	private ArrayList<Project> projectList;
	private ProjectFilter filter;

	static class ViewHolder
	{
		TextView projectId;
		TextView projectTextView;
	}

	/**
	 * This is the constructor for ProjectArrayAdapter.
	 *
	 * @param context the application context under which this object is created
	 * methodtype constructor
	 */
	public ProjectArrayAdapter(Context context)
	{
		super(context, R.layout.project_row);
	}

	/**
	 * Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
	 *
	 * methodtype get method
	 */
	@Override
	public int getViewTypeCount()
	{
		return ITEM_VIEW_TYPE_COUNT;
	}

	/**
	 * Get the type of View that will be created by getView(int, View, ViewGroup) for the specified item.
	 *
	 * @param position The position of the item within the adapter's data set whose view type we want.
	 * methodtype get method
	 */
	@Override
	public int getItemViewType(int position)
	{
		if(isSeparator(getItem(position)))
		{
			return ITEM_VIEW_TYPE_SEPARATOR;
		}
		else
		{
			return ITEM_VIEW_TYPE_PROJECT;
		}
	}

	/**
	 * Returns true if the item at the specified position is not a separator.
	 *
	 * methodtype boolean query method
	 */
	@Override
	public boolean isEnabled(int position)
	{
		return !isSeparator(getItem(position));
	}

	/**
	 * Returns true if the item at the specified position is a separator.
	 *
	 * methodtype boolean query method
	 */
	private boolean isSeparator(Project project)
	{
		return (project.getId().equals(ProjectsListFragment.SEPARATOR_ID));
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
		final Project project = getItem(position);

		if(isSeparator(project))
		{
			convertView = inflater.inflate(R.layout.project_list_separator, parent, false);
			TextView projectListSeparator = (TextView) convertView.findViewById(R.id.projectListSeparator);
			projectListSeparator.setText(project.getName());
			return convertView;
		}
		else
		{
			ViewHolder viewHolder;
			if(convertView == null)
			{
				convertView = inflater.inflate(R.layout.project_row, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.projectId = (TextView)convertView.findViewById(R.id.projectId);
				viewHolder.projectTextView = (TextView)convertView.findViewById(R.id.projectName);
				convertView.setTag(viewHolder);
			} else
			{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.projectId.setText(project.getId());
			viewHolder.projectTextView.setText(project.getName());
			return convertView;
		}
	}

	/**
	 * This method sets the two local arrayLists
	 * @param projectList a List with projects
	 *
	 * methodtype set method
	 */
	public void setProjectList(List<Project> projectList)
	{
		this.projectList = new ArrayList<>();
		this.projectList.addAll(projectList);
		this.originalList = new ArrayList<>();
		this.originalList.addAll(projectList);
	}

	/**
	 * returns a project filter
	 *
	 * methodtype get method
	 */
	@Override
	public Filter getFilter() {
		if (filter == null){
			filter  = new ProjectFilter();
		}
		return filter;
	}

	private class ProjectFilter extends Filter
	{
		/**
		 * filter the data according to the constraint
		 *
		 * methodtype helper method
		 */
		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if(constraint.toString().length() > 0)
			{
				ArrayList<Project> filteredItems = new ArrayList<>();

				for(int i = 0; i < originalList.size(); i++)
				{
					Project project = originalList.get(i);
					if(project.toString().toLowerCase().contains(constraint) &&
							!project.getId().contains(ProjectsListFragment.SEPARATOR_ID))
						filteredItems.add(project);
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			}
			else
			{
				synchronized(this)
				{
					result.values = originalList;
					result.count = originalList.size();
				}
			}
			return result;
		}

		/**
		 * publish the filtering results in the user interface
		 *
		 * methodtype helper method
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			projectList = (ArrayList<Project>) results.values;
			notifyDataSetChanged();
			clear();
			for(int i = 0; i < projectList.size(); i++)
				add(projectList.get(i));
			notifyDataSetInvalidated();
		}
	}

}
