package dess15proj5.fau.cs.osr_amos.mobiletimerecording;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility.ProjectTimer;

public class ProjectList extends ActionBarActivity
{
	private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

		tableLayout = (TableLayout)findViewById(R.id.project_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

		switch (item.getItemId())
		{
			case R.id.action_add_project:
				addNewProject();
				return true;
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
    }

	private void addNewProject()
	{
		final EditText input = new EditText(this);
		new AlertDialog.Builder(ProjectList.this).setTitle("Update Status")
												 .setMessage("hi")
												 .setView(input)
												 .setPositiveButton("Ok", new DialogInterface.OnClickListener()
												 {
													 public void onClick(DialogInterface dialog, int whichButton)
													 {
														 createTableRow(input);
													 }
												 })
												 .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
												 {
													 public void onClick(DialogInterface dialog, int whichButton)
													 {
														 // Do nothing.
													 }
												 })
												 .show();
	}

	private void createTableRow(EditText editText)
	{
		TableRow row = (TableRow)LayoutInflater.from(ProjectList.this)
											   .inflate(R.layout.project_row, null);
		((TextView)row.findViewById(R.id.projectName)).setText(editText.getText());
		initializeStartButton((Button)row.findViewById(R.id.startButton), (ProjectTimer)row.findViewById(R.id.timer));
		tableLayout.addView(row);
		tableLayout.requestLayout();
	}

	/**
	 * @methodtype command method
	 */
	private void initializeStartButton(final Button button, final ProjectTimer timer)
	{
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!timer.isRunning())
				{
					timer.start();
					button.setText("Stop");
				} else
				{
					timer.stop();
					button.setText("Start");
				}
			}
		});
	}
}
