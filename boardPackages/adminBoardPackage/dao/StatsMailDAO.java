package project.pc.dao;

public interface StatsMailDAO {
	
	public int getDetCount(int interval) throws Exception;
	
	public int getPeriodCount(int start, int end) throws Exception;
	
	public int getCusCount(int interval) throws Exception;
	
	public int getCusDetCount() throws Exception;
	
	public int getAppCount(int interval) throws Exception;
	
	public int getPeriodAppCount(int start, int end) throws Exception;
}
