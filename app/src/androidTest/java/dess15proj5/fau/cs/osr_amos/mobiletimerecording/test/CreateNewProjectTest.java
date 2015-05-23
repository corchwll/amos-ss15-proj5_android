package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

public class CreateNewProjectTest extends ActivityInstrumentationTestCase2<RegistrationActivity>
{
	private Solo solo;

	public CreateNewProjectTest()
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
		//Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.action_add_project));
		//Wait for dialog
		solo.waitForDialogToOpen(5000);
		//Enter the text: '12345'
		solo.clearEditText((android.widget.EditText)solo.getView(
				dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectId));
		solo.enterText((android.widget.EditText)solo.getView(
				dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectId), "12345");
		//Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
		//Enter the text: 'testProject'
		solo.clearEditText((android.widget.EditText)solo.getView(
				dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
		solo.enterText((android.widget.EditText)solo.getView(
				dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName), "testProject");
		//Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
	}
}
