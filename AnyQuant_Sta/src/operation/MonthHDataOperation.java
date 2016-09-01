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

public class MonthHDataOperation extends Operation{
	
	public static HistoryDataOperation historyDataOperation=new HistoryDataOperation();
	
	public void createTable(String siid)
	{
		try {
			super.createTable("create table MonthHData_"+siid+"("
					+"monthid int(11) not null,"
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
	
	public void updateTable(String type,String siid)
	{
		List<String> attributes=new ArrayList<String>();
		attributes.add("monthid");
		attributes.add("date");
		attributes.add("open");
		attributes.add("close");
		attributes.add("increase");
		attributes.add("incrPer");
		attributes.add("low");
		attributes.add("high");
		attributes.add("volume");
		attributes.add("amount");
		//从数据库stockinf表中读取monthid和monthhisid
		int monthid=-1;
		int monthhisid=-1;
		if(type.equals("s"))
		{
			StockInf stockInf=new StockInfOperation().selectAllSiid(siid);
			monthid=stockInf.getMonthid();
			monthhisid=stockInf.getMonthhisid();
		}
		else if(type.equals("i"))
		{
			IndiceInf indiceInf=new IndiceInfOperation().selectAllSiid(siid);
			monthid=indiceInf.getMonthid();
			monthhisid=indiceInf.getMonthhisid();
		}
		else
		{
			return ;//exception
		}
		if(monthid==-1)
		{
			return ;
		}
		if(monthhisid==-1)
		{
			createTable(siid);
		}
		
		//从数据库中读取需要更新的周的历史数据
		List<HistoryData> historyDataList=historyDataOperation.selectMonthData(siid, monthhisid, monthid);
		if(monthhisid!=-1)
		{
			deleteLatest(siid);
		}
		else
		{
			monthhisid++;
		}
//			System.out.println(monthhisid);
//			System.out.println(monthid);
//			System.out.println(historyDataList);
		List<HistoryData> newmonthhisList=new ArrayList<HistoryData>();
		
		int start=0;
		int end=-1;
		int itr=0;
		while(itr<historyDataList.size())
		{
			//看此时itr取到的历史数据的monthid是否是接下来要处理的
			if(historyDataList.get(itr).getMonthid()==monthhisid&&itr!=historyDataList.size()-1)
			{
				end++;
				itr++;
			}
			else
			{
				if(historyDataList.get(itr).getMonthid()==monthhisid&&itr==historyDataList.size()-1)
				{
					end++;
					itr++;
				}
				
				HistoryData historyData=new HistoryData();
				historyData.setMonthid(monthhisid);
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
				newmonthhisList.add(historyData);
				monthhisid++;
				start=end+1;
			}
		}
//			System.out.println(newmonthhisList);
		//插入周历史数据
		insert(siid,attributes,newmonthhisList);
		
		//更新inf
		if(type.equals("s"))
		{
			new StockInfOperation().modify("monthhisid="+newmonthhisList.get(newmonthhisList.size()-1).getMonthid()+" where sid='"+siid+"'");
		}
		else if(type.equals("i"))
		{
			new IndiceInfOperation().modify("monthhisid="+newmonthhisList.get(newmonthhisList.size()-1).getMonthid()+" where iid='"+siid+"'");
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
	
	public void insert(String siid,List<String> attributes,List<HistoryData> historyDataList)
	{
		String sql="insert into MonthHData_"+siid+"(";
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
//			System.out.println(sql);
		try {
			super.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCondition(String siid,String condition)
	{
		System.out.println("delete from MonthHData_"+siid+" "+condition);
		try {
			super.delete("delete from MonthHData_"+siid+" "+condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteLatest(String siid)
	{
		deleteCondition(siid,"where monthid=(select monthid from(select max(monthid) as monthid from MonthHData_"+siid+") as a)");
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
	
	public List<HistoryData> selectCondition(String siid,String condition)
	{
//		System.out.println("select * from HistoryData_"+siid+" "+condition);
		List list=null;
		List<HistoryData> historyDataList=new ArrayList<HistoryData>();
		Connector conn=new Connector();
		try {
			list = QueryTool.resultSetToList(super.select(conn,"select * from MonthHData_"+siid+" "+condition));
			conn.close();
			if(list.size()==0)
				return null;
			for(int i=0;i<list.size();i++)
			{
				HashMap hm=(HashMap)list.get(i);
	//			System.out.println(hm.get("date").getClass());
				historyDataList.add(new HistoryData(0,(Date)hm.get("date"),
						0,(int)hm.get("monthid"),0,
						(double)hm.get("open"),(double)hm.get("close"),(double)hm.get("increase"),(double)hm.get("incrPer"),
						(double)hm.get("low"),(double)hm.get("high"),Long.parseLong((String)hm.get("volume")),(double)hm.get("amount"),0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historyDataList;
	}
	
}
