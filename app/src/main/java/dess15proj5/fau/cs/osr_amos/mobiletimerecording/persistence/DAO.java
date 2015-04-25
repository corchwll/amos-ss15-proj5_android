package dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence;

import java.sql.SQLException;

public interface DAO
{
	public void open() throws SQLException;
	public void close();
}
