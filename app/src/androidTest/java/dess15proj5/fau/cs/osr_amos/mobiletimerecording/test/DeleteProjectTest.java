package dess15proj5.fau.cs.osr_amos.mobiletimerecording.test;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui.RegistrationActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class DeleteProjectTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {
  	private Solo solo;
  	
  	public DeleteProjectTest() {
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
        //Click on testProject
		solo.clickOnView(solo.getView(dess15proj5.fau.cs.osr_amos.mobiletimerecording.R.id.projectName, 1));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
        //Click on Delete project
		solo.clickInList(2, 0);
	}
}
