package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ProjectsDAO extends DAO
{
	public Project create(String projectId, String projectName, Date finalDate, boolean isUsed, boolean isArchived,
						  boolean isDisplayed);
	public void update(Project project);
	public Project load(String projectId);
	public void delete(String projectId);
	public List<Project> listAll();
}
