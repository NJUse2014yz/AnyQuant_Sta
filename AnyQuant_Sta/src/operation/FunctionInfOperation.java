package operation;

public class FunctionInfOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table Function("
					+ "name varchar(15) not null primary key,"
					+ "discription mediumtext,"
					+ "type varchar(16),"
					+ "rank int"
					+ ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insertFunction(String name,String discription,String type,int rank)
	{
		try {
			super.insert("insert into Function(name,discription,type,rank) "
					+ "values('"+name+"','"+discription+"','"+type+"',"+rank+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void changeDiscription(String name,String discription)
	{
		try {
			super.modify("update Function set discription='"+discription+"' where name='"+name+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void changeRank(String name,int rank)
	{
		try {
			super.modify("update Function set rank="+rank+" where name='"+name+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
