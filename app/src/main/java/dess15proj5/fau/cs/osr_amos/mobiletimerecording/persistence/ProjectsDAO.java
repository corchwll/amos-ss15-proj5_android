package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Project;

import java.util.List;

public interface ProjectsDAO extends DAO
{
	public Project create(String projectName);
	public void update(Project project);
	public Project load(long projectId);
	public void delete(long projectId);
	public List<Project> listAll();
}
