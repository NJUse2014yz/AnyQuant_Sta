package operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import type.HistoryData;
import helper.Connector;
import helper.QueryTool;

public class AreaLatestOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table AreaLatest ("
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
			super.insert("insert into AreaLatest" 
					+ "(sid,name,block)"
					+ "(select * from AreaInf)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateTable(String siid)
	{
		HistoryData his=new HistoryDataOperation().selectLatest(siid);
		try {
			super.modify("update AreaLatest Area set"
				+"Area.id="+his.getId()+","
				+"Area.date='"+his.getDate()+"',"
				+"Area.weekid="+his.getWeekid()+","
				+"Area.monthid="+his.getMonthid()+","
				+"Area.yearid="+his.getYearid()+","
				+"Area.open="+his.getOpen()+","
				+"Area.close="+his.getClose()+","
				+"Area.increase="+his.getIncrease()+","
				+"Area.incrPer="+his.getIncrPer()+","
				+"Area.low="+his.getLow()+","
				+"Area.high="+his.getHigh()+","
				+"Area.volume="+his.getVolume()+","
				+"Area.amount="+his.getAmount()+","
				+"Area.turnover="+his.getTurnover()
			+"where Area.sid='"+siid+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateAll()
	{
		List list=null;
		Connector conn=new Connector();
		try {
			list=QueryTool.resultSetToList(super.select(conn,"select distinct sid from AreaLatest"));
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
			list=QueryTool.resultSetToList(super.select(conn,"select distinct sid from AreaLatest"));
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
