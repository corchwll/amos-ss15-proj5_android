package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;


public class ProjectTimer extends Chronometer
{
	private boolean isRunning = false;

	public ProjectTimer(Context context)
	{
		super(context);
	}

	public ProjectTimer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public ProjectTimer(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public void start()
	{
		this.setBase(SystemClock.elapsedRealtime());

		super.start();
		isRunning = true;
	}

	@Override
	public void stop()
	{
		super.stop();
		isRunning = false;
	}

	public boolean isRunning()
	{
		return isRunning;
	}
}
