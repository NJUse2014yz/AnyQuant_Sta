package operation;

public class WeekQDataOperationTest {
	public static WeekQDataOperation instance=new WeekQDataOperation();
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
		WeekQDataOperationTest.updateTable();
	}
}
