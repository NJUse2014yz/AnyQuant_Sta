package helper;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {
	public static final String url = "jdbc:mysql://114.55.37.133:25003/AnyQuant?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "AnyQuant";
    public static final String password = "";
 
    public Connection conn = null;  
    public PreparedStatement pst = null;
    public ResultSet rst = null;
  
    public Connector(String sql) {
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    public Connector()
    {
    	try{
	    	Class.forName(name);
	    	conn = DriverManager.getConnection(url, user, password);//获取连接
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }
    }
    
    public void execute(String sql)
    {
    	try {
			pst = conn.prepareStatement(sql);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public ResultSet executeQuery(String sql)
    {
    	try {
			pst = conn.prepareStatement(sql);
			return pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }
    
    public void create()
    {
    	
    }
    
    public void insert()
    {
    	
    }
    
    public void update()
    {
    	
    }
    
    public void delete()
    {
    	
    }
    
    public void drop()
    {
    	
    }
}
