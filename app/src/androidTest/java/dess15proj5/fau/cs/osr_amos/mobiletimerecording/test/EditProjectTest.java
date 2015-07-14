package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class EditProjectTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {
  	private Solo solo;
  	
  	public EditProjectTest() {
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
        //Click on 12346 Testproject
		solo.clickInList(7, 0);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
        //Click on Edit Project
		solo.clickInList(1, 0);
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.EditProjectActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.EditProjectActivity is not found!", solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.EditProjectActivity.class));
        //Click on Testproject
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
        //Set default small timeout to 18178 milliseconds
		Timeout.setSmallTimeout(18178);
        //Enter the text: 'Testproject2'
		solo.clearEditText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
		solo.enterText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName), "Testproject2");
        //Click on 12346
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectId));
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.action_save_new_item));
	}
}
