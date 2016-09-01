package operation;

import helper.JsonExchangeTool;

import java.util.ArrayList;
import java.util.List;

import type.StockInf;

public class StockInfOperationTest {
	public static void testInsert()
	{
		new StockInfOperation().dropTable();
		new StockInfOperation().createTable();
		List<StockInf> stockInfList=new ArrayList<StockInf>();
		List<StockInf> list = JsonExchangeTool.getStockInf();
		for(int i=0;i<10;i++)
		{
			stockInfList.add(list.get(i));
		}
		for(int i=list.size()-1;i>list.size()-11;i--)
		{
			stockInfList.add(list.get(i));
		}
		System.out.println(stockInfList);
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
		new StockInfOperation().insert(attributes, stockInfList);
		new StockInfOperation().modify("id=id-2924 where id>10");
	}
	public static void selectAllSiid()
	{
		System.out.println(new StockInfOperation().selectAllSiid("sh600000"));
	}
	public static void main(String[] args)
	{
		StockInfOperationTest.selectAllSiid();
	}
}
