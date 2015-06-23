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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.MainActivity;

public class AccountingNotification
{
	public static final int ALARM_ID = 21;

	private Context context;

	/**
	 * This is used to create an AccountingNotification object.
	 *
	 * @param context the context under which the object should be created.
	 * methodtype constructor
	 */
	public AccountingNotification(Context context)
	{
		this.context = context;
	}

	/**
	 * This method creates a notification to remind the user that he has not recorded work for today.
	 *
	 * methodtype command method
	 */
	public void createNotification()
	{
		Notification.Builder mBuilder =
				new Notification.Builder(context)
						.setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("Mobile Time Accounting")
						.setContentText("You haven't recorded your time today");

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		mBuilder.setContentIntent(pIntent);
		NotificationManager mNotificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(50, mBuilder.build());
	}
}
