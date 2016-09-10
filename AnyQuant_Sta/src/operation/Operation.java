package operation;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Scanner;

import helper.Connector;

public class Operation {
	public void createTable(String sql) throws Exception
	{
		Connector conn=new Connector();
		conn.execute(sql);
		conn.close();
	}
	public void dropTable(String sql) throws Exception
	{
		Connector conn=new Connector();
		conn.execute(sql);
		conn.close();
	}
	public void insert(String sql) throws Exception
	{
		Connector conn=new Connector();
		conn.execute(sql);
		conn.close();
	}
	public void modify(String sql) throws Exception
	{
		Connector conn=new Connector();
		conn.execute(sql);
		conn.close();
	}
	public void delete(String sql) throws Exception
	{
		Connector conn=new Connector();
		conn.execute(sql);
		conn.close();
	}
	public ResultSet select(Connector conn,String sql) throws Exception
	{
		ResultSet set= conn.executeQuery(sql);
		return set;
	}
	public static void init()
	{
//		new UserInfOperation().initialTable();
//		new StockInfOperation().initialTable();
//		new StockInfOperation().updatePartnerAll();
//		new IndiceInfOperation().initialTable();
//		new AreaInfOperation().initailTable();
//		new ConceptInfOperation().initailTable();
//		new IndustryInfOperation().initailTable();
//		new AreaLatestOperation().initailTable();
//		new StrategyOperation().createTable();
//		new MultFactStockRecommendOperation().initialTable();

//		FunctionOperation fo=new FunctionOperation();
//		fo.createTable();
//		
//		fo.insertFunction("Trend", "test","flag", 0);
//		fo.insertFunction("UpTrend", "test","flag", 0);
//		fo.insertFunction("DownTrend", "test","flag", 0);
//		fo.insertFunction("Cross", "test","flag", 0);
//		fo.insertFunction("CrossPoint", "test","flag", 0);
//		
//		fo.insertFunction("Pair", "test", "choose", 0);
//		
//		fo.insertFunction("Share", "test", "order", 0);
//		fo.insertFunction("SharePercent", "test", "order", 0);
//		fo.insertFunction("ShareTarget", "test", "order", 0);
//		fo.insertFunction("Value", "test", "order", 0);
//		fo.insertFunction("ValuePercent", "test", "order", 0);
//		fo.insertFunction("ValueTarget", "test", "order", 0);
		
//		QuotaInfOperation qio=new QuotaInfOperation();
//		qio.createTable();
//		qio.insertQuotaInf("m5","test","mean",0);
//		qio.insertQuotaInf("m10","test","mean",0);
//		qio.insertQuotaInf("m20","test","mean",0);
//		qio.insertQuotaInf("rsi","test","rsi",0);
		
//		StrategyInfOperation sio=new StrategyInfOperation();
//		sio.createTable();
//		sio.insertStrategyInf("五日金叉策略","test","cross",0);

	}
	public static void update()
	{
		new HistoryDataOperation().updateAll();
		new QuotaDataOperation().updateAll();
		new WeekHDataOperation().updateAll();
		new MonthHDataOperation().updateAll();
		new WeekQDataOperation().updateAll();
		new MonthQDataOperation().updateAll();
//		new AreaLatestOperation().updateAll();
//		new ConceptLatestOperation().updateAll();
//		new IndustryLatestOperation().updateAll();
		
	}
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		while(true)
		{
			String order=in.next();
			if(order.equals("initial"))
			{
				init();
				System.out.println("initial done!");
			}
			else if(order.equals("update"))
			{
//				Calendar cal=Calendar.getInstance();
//				if(cal.get(Calendar.HOUR_OF_DAY)==16)
//				{
					update();
					System.out.println("update done!");
//				}
			}
			else
			{
				System.out.println("exit!");
				System.exit(0);
			}
		}
		
	}
}
