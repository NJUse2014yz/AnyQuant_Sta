package operation;

import helper.Connector;
import helper.JsonExchangeTool;
import helper.QueryTool;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import type.HistoryData;
import type.IndiceInf;
import type.StockInf;

public class HistoryDataOperation extends Operation{
	
	public void createTable(String siid)
	{
		try {
			super.createTable("create table HistoryData_"+siid+"("
					+"id int(11) not null,"
					+"date Date not null primary key,"
					+"weekid int(11),"
					+"monthid int(11),"
					+"yearid int(11),"
					+"open double,"
					+"close double,"
					+"increase double,"
					+"incrPer double,"
					+"low double,"
					+"high double,"
					+"volume long,"
					+"amount double,"
					+"turnover double"
					+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dropTable(String siid)
	{
		try {
			super.dropTable("drop table HistoryData_"+siid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new StockInfOperation().modify("date=null,hisid=-1 where sid='"+siid+"'");
	}
	
	public void dropAll()
	{
		List<StockInf> stockInfList=new StockInfOperation().selectAllCondition("");
		for(int i=0;i<stockInfList.size();i++)
		{
			dropTable(stockInfList.get(i).getSid());
		}
		List<IndiceInf> indiceInfList=new IndiceInfOperation().selectAllCondition("");
		for(int i=0;i<indiceInfList.size();i++)
		{
			dropTable(indiceInfList.get(i).getIid());
		}
	}
	
//	public void initailTable(String type,String siid)
//	{
//		dropTable(siid);
//		createTable(siid);
//		updateTable(type,siid);
//	}
//	
//	public void initailAll()
//	{
//		List<StockInf> stockInfList=new StockInfOperation().selectAllCondition("");
//		for(int i=0;i<stockInfList.size();i++)
//		{
//			initailTable("s",stockInfList.get(i).getSid());
//		}
//		List<IndiceInf> indiceInfList=new IndiceInfOperation().selectAllCondition("");
//		for(int i=0;i<indiceInfList.size();i++)
//		{
//			initailTable("i",indiceInfList.get(i).getIid());
//		}
//	}
	
	public void updateTable(String type,String siid)
	{
		List<String> attributes=new ArrayList<String>();
		attributes.add("id");
		attributes.add("date");
		attributes.add("weekid");
		attributes.add("monthid");
		attributes.add("yearid");
		attributes.add("open");
		attributes.add("close");
		attributes.add("increase");
		attributes.add("incrPer");
		attributes.add("low");
		attributes.add("high");
		attributes.add("volume");
		attributes.add("amount");
		attributes.add("turnover");
		
		List<HistoryData> historyDataList=null;
		HistoryData latestHistoryData=selectLatest(siid);
		
		if(latestHistoryData==null)
		{
			latestHistoryData=new HistoryData();
			dropTable(siid);
			createTable(siid);
		}
		if(type.equals("s"))
		{
			historyDataList=JsonExchangeTool.getStockHistoryData(siid, latestHistoryData.getDate(), new Date(Calendar.getInstance().getTimeInMillis()));
		}
		else if(type.equals("i"))
		{
			historyDataList=JsonExchangeTool.getIndiceHistoryData(siid, latestHistoryData.getDate(), new Date(Calendar.getInstance().getTimeInMillis()));
		}
		if(historyDataList==null)
		{
			return ;
		}
		for(int i=historyDataList.size()-1;i>=0;i--)
		{
			HistoryData nowHistoryData=historyDataList.get(i);
			nowHistoryData.setYearid(nowHistoryData.getDate().getYear()+1900);
			nowHistoryData.setId(latestHistoryData.getId()+1);
			if(latestHistoryData.getDate()==null)
			{
				nowHistoryData.setWeekid(0);
				nowHistoryData.setMonthid(0);
			}
			else
			{
				if(nowHistoryData.getDate().getMonth()==latestHistoryData.getDate().getMonth())
				{
					nowHistoryData.setMonthid(latestHistoryData.getMonthid());
				}
				else
				{
					nowHistoryData.setMonthid(latestHistoryData.getMonthid()+1);
				}
				
				Calendar cal1=Calendar.getInstance();
				cal1.setTime(nowHistoryData.getDate());
				Calendar cal2=Calendar.getInstance();
				cal2.setTime(latestHistoryData.getDate());
				if(cal1.get(Calendar.WEEK_OF_YEAR)==cal2.get(Calendar.WEEK_OF_YEAR))
				{
					nowHistoryData.setWeekid(latestHistoryData.getWeekid());
				}
				else
				{
					nowHistoryData.setWeekid(latestHistoryData.getWeekid()+1);
				}
			}
			latestHistoryData=nowHistoryData;
			
		}
		
		insert(siid,attributes,historyDataList);
		
		if(historyDataList!=null)
		{
			if(type.equals("s"))
			{
				new StockInfOperation().modify("date='"+historyDataList.get(0).getDate()+"',hisid="+latestHistoryData.getId()
						+",weekid="+historyDataList.get(0).getWeekid()+",monthid="+historyDataList.get(0).getMonthid()
						+" where sid='"+siid+"'");
			}
			else if(type.equals("i"))
			{
				new IndiceInfOperation().modify("date='"+historyDataList.get(0).getDate()+"',hisid="+latestHistoryData.getId()
				+",weekid="+historyDataList.get(0).getWeekid()+",monthid="+historyDataList.get(0).getMonthid()
				+" where iid='"+siid+"'");
			}
		}
	}
	
	public void updateAll()
	{
		List<StockInf> stockInfList=new StockInfOperation().selectAllCondition("");
		for(int i=0;i<stockInfList.size();i++)
		{
			updateTable("s",stockInfList.get(i).getSid());
		}
		List<IndiceInf> indiceInfList=new IndiceInfOperation().selectAllCondition("");
		for(int i=0;i<indiceInfList.size();i++)
		{
			updateTable("i",indiceInfList.get(i).getIid());
		}
	}
	
	public void insert(String siid,List<String> attributes,List<HistoryData> historyDataList)
	{
		String sql="insert into HistoryData_"+siid+"(";
		sql+=attributes.get(0);
		for(int j=1;j<attributes.size();j++)
		{
			sql+=","+attributes.get(j);
		}
		sql+=") values";
		for(int i=0;i<historyDataList.size();i++)
		{
			HistoryData historyData=historyDataList.get(i);
			for(int j=0;j<attributes.size();j++)
			{
				sql=historyDataElement(sql,attributes.get(j),j,historyData,i);
			}
			sql+=")";
		}
//		System.out.println(sql);
		try {
			super.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<HistoryData> selectCondition(String siid,String condition)
	{
//		System.out.println("select * from HistoryData_"+siid+" "+condition);
		List list=null;
		List<HistoryData> historyDataList=new ArrayList<HistoryData>();
		Connector conn=new Connector();
		try {
			list = QueryTool.resultSetToList(super.select(conn,"select * from HistoryData_"+siid+" "+condition));
			conn.close();
			if(list.size()==0)
				return null;
			for(int i=0;i<list.size();i++)
			{
				HashMap hm=(HashMap)list.get(i);
	//			System.out.println(hm.get("date").getClass());
				historyDataList.add(new HistoryData((int)hm.get("id"),(Date)hm.get("date"),
						(int)hm.get("weekid"),(int)hm.get("monthid"),(int)hm.get("yearid"),
						(double)hm.get("open"),(double)hm.get("close"),(double)hm.get("increase"),(double)hm.get("incrPer"),
						(double)hm.get("low"),(double)hm.get("high"),Long.parseLong((String)hm.get("volume")),(double)hm.get("amount"),(double)hm.get("turnover")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historyDataList;
	}
	
	public HistoryData selectLatest(String siid)
	{
		List<HistoryData> historyDataList=null;
		try{
			historyDataList=selectCondition(siid,"where id=(select max(id) from HistoryData_"+siid+")");
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		if(historyDataList==null)
		{
			return null;
		}
		else
		{
			return historyDataList.get(0);
		}
	}
	
	public List<HistoryData> selectWeekData(String siid,int weekhisid,int weekid)
	{
		List<HistoryData> historyDataList=selectCondition(siid,"where weekid between "+weekhisid+" and "+weekid);
		return historyDataList;
	}
	
	public List<HistoryData> selectMonthData(String siid,int monthhisid,int monthid)
	{
		List<HistoryData> historyDataList=selectCondition(siid,"where monthid between "+monthhisid+" and "+monthid);
		return historyDataList;
	}
	
	public List<Double> selectAttribute_num_date(String siid,String attribute,int num,Date date)
	{
		List<Double> historyDataList=selectCondition(siid,attribute,
				"where (select max(id) from HistoryData_"+siid
				+" where date<='"+date.toString()
				+"') between id and id+"+num+"-1"
				+" order by id");
		return historyDataList;
	}
	
	public List<Double> selectCondition(String siid,String attribute,String condition)
	{
//		System.out.println("select * from HistoryData_"+siid+" "+condition);
		List list=null;
		List<Double> resultList=new ArrayList<Double>();
		Connector conn=new Connector();
		try {
			list = QueryTool.resultSetToList(super.select(conn,"select "+attribute+" from HistoryData_"+siid+" "+condition));
			conn.close();
			if(list.size()==0)
				return null;
			for(int i=0;i<list.size();i++)
			{
				HashMap hm=(HashMap)list.get(i);
	//			System.out.println(hm.get("date").getClass());
				resultList.add((double) hm.get(attribute));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	private String historyDataElement(String sql,String attribute,int j,HistoryData historyData,int i)
	{
		if(i==0)
		{
			if(j==0)
			{
				sql+="(";
			}
			else
			{
				sql+=",";
			}
		}
		else
		{
			if(j==0)
			{
				sql+=",(";
			}
			else
			{
				sql+=",";
			}
		}
		
		switch(attribute)
		{
		case "id":
			sql+=historyData.getId();
			break;
		case "date":
			sql+="'"+historyData.getDate()+"'";
			break;
		case "weekid":
			sql+=historyData.getWeekid();
			break;
		case "monthid":
			sql+=historyData.getMonthid();
			break;
		case "yearid":
			sql+=historyData.getYearid();
			break;
		case "open":
			sql+=historyData.getOpen();
			break;
		case "close":
			sql+=historyData.getClose();
			break;
		case "increase":
			sql+=historyData.getIncrease();
			break;
		case "incrPer":
			sql+=historyData.getIncrPer();
			break;
		case "low":
			sql+=historyData.getLow();
			break;
		case "high":
			sql+=historyData.getHigh();
			break;
		case "volume":
			sql+=historyData.getVolume();
			break;
		case "amount":
			sql+=historyData.getAmount();
			break;
		case "turnover":
			sql+=historyData.getTurnover();
			break;
		}
		return sql;
	}

}
