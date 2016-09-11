package operation;

import helper.MultFacHelper;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import type.MultFactData;


public class MultFactStockRecommendOperation extends Operation{
	public void createTable()
	{
		try {
			super.createTable("create table MultFactStockRecommend"
					+ "(attribute varchar(35),"
					+ "incPerc double,"
					+ "days int,"
					+ "sumsto int,"
					+ "stolist varchar(1000),"
					+ "primary key (attribute,incPerc,days,sumsto))");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteTable()
	{
		try {
			super.delete("drop table MultFactStockRecommend");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initialTable()
	{
		this.deleteTable();
		this.createTable();
		List<MultFactData> list=new ArrayList<MultFactData>();
		try {
			list = getAllResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql="insert into MultFactStockRecommend(attribute,incPerc,days,sumsto,stolist)"
				+" values";
		MultFactData multFactData0=list.get(0);
		sql+="('"+multFactData0.getAttribute()+"',"
				+multFactData0.getIncPerc()+","
				+multFactData0.getDays()+","
				+multFactData0.getSumsto()+","
				+"'"+JSONArray.fromObject(multFactData0.getStolist())+"'"
				+")";
		for(int i=1;i<list.size();i++)
		{
			MultFactData multFactData=list.get(i);
			sql+=",('"+multFactData.getAttribute()+"',"
					+multFactData.getIncPerc()+","
					+multFactData.getDays()+","
					+multFactData.getSumsto()+","
					+"'"+JSONArray.fromObject(multFactData.getStolist())+"'"
					+")";
		}
//		System.out.println(sql);
		try {
			super.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private ArrayList<MultFactData> getAllResult() throws Exception{
		ArrayList<MultFactData> result=new ArrayList<MultFactData>();
		int[] days={30,60,300};
		int[] sum={10,15,50};
		String[] attribute={"turnover","amount","vr","dmh","dml","tr"};
		
		for(int i=0;i<attribute.length;i++){
			for(int k=0;k<days.length;k++){
				for(int m=0;m<sum.length;m++){
					MultFactData temp=MultFacHelper.getAttriTop(attribute[i], days[k], sum[m]);
					if(temp.sumsto!=0){
						result.add(temp);
					}
				}
			}
		}
//		System.out.println(result.size());
		return result;
	}
}
