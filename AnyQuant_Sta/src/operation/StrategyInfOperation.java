package operation;

public class StrategyInfOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table StrategyInf("
					+ "name varchar(15) not null primary key,"
					+ "discription varchar(10000),"
					+ "type varchar(16),"
					+ "rank int"
					+ ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insertStrategyInf(String name,String discription,String type,int rank)
	{
		try {
			super.insert("insert into StrategyInf(name,discription,type,rank) "
					+ "values('"+name+"','"+discription+"','"+type+"',"+rank+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void changeDiscription(String name,String discription)
	{
		try {
			super.modify("update StrategyInf set discription='"+discription+"' where name='"+name+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void changeRank(String name,int rank)
	{
		try {
			super.modify("update StrategyInf set rank="+rank+" where name='"+name+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
