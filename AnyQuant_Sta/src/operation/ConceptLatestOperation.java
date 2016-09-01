package operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import type.HistoryData;
import helper.Connector;
import helper.QueryTool;

public class ConceptLatestOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table ConceptLatest ("
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
			super.insert("insert into ConceptLatest" 
					+ "(sid,name,block)"
					+ "(select * from ConceptInf)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateTable(String siid)
	{
		HistoryData his=new HistoryDataOperation().selectLatest(siid);
		try {
			super.modify("update ConceptLatest Concept set"
				+"Concept.id="+his.getId()+","
				+"Concept.date='"+his.getDate()+"',"
				+"Concept.weekid="+his.getWeekid()+","
				+"Concept.monthid="+his.getMonthid()+","
				+"Concept.yearid="+his.getYearid()+","
				+"Concept.open="+his.getOpen()+","
				+"Concept.close="+his.getClose()+","
				+"Concept.increase="+his.getIncrease()+","
				+"Concept.incrPer="+his.getIncrPer()+","
				+"Concept.low="+his.getLow()+","
				+"Concept.high="+his.getHigh()+","
				+"Concept.volume="+his.getVolume()+","
				+"Concept.amount="+his.getAmount()+","
				+"Concept.turnover="+his.getTurnover()
			+"where Concept.sid='"+siid+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateAll()
	{
		List list=null;
		Connector conn=new Connector();
		try {
			list=QueryTool.resultSetToList(super.select(conn,"select distinct sid from ConceptLatest"));
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
			list=QueryTool.resultSetToList(super.select(conn,"select distinct sid from ConceptLatest"));
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
