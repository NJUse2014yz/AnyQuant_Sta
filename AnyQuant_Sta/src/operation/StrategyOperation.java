package operation;

public class StrategyOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table Strategy("
					+ "userName varchar(35) not null,"
					+ "createrName varchar(35) not null,"
					+ "strategyName varchar(35) not null,"
					+ "stockList text,"
					+ "choose mediumtext,"
					+ "risk mediumtext,"
					+ "flags mediumtext,"
					+ "realTest mediumtext,"
					+ "report mediumtext,"
					+ "score double,"
					+ "privacy int default 0,"//1为公开
					+ "primary key (userName,createrName,strategyName))");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
