package operation;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import type.StockInf;
import type.StockPair;
import helper.Connector;
import helper.JsonExchangeTool;
import helper.PairStrategy;
import helper.QueryTool;

public class StockInfOperation extends Operation{
	public static int partnerNum=3;
	public void createTable(){
		try {
			super.createTable("create table StockInf(" 
					+ "id int(11),"
					+ "sid varchar(16) not null primary key,"
					+ "sname varchar(32)," 
					+ "date date,"
					+ "hisid int(11) not null default-1,"
					+ "quoid int(11) not null default-1,"
					+ "weekid int(11) not null default-1,"
					+ "weekhisid int(11) not null default-1,"
					+ "weekquoid int(11) not null default-1,"
					+ "monthid int(11) not null default-1,"
					+ "monthhisid int(11) not null default-1,"
					+ "monthquoid int(11) not null default-1," 
					+ "partner1 varchar(16),"
					+ "coe1 double,"
					+ "partner2 varchar(16),"
					+ "coe2 double,"
					+ "partner3 varchar(16),"
					+ "coe3 double"
					+ ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dropTable(){
		try {
			super.dropTable("drop table StockInf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialTable(){
		dropTable();
		createTable();
		List<StockInf> stockInfList=new ArrayList<StockInf>();
		List<StockInf> list = JsonExchangeTool.getStockInf();
		System.out.println(list);
		for(int i=0;i<list.size();i++)
		{
			stockInfList.add(list.get(i));
		}
		List<String> attributes=new ArrayList<String>();
		attributes.add("id");
		attributes.add("sid");
		attributes.add("sname");
		attributes.add("date");
		attributes.add("hisid");
		attributes.add("quoid");
		attributes.add("weekid");
		attributes.add("weekhisid");
		attributes.add("weekquoid");
		attributes.add("monthid");
		attributes.add("monthhisid");
		attributes.add("monthquoid");
		insert(attributes, stockInfList);
	}
	
	public void insert(List<String> attributes,List<StockInf> stockInfList){
		String sql="insert into StockInf(";
		sql+=attributes.get(0);
		for(int i=1;i<attributes.size();i++)
		{
			sql+=","+attributes.get(i);
		}
		sql+=") values";
		
		for(int i=0;i<stockInfList.size();i++)
		{
			for(int j=0;j<attributes.size();j++)
			{
				sql=stockInfElement(sql,attributes.get(j),j,stockInfList.get(i),i);
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
	
	private String stockInfElement(String sql,String attribute,int j,StockInf stockInf,int i)
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
			sql+=stockInf.getId();
			break;
		case "sid":
			sql+="'"+stockInf.getSid()+"'";
			break;
		case "sname":
			sql+="'"+stockInf.getSname()+"'";
			break;
		case "date":
			if(stockInf.getDate()!=null)
			{
				sql+="'"+stockInf.getDate()+"'";
			}
			else
			{
				sql+="null";
			}
			break;
		case "hisid":
			sql+=stockInf.getHisid();
			break;
		case "quoid":
			sql+=stockInf.getQuoid();
			break;
		case "weekid":
			sql+=stockInf.getWeekid();
			break;
		case "weekhisid":
			sql+=stockInf.getWeekhisid();
			break;
		case "weekquoid":
			sql+=stockInf.getWeekquoid();
			break;
		case "monthid":
			sql+=stockInf.getMonthid();
			break;
		case "monthhisid":
			sql+=stockInf.getWeekhisid();
			break;
		case "monthquoid":
			sql+=stockInf.getWeekquoid();
			break;
		}
		return sql;
	}

	public void updatePartner(String siid,List<String> sidlist)
	{
		List<StockPair> result=null;
//		List<String> sidlist=selectSiidCondition("");
		try {
			result=PairStrategy.getPair(siid,sidlist,partnerNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modify("partner1='"+result.get(0).sid+"' "+",coe1="+result.get(0).correlation
				+",partner2='"+result.get(1).sid+"' "+",coe2="+result.get(1).correlation
				+",partner3='"+result.get(2).sid+"' "+",coe3="+result.get(2).correlation
				+" where sid='"+siid+"'");
	}
	
	public void updatePartnerAll()
	{
		List<String> list=selectSiidCondition("");
		for(int i=0;i<list.size();i++)
		{
			updatePartner(list.get(i),list);
		}
	}
	
	public void modify(String set)
	{
		try {
			super.modify("update StockInf set "+set);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String condition)
	{
		try {
			super.delete("delete StockInf where "+condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> selectSiidCondition(String condition) {
		List<String> sidList=new ArrayList<String>();
		ResultSet resultSet=null;
		Connector conn=new Connector();
		try {
			resultSet=super.select(conn,"select sid from StockInf "+condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<HashMap<String,Object>> list=null;
		try {
			list=QueryTool.resultSetToList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		for(int i=0;i<list.size();i++)
		{
			HashMap<String,Object> map=list.get(i);
			sidList.add((String)map.get("sid"));	
		}
		return sidList;
	}

	public List<StockInf> selectAllCondition(String condition) {
		List<StockInf> stockInfList=new ArrayList<StockInf>();
		ResultSet resultSet=null;
		Connector conn=new Connector();
		try {
			resultSet=super.select(conn,"select * from StockInf "+condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<HashMap<String,Object>> list=null;
		try {
			list=QueryTool.resultSetToList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<list.size();i++)
		{
			HashMap<String,Object> map=list.get(i);
			stockInfList.add(new StockInf(
					(int)map.get("id"),(String)map.get("sid"),(String)map.get("sname"),(Date)map.get("date"),(int)map.get("hisid"),(int)map.get("quoid"),
					(int)map.get("weekid"),(int)map.get("weekhisid"),(int)map.get("weekquoid"),
					(int)map.get("monthid"),(int)map.get("monthhisid"),(int)map.get("monthquoid")));	
		}
		return stockInfList;
	}
	
	public StockInf selectAllSiid(String siid)
	{
		return selectAllCondition("where sid='"+siid+"'").get(0);
	}
}
