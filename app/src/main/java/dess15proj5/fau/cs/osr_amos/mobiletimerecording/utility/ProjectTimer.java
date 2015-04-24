package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;


public class ProjectTimer extends Chronometer
{
	private boolean isRunning = false;
	private long lastBreak = 0L;

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
		if(lastBreak == 0L)
		{
			this.setBase(SystemClock.elapsedRealtime());
		} else
		{
			this.setBase(this.getBase() + SystemClock.elapsedRealtime() - lastBreak);
		}

		super.start();
		isRunning = true;
	}

	@Override
	public void stop()
	{
		super.stop();
		isRunning = false;
		lastBreak = SystemClock.elapsedRealtime();
	}

	public boolean isRunning()
	{
		return isRunning;
	}
}
