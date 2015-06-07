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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CSVMailer
{
	private String[] recipients;
	private String subject;
	private Uri attachment;

	private Context context;

	public CSVMailer(String[] recipients, String subject, Uri attachment, Context context)
	{
		this.recipients = recipients;
		this.subject = subject;
		this.attachment = attachment;
		this.context = context;
	}

	public void send()
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("*/*");
		intent.putExtra(Intent.EXTRA_EMAIL, recipients);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_STREAM, attachment);

		if (intent.resolveActivity(context.getPackageManager()) != null)
		{
			context.startActivity(intent);
		}
	}
}
