package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.util.List;

public interface ProjectsDAO
{
	public Project create(long projectId, String projectName);
	public void update(Project project);
	public Project load(long projectId);
	public void delete(long projectId);
	public List<Project> listAll();
}
