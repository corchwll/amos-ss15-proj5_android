package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class AddProjectTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {
  	private Solo solo;
  	
  	public AddProjectTest() {
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
        //Click on ImageView
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.addProjectFAB));
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectActivity is not found!", solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectActivity.class));
        //Enter the text: '12345'
		solo.clearEditText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectId));
		solo.enterText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectId), "12345");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
        //Enter the text: 'Testproject'
		solo.clearEditText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName));
		solo.enterText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.newProjectName), "Testproject");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.datePickerAddProject));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Scroll to SimpleMonthView
		android.widget.ListView listView0 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView0, 1390);
        //Click on SimpleMonthView
		solo.clickOnView(solo.getView(android.widget.SimpleMonthView.class, 0));
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Enter the text: '01.01.2016'
		solo.clearEditText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.datePickerAddProject));
		solo.enterText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.datePickerAddProject), "01.01.2016");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.locationEditText));
        //Wait for activity: 'dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectMapActivity'
		assertTrue("dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectMapActivity is not found!", solo.waitForActivity(dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.AddProjectMapActivity.class));
        //Click on ImageView
		solo.clickOnView(solo.getView(0x2));
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.action_save_new_item));
        //Enter the text: '49.54338073730469 11.024848937988281'
		solo.clearEditText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.locationEditText));
		solo.enterText((android.widget.EditText) solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.locationEditText), "49.5433807373046911.024848937988281");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.action_save_new_item));
	}
}
