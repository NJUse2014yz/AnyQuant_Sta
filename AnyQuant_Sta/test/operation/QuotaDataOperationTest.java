package operation;

public class QuotaDataOperationTest {
	public static QuotaDataOperation instance=new QuotaDataOperation();
	public static void dropTable()
	{
		try {
			instance.dropTable("sh600000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void updateTable()
	{
		String siid="sh600000";
		try {
			instance.updateTable("s", siid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
//		QuotaDataOperationTest.dropTable();
		QuotaDataOperationTest.updateTable();
	}
}
