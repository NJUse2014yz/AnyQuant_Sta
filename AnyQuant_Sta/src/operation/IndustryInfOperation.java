package operation;

import java.io.IOException;
import java.util.List;

import helper.CsvTool;

public class IndustryInfOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table IndustryInf ("
				+"sid varchar(16) not null,"
				+"name varchar(32),"
				+"cname varchar(32) not null"
				+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void dropTable()
	{
		try {
			super.dropTable("drop table IndustryInf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initailTable()
	{
		dropTable();
		createTable();
		List<List<String>> list=null;
		try {
			list=new CsvTool("src/mysql/IndustryInf.csv").readCSVFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sql="insert into IndustryInf(sid,name,cname) values";
		List<String> sublist0=list.get(1);
		sql+="('"+sublist0.get(0)+"','"+sublist0.get(1)+"','"+sublist0.get(2)+"')";
		for(int i=2;i<list.size();i++)
		{
			List<String> sublist=list.get(i);
			sql+=",('"+sublist.get(0)+"','"+sublist.get(1)+"','"+sublist.get(2)+"')";
		}
		try {
			super.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
