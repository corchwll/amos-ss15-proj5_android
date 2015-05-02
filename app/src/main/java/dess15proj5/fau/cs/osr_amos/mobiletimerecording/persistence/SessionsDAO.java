package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import dess15proj5.fau.cs.osr_amos.mobiletimerecording.models.Session;

import java.util.Date;
import java.util.List;

public interface SessionsDAO
{
	public Session create(long projectId, Date startTime);
	public void update(Session session);
	public Session load(long sessionId);
	public void delete(long sessionId);
	public List<Session> listAll();
}
