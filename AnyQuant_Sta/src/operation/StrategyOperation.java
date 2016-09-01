package operation;

public class StrategyOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table Strategy("
					+ "userName varchar(35) not null,"
					+ "createrName varchar(35) not null,"
					+ "strategyName varchar(35) not null,"
					+ "stockList varchar(1000),"
					+ "choose varchar(1000),"
					+ "risk varchar(1000),"
					+ "flags varchar(1000),"
					+ "realTest varchar(1000),"
					+ "report varchar(15000),"
					+ "score double,"
					+ "privacy int default 0,"//1为公开
					+ "primary key (userName,createrName,strategyName))");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
