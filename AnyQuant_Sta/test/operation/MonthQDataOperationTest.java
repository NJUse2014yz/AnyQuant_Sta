package operation;

public class MonthQDataOperationTest {
	public static MonthQDataOperation instance=new MonthQDataOperation();
	public static void updateTable()
	{
		String siid="sh600000";
		try {
			instance.updateTable("s", siid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("updateTable"+siid+" done");
	}
	public static void main(String[] args)
	{
		MonthQDataOperationTest.updateTable();
	}
}
