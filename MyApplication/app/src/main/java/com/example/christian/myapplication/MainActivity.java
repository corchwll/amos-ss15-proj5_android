package com.example.christian.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Button;
import android.os.SystemClock;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Chronometer chronometer;
    private Button startButton;
    private boolean chronometerRunning = false;
    private long lastPause = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        startButton = ((Button) findViewById(R.id.start_button));
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button)
            {
                if (chronometerRunning == false)
                {
                    if(lastPause == 0)
                    {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    }
                    else
                    {
                        chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                    }
                    chronometer.start();
                    chronometerRunning = true;
                    startButton.setText("Stop");
                }
                else if (chronometerRunning)
                {
                    chronometer.stop();
                    lastPause = SystemClock.elapsedRealtime();
                    chronometerRunning = false;
                    startButton.setText("Start");
                }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
