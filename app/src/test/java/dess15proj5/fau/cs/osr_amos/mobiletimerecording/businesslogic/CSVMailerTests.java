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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.testUtility.TestContext;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.testUtility.TestIntent;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class CSVMailerTests
{
	@Test
	public void testSend_DummyData_IntentIsFilledCorrectly()
	{
		String[] recipients = new String[]{"test@test1234.de"};
		String subject = "test";

		Uri.Builder builder = new Uri.Builder();
		Uri uri = builder.build();

		TestContext context = new TestContext();
		CSVMailer mailer = new CSVMailer(recipients, subject, uri, context);
		mailer.setIntent(new TestIntent(Intent.ACTION_SEND));
		mailer.send();

		TestIntent intent = (TestIntent)context.getIntent();
		Map<Object,Object> extras = intent.getExtrasMap();

		assertTrue(extras.get(Intent.EXTRA_SUBJECT).equals(subject) && extras.get(Intent.EXTRA_EMAIL).equals
				(recipients));
	}
}
