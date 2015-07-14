package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class AddNewSessionTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {
  	private Solo solo;
  	
  	public AddNewSessionTest() {
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
        //Click on 12346 Testproject2
		solo.clickInList(7, 0);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
        //Click on ImageView
		solo.clickOnView(solo.getView("addSessionFAB"));
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity is not found!", solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity.class));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Set default small timeout to 38816 milliseconds
		Timeout.setSmallTimeout(38816);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Projects
		solo.clickInList(2, 1);
        //Click on 00000 Illness
		solo.clickInList(2, 0);
        //Click on ImageView
		solo.clickOnView(solo.getView("addSessionFAB"));
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity is not found!", solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddSessionActivity.class));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("action_save_new_item"));
	}
}
