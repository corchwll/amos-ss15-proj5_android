package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class RegistrationActivity extends ActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		setClickListener();
	}

	private void setClickListener()
	{
		Button createUserButton = (Button) findViewById(R.id.createUserButton);
		createUserButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getBaseContext(), ProjectListActivity.class);
				startActivity(intent);
			}
		});
	}

}
