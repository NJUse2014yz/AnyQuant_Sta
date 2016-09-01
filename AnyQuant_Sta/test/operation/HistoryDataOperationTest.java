package operation;

public class HistoryDataOperationTest {
	public static HistoryDataOperation instance=new HistoryDataOperation();
	
	public static void dropAll()
	{
		instance.dropAll();
		System.out.println("dropAll done");
	}
	
//	public static void initialTable()
//	{
//		String siid="sh600000";
//		instance.updateTable("s",siid);
//		System.out.println("initailTable"+siid+" done");
//	}
	
	public static void updateTable()
	{
		String siid="sh000001";
		instance.updateTable("i",siid);
		System.out.println("updateTable"+siid+" done");
	}
	
	public static void selectLatest()
	{
		String siid="sh600004";
		System.out.println(instance.selectLatest(siid));
		System.out.println("selectLatest"+siid+" done");
	}
	
	public static void main(String[] args)
	{
//		HistoryDataOperationTest.initialTable();
		HistoryDataOperationTest.updateTable();
//		HistoryDataOperationTest.selectLatest();
	}

}
