package operation;

import helper.Connector;

public class UserInfOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table UserInf("
					+ "userName varchar(35) not null primary key,"
					+ "password varchar(35) not null,"
//					+ "email varchar(35),"
//					+ "phoneNumber varchar(20),"
					+ "rank int default 0,"
					+ "score bigint default 0,"
					+ "stocks varchar(1000)"
					+ ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void dropTable()
	{
		try {
			super.dropTable("drop table UserInf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initialTable()
	{
		createTable();
		try {
			super.insert("insert into UserInf values('u0','0',0,0),('u1','1',1,1000),('u2','2',2,2000),('u3','3',3,3000),('u4','4',4,4000),('u5','5',5,5000)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
