package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

public class ChangeUserProfileTest extends ActivityInstrumentationTestCase2<RegistrationActivity>
{
	private Solo solo;

	public ChangeUserProfileTest()
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
		//Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity'
		solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity.class, 2000);
		//Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
		//Click on Settings
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.drawer_list_item, 3));
		//Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.SettingsActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.SettingsActivity is not found!",
				solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.SettingsActivity.class));
		//Click on LinearLayout Change User Profile  LinearLayout
		solo.clickInList(1, 0);
		//Click on dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.lastname
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.lastname));
		//Set default small timeout to 12573 milliseconds
		Timeout.setSmallTimeout(12573);
		//Enter the text: 'lastname'
		solo.clearEditText(
				(android.widget.EditText)solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.lastname));
		solo.enterText(
				(android.widget.EditText)solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.lastname),
				"lastname");
		//Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.action_save_user_profile));
		//Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
	}
}
