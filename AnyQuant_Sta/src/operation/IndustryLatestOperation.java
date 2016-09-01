package operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import type.HistoryData;
import helper.Connector;
import helper.QueryTool;

public class IndustryLatestOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table IndustryLatest ("
				+"sid varchar(16) not null,"
				+"name varchar(32),"
				+"block varchar(32),"
				+"id int(11),"
				+"date Date,"
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
	public void initailTable()
	{
		createTable();
		try {
			super.insert("insert into IndustryLatest" 
					+ "(sid,name,block)"
					+ "(select * from IndustryInf)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateTable(String siid)
	{
		HistoryData his=new HistoryDataOperation().selectLatest(siid);
		try {
			super.modify("update IndustryLatest Industry set"
				+"Industry.id="+his.getId()+","
				+"Industry.date='"+his.getDate()+"',"
				+"Industry.weekid="+his.getWeekid()+","
				+"Industry.monthid="+his.getMonthid()+","
				+"Industry.yearid="+his.getYearid()+","
				+"Industry.open="+his.getOpen()+","
				+"Industry.close="+his.getClose()+","
				+"Industry.increase="+his.getIncrease()+","
				+"Industry.incrPer="+his.getIncrPer()+","
				+"Industry.low="+his.getLow()+","
				+"Industry.high="+his.getHigh()+","
				+"Industry.volume="+his.getVolume()+","
				+"Industry.amount="+his.getAmount()+","
				+"Industry.turnover="+his.getTurnover()
			+"where Industry.sid='"+siid+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateAll()
	{
		List list=null;
		Connector conn=new Connector();
		try {
			list=QueryTool.resultSetToList(super.select(conn,"select distinct sid from IndustryLatest"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		for(int i=0;i<list.size();i++)
		{
			updateTable((String)((HashMap)list.get(i)).get("sid"));
		}
	}
	public List<String> selectSiid()
	{
		List list=null;
		List<String> sidList=new ArrayList<String>();
		Connector conn=new Connector();
		try {
			list=QueryTool.resultSetToList(super.select(conn,"select distinct sid from IndustryLatest"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		for(int i=0;i<list.size();i++)
		{
			sidList.add((String)((HashMap)list.get(i)).get("sid"));
		}
		return sidList;
	}
}
