package operation;

import helper.Connector;
import helper.MMSTool;
import helper.QueryTool;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import type.HistoryData;
import type.IndiceInf;
import type.StockInf;


public class WeekHDataOperation extends Operation{
	public static HistoryDataOperation historyDataOperation=new HistoryDataOperation();
	public void createTable(String siid)
	{
		try {
			super.createTable("create table WeekHData_"+siid+"("
					+"weekid int(11) not null,"
					+"date Date not null primary key,"
					+"open double,"
					+"close double,"
					+"increase double,"
					+"incrPer double,"
					+"low double,"
					+"high double,"
					+"volume long,"
					+"amount double"
					+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateAll()
	{
		List<StockInf> stockList=new StockInfOperation().selectAllCondition("");
		for(int i=0;i<stockList.size();i++)
		{
			updateTable("s",stockList.get(i).getSid());
		}
		List<IndiceInf> indiceList=new IndiceInfOperation().selectAllCondition("");
		for(int i=0;i<indiceList.size();i++)
		{
			updateTable("i",indiceList.get(i).getIid());
		}
	}
	public void updateTable(String type,String siid)
	{
		List<String> attributes=new ArrayList<String>();
		attributes.add("weekid");
		attributes.add("date");
		attributes.add("open");
		attributes.add("close");
		attributes.add("increase");
		attributes.add("incrPer");
		attributes.add("low");
		attributes.add("high");
		attributes.add("volume");
		attributes.add("amount");
		//从数据库stockinf表中读取weekid和weekhisid
		int weekid=-1;
		int weekhisid=-1;
		if(type.equals("s"))
		{
			StockInf stockInf=new StockInfOperation().selectAllSiid(siid);
			weekid=stockInf.getWeekid();
			weekhisid=stockInf.getWeekhisid();
		}
		else if(type.equals("i"))
		{
			IndiceInf indiceInf=new IndiceInfOperation().selectAllSiid(siid);
			weekid=indiceInf.getWeekid();
			weekhisid=indiceInf.getWeekhisid();
		}
		else
		{
			return ;//exception
		}
		if(weekid==-1)
		{
			return ;
		}
		if(weekhisid==-1)
		{
			createTable(siid);
		}
		
		//从数据库中读取需要更新的周的历史数据
		List<HistoryData> historyDataList=historyDataOperation.selectWeekData(siid, weekhisid, weekid);
		if(weekhisid!=-1)
		{
			deleteLatest(siid);
		}
		else
		{
			weekhisid++;
		}
//		System.out.println(weekhisid);
//		System.out.println(weekid);
//		System.out.println(historyDataList);
		List<HistoryData> newweekhisList=new ArrayList<HistoryData>();
		
		int start=0;
		int end=-1;
		int itr=0;
		while(itr<historyDataList.size())
		{
			//看此时itr取到的历史数据的weekid是否是接下来要处理的
			if(historyDataList.get(itr).getWeekid()==weekhisid&&itr!=historyDataList.size()-1)
			{
				end++;
				itr++;
			}
			else
			{
				if(historyDataList.get(itr).getWeekid()==weekhisid&&itr==historyDataList.size()-1)
				{
					end++;
					itr++;
				}
				
				HistoryData historyData=new HistoryData();
				historyData.setWeekid(weekhisid);
				historyData.setDate(historyDataList.get(end).getDate());
				historyData.setOpen(historyDataList.get(start).getOpen());
				historyData.setClose(historyDataList.get(end).getClose());
				historyData.setHigh(MMSTool.max_high(historyDataList.subList(start, end+1)));
				historyData.setLow(MMSTool.min_low(historyDataList.subList(start, end+1)));
				historyData.setVolume(MMSTool.sum_vol(historyDataList.subList(start, end+1)));
				historyData.setIncrease(historyData.getClose()-historyData.getOpen());
				if(historyData.getOpen()!=0)
					historyData.setIncrPer(historyData.getIncrease()/historyData.getOpen());
				else
					historyData.setIncrPer(0);
				historyData.setAmount(MMSTool.sum_amo(historyDataList.subList(start, end+1)));
				newweekhisList.add(historyData);
				weekhisid++;
				start=end+1;
			}
		}
//		System.out.println(newweekhisList);
		//插入周历史数据
		insert(siid,attributes,newweekhisList);
		
		//更新inf
		if(type.equals("s"))
		{
			new StockInfOperation().modify("weekhisid="+newweekhisList.get(newweekhisList.size()-1).getWeekid()+" where sid='"+siid+"'");
		}
		else if(type.equals("i"))
		{
			new IndiceInfOperation().modify("weekhisid="+newweekhisList.get(newweekhisList.size()-1).getWeekid()+" where iid='"+siid+"'");
		}
	}
	
	public void insert(String siid,List<String> attributes,List<HistoryData> historyDataList)
	{
		String sql="insert into WeekHData_"+siid+"(";
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
	
	public void deleteCondition(String siid,String condition)
	{
		System.out.println("delete from WeekHData_"+siid+" "+condition);
		try {
			super.delete("delete from WeekHData_"+siid+" "+condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteLatest(String siid)
	{
		deleteCondition(siid,"where weekid=(select weekid from(select max(weekid) as weekid from WeekHData_"+siid+") as a)");
	}
	
	public List<HistoryData> selectCondition(String siid,String condition)
	{
//		System.out.println("select * from HistoryData_"+siid+" "+condition);
		List<HashMap<String,Object>> list=null;
		List<HistoryData> historyDataList=new ArrayList<HistoryData>();
		Connector conn=new Connector();
		try {
			list = QueryTool.resultSetToList(super.select(conn,"select * from WeekHData_"+siid+" "+condition));
			conn.close();
			if(list.size()==0)
				return null;
			for(int i=0;i<list.size();i++)
			{
				HashMap<String,Object> hm=list.get(i);
	//			System.out.println(hm.get("date").getClass());
				historyDataList.add(new HistoryData(0,(Date)hm.get("date"),
						(int)hm.get("weekid"),0,0,
						(double)hm.get("open"),(double)hm.get("close"),(double)hm.get("increase"),(double)hm.get("incrPer"),
						(double)hm.get("low"),(double)hm.get("high"),Long.parseLong((String)hm.get("volume")),(double)hm.get("amount"),0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historyDataList;
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
