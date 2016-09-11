package operation;

import helper.Connector;
import helper.QueryTool;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import type.IndiceInf;

public class IndiceInfOperation extends Operation{
	
	public void createTable(){
		try {
			super.createTable("create table IndiceInf(" 
					+ "id int(11),"
					+ "iid varchar(16) not null primary key,"
					+ "iname varchar(32)," 
					+ "date date,"
					+ "hisid int(11) not null default-1,"
					+ "quoid int(11) not null default-1,"
					+ "weekid int(11) not null default-1,"
					+ "weekhisid int(11) not null default-1,"
					+ "weekquoid int(11) not null default-1,"
					+ "monthid int(11) not null default-1,"
					+ "monthhisid int(11) not null default-1,"
					+ "monthquoid int(11) not null default-1" 
					+ ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dropTable(){
		try {
			super.dropTable("drop table IndiceInf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialTable(){
		dropTable();
		createTable();
		try {
			super.insert("insert into IndiceInf(id,iid,iname) "
					+"values(0,'sh000001','上证指数'),"
					+"(1,'sh000002','A股指数'),"
					+"(2,'sh000003','B股指数'),"
					+"(3,'sh000004','工业指数'),"
					+"(4,'sh000005','商业指数'),"
					+"(5,'sh000006','地产指数'),"
					+"(6,'sh000007','公用指数'),"
					+"(7,'sh000008','综合指数'),"
					+"(8,'sh000010','上证180'),"
					+"(9,'sh000011','沪市基金'),"
					+"(10,'sh000012','国债指数'),"
					+"(11,'sh000013','企债指数'),"
					+"(12,'sh000015','红利指数'),"
					+"(13,'sh000016','上证50'),"
					+"(14,'sh000017','新综指'),"
					+"(15,'sh000019','治理指数'),"
					+"(16,'sh000042','上证央企'),"
					+"(17,'sh000043','超大盘'),"
					+"(18,'sh000049','上证民企'),"
					+"(19,'sh000054','上证海外'),"
					+"(20,'sh000055','上证地企'),"
					+"(21,'sh000056','上证国企'),"
					+"(22,'sh000057','全指成长'),"
					+"(23,'sh000058','全指价值'),"
					+"(24,'sh000059','全R成长'),"
					+"(25,'sh000060','全R价值'),"
					+"(26,'sh000061','沪企债30'),"
					+"(27,'sh000062','上证沪企'),"
					+"(28,'sh000063','上证周期'),"
					+"(29,'sh000064','非周期'),"
					+"(30,'sh000066','上证商品'),"
					+"(31,'sh000067','上证新兴'),"
					+"(32,'sh000159','沪股通'),"
					+"(33,'sh000300','沪深300'),"
					+"(35,'sh000903','中证100'),"
					+"(36,'sh000905','中证500'),"
					+"(37,'sh000906','中证800'),"
					+"(38,'sh000914','300金融'),"
					+"(39,'sh000922','中证红利'),"
					+"(40,'sh000923','公司债'),"
					+"(41,'sh000926','央证央企（上）'),"
					+"(42,'sh000927','央企100（上）'),"
					+"(43,'sh000964','中证新兴'),"
					+"(44,'sh000999','两岸三地'),"
					+"(45,'sz399001','深证成指'),"
					+"(46,'sz399002','深成指R'),"
					+"(47,'sz399003','成份B股'),"
					+"(48,'sz399004','深证100R'),"
					+"(49,'sz399005','中小板指'),"
					+"(50,'sz399006','创业板指'),"
					+"(51,'sz399007','深证300'),"
					+"(52,'sz399008','中小300'),"
					+"(53,'sz399101','中小板综'),"
					+"(54,'sz399102','创业板综'),"
					+"(55,'sz399106','深证综指'),"
					+"(56,'sz399107','深证A指'),"
					+"(57,'sz399108','深证B指'),"
					+"(58,'sz399300','沪深300'),"
					+"(59,'sz399305','深市基金'),"
					+"(60,'sz399352','深报综指'),"
					+"(61,'sz399369','CBN兴全'),"
					+"(62,'sz399481','企债指数'),"
					+"(63,'sz399606','创业板R'),"
					+"(64,'sz399926','中证央企（深）'),"
					+"(65,'sz399927','央企100（深）')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void modify(String set)
	{
		try {
			super.modify("update IndiceInf set "+set);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String condition)
	{
		try {
			super.delete("delete IndiceInf where "+condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<IndiceInf> selectAllCondition(String condition) {
		List<IndiceInf> indiceInfList=new ArrayList<IndiceInf>();
		ResultSet resultSet=null;
		Connector conn=new Connector();
		try {
			resultSet=super.select(conn,"select * from IndiceInf "+condition);
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
//			System.out.println(map);
			indiceInfList.add(new IndiceInf(
					(int)map.get("id"),(String)map.get("iid"),(String)map.get("iname"),(Date)map.get("date"),(int)map.get("hisid"),(int)map.get("quoid"),
					(int)map.get("weekid"),(int)map.get("weekhisid"),(int)map.get("weekquoid"),
					(int)map.get("monthid"),(int)map.get("monthhisid"),(int)map.get("monthquoid")));	
		}
		return indiceInfList;
	}
	
	public IndiceInf selectAllSiid(String siid)
	{
		return selectAllCondition("where iid='"+siid+"'").get(0);
	}
}
