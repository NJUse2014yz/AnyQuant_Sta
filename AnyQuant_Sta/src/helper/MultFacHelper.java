package helper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import operation.HistoryDataOperation;
import operation.QuotaDataOperation;
import operation.StockInfOperation;
import type.MultFacInf;
import type.MultFactData;
import type.StockInf;

public class MultFacHelper {

	private static StockInfOperation stockInfOperation = new StockInfOperation();
	private static HistoryDataOperation historyDataOperation = new HistoryDataOperation();
	private static QuotaDataOperation quotaDataOperation = new QuotaDataOperation();

	// 平均换手率，平均成交量,成交量变异率（20）vr，最旧的动向指标dmh,dml,tr
	public static MultFactData getAttriTop(String Attribute, int days, int sumsto) throws Exception {
		MultFactData multfactdata;
		ArrayList<String> result = new ArrayList<String>();
		List<StockInf> stolist = stockInfOperation.selectAllCondition("");
		ArrayList<MultFacInf> templist=new ArrayList<MultFacInf>();

		Calendar today = Calendar.getInstance();

		for(int i=0;i<stolist.size();i++){
			String stockid=stolist.get(i).getSid();
			List<Double> historyData=historyDataOperation.selectAttribute_num_date(stockid,"incrPer", days, new Date(today.getTimeInMillis()));
			
			int hisize=historyData.size();
			
			double incPerc=0.0;
			double attribute=0.0;
			
			if(hisize!=0){
				System.out.println(historyData);
				for(int k=0;k<historyData.size();k++){
					incPerc+=historyData.get(k);
				}
				
				List<Double> quotaData;
				
				if(Attribute.equals("turnover")||Attribute.equals("amount")) {
					historyData=historyDataOperation.selectAttribute_num_date(stockid,Attribute, days, new Date(today.getTimeInMillis()));
					for(int k=0;k<historyData.size();k++){
						attribute+=historyData.get(k);
					}
					attribute=attribute/hisize;
				}
				else if(Attribute.equals("vr"))
				{
					quotaData=quotaDataOperation.selectAttribute_num_date(stockid,Attribute, days, new Date(today.getTimeInMillis()));
					int num=0;
					for(int k=0;k<hisize;k+=20){
						attribute+=quotaData.get(k);
						num++;
					}
					attribute=attribute/num;
				}
				else if(Attribute.equals("dmh")||Attribute.equals("dml")||Attribute.equals("tr"))
				{
					quotaData=quotaDataOperation.selectAttribute_num_date(stockid,Attribute, days, new Date(today.getTimeInMillis()));
					attribute=quotaData.get(0);
				}
				else
				{
					
				}
			}
			MultFacInf inf=new MultFacInf(stockid,attribute,incPerc);
			templist.add(inf);
		}
		
		//按stosum依次计算；
		int teams=templist.size()/sumsto;
		if(teams<=0){
			System.out.println("the value of sumsto is unreasonable");
			return new MultFactData();
		}
		
		double[] testResult=new double[teams];
		for(int i=0;i<teams;i++){
			for(int k=0;k<sumsto;k++){
				testResult[i]+=templist.get(i*sumsto+k).IncPerc;
			}
		}

		//查找到最大收益
		int max=0;
		for(int t=1;t<teams;t++){
			if(testResult[t]>testResult[max]){
				max=t;
			}
		}
		for(int i=0;i<sumsto;i++){
			result.add(templist.get(max*sumsto+i).stockId);
			System.out.println(i+"  "+templist.get(max*sumsto+i).stockId);
		}
		multfactdata=new MultFactData(Attribute,result,testResult[max]/10,days,sumsto);
		
		return multfactdata;
	}
	
	public static void sort(ArrayList<MultFacInf> list){
		for(int i=0;i<list.size();i++){
			for(int k=0;k<list.size()-1;k++){
				if(list.get(k).attribute<list.get(k+1).attribute){
					MultFacInf trans=list.get(k);
					list.set(k, list.get(k+1));
					list.set(k+1, trans);
				}
			}
		}
	}
}
