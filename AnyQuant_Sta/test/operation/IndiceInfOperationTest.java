package operation;

import helper.JsonExchangeTool;

import java.util.ArrayList;
import java.util.List;

import type.StockInf;

public class IndiceInfOperationTest {
	public static IndiceInfOperation instance=new IndiceInfOperation();
	public static void SelectAllSiid()
	{
		System.out.println(new IndiceInfOperation().selectAllSiid("sh000001"));
	}
//	public static void updateTable()
//	{
//		instance.
//	}
	public static void main(String[] args)
	{
//		IndiceInfOperationTest.instance.initialTable();
		IndiceInfOperationTest.SelectAllSiid();
	}
}
