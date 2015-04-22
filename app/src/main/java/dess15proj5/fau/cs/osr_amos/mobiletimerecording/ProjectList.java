package dess15proj5.fau.cs.osr_amos.mobiletimerecording;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;


public class ProjectList extends ActionBarActivity
{
    private Chronometer chronometer;
    private Button startButton;
    private boolean chronometerRunning = false;
    private long lastBreak = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        startButton = ((Button) findViewById(R.id.start_button));
		initializeStartButton();
    }

	/**
	 * @methodtype command method
	 */
	private void initializeStartButton()
	{
		startButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!chronometerRunning)
				{
					if(lastBreak == 0L)
					{
						chronometer.setBase(SystemClock.elapsedRealtime());
					} else
					{
						chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastBreak);
					}
					chronometer.start();
					chronometerRunning = true;
					startButton.setText("Stop");
				} else
				{
					chronometer.stop();
					lastBreak = SystemClock.elapsedRealtime();
					chronometerRunning = false;
					startButton.setText("Start");
				}

			}
		});
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
