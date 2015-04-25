package dess15proj5.fau.cs.osr_amos.mobiletimerecording.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;

public class ProjectButton extends Button
{
	private Project project;
	private Session currentSession;

	public ProjectButton(Context context)
	{
		super(context);
	}

	public ProjectButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public ProjectButton(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}

	public Session getCurrentSession()
	{
		return currentSession;
	}

	public void setCurrentSession(Session currentSession)
	{
		this.currentSession = currentSession;
	}
}
