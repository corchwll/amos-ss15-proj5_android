package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class AddMinimalProjectTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {
  	private Solo solo;
  	
  	public AddMinimalProjectTest() {
		super(RegistrationActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity'
		solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity.class, 2000);
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.MainActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.MainActivity is not found!", solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.MainActivity.class));
        //Click on ImageView
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.addProjectFAB));
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectActivity is not found!", solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectActivity.class));
        //Enter the text: '12346'
		solo.clearEditText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectId));
		solo.enterText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectId), "12346");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
        //Enter the text: 'Testproject'
		solo.clearEditText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
		solo.enterText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName), "Testproject");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.action_save_new_item));
	}
}
