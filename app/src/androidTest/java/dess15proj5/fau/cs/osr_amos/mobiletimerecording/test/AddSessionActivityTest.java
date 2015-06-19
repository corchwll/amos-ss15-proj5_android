package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

public class AddSessionActivityTest extends ActivityInstrumentationTestCase2<RegistrationActivity>
{
	private Solo solo;

	public AddSessionActivityTest()
	{
		super(RegistrationActivity.class);
	}

	public void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
	}

	@Override
	public void tearDown() throws Exception
	{
		solo.finishOpenedActivities();
		super.tearDown();
	}

	public void testRun()
	{
		solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity.class, 2000);
		Timeout.setSmallTimeout(41070);
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.projectName));
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
		solo.clickInList(1, 0);
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity is not found!",
				solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity.class));
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.action_save_new_item));
	}
}
