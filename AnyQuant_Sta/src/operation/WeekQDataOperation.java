package operation;

import helper.Connector;
import helper.MMSTool;
import helper.QueryTool;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import type.HistoryData;
import type.IndiceInf;
import type.QuotaData;
import type.StockInf;

public class WeekQDataOperation extends Operation{
	
	public static StockInfOperation stockInfOperation=new StockInfOperation();
	public static IndiceInfOperation indiceInfOperation=new IndiceInfOperation();
	public static HistoryDataOperation historyDataOperation=new HistoryDataOperation();
	public static QuotaDataOperation quotaDataOperation=new QuotaDataOperation();
	public static WeekHDataOperation weekHDataOperation=new WeekHDataOperation();
	public static WeekQDataOperation weekQDataOperation=new WeekQDataOperation();
	public static List<HistoryData> hisList=null;//全部历史数据
	public static List<QuotaData> quoList=null;//已有全部指标数据
	public static List<QuotaData> newquoList=new ArrayList<QuotaData>();
	public static int quoid=-1;
	public static int hisid=-1;
	
	public void createTable(String siid)
	{
		try {
			super.createTable("create table WeekQData_"+siid+"("
				+"weekid int(11) not null primary key,"
				+"date date not null,"
				+"m5 double default 0,"
				+"m10 double default 0,"
				+"m20 double default 0,"
				+"m30 double default 0,"
				+"bias5 double default 0,"
				+"bias10 double default 0,"
				+"bias20 double default 0,"
				+"boll1 double default 0,"
				+"boll2 double default 0,"
				+"boll3 double default 0,"
				+"rsi double default 0,"
				+"rsi5 double default 0,"
				+"rsi10 double default 0,"
				+"rsi20 double default 0,"
				+"vr double default 0,"
				+"rsv double default 0,"
				+"k double default 50,"
				+"d double default 50,"
				+"j double default 0,"
				+"ema12 double default 0,"
				+"ema26 double default 0,"
				+"ema50 double default 0,"
				+"ema5 double default 0,"
				+"ema35 double default 0,"
				+"ema10 double default 0,"
				+"ema60 double default 0,"
				+"diff double default 0,"
				+"dea double default 0,"
				+"macd double default 0,"
				+"dmh double default 0,"
				+"dml double default 0,"
				+"tr double default 0,"
				+"dmh12 double default 0,"
				+"dml12 double default 0,"
				+"tr12 double default 0,"
				+"dih double default 0,"
				+"dil double default 0,"
				+"dih12 double default 0,"
				+"dil12 double default 0,"
				+"dx double default 0,"
				+"adx double default 0,"
				+"adxr double default 0,"
				+"obv double default 0,"
				+"roc12 double default 0,"
				+"roc25 double default 0"
				+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateAll()
	{
		List<StockInf> stockList=stockInfOperation.selectAllCondition("");
		for(int i=0;i<stockList.size();i++)
		{
			updateTable("s",stockList.get(i).getSid());
		}
		List<IndiceInf> indiceList=indiceInfOperation.selectAllCondition("");
		for(int i=0;i<indiceList.size();i++)
		{
			updateTable("i",indiceList.get(i).getIid());
		}
	}

	public void updateTable(String type,String siid)
	{
		List<String> attributes=new ArrayList<String>();
		attributes.add("weekid");
		attributes.add("date");
		attributes.add("m5");
		attributes.add("m10");
		attributes.add("m20");
		attributes.add("m30");
		attributes.add("bias5");
		attributes.add("bias10");
		attributes.add("bias20");
		attributes.add("boll1");
		attributes.add("boll2");
		attributes.add("boll3");
		attributes.add("rsi");
		attributes.add("rsi5");
		attributes.add("rsi10");
		attributes.add("rsi20");
		attributes.add("vr");
		attributes.add("rsv");
		attributes.add("k");
		attributes.add("d");
		attributes.add("j");
		attributes.add("ema12");
		attributes.add("ema26");
		attributes.add("ema50");
		attributes.add("ema5");
		attributes.add("ema35");
		attributes.add("ema10");
		attributes.add("ema60");
		attributes.add("diff");
		attributes.add("dea");
		attributes.add("macd");
		attributes.add("dmh");
		attributes.add("dml");
		attributes.add("tr");
		attributes.add("dmh12");
		attributes.add("dml12");
		attributes.add("tr12");
		attributes.add("dih");
		attributes.add("dil");
		attributes.add("dih12");
		attributes.add("dil12");
		attributes.add("dx");
		attributes.add("adx");
		attributes.add("adxr");
		attributes.add("obv");
		attributes.add("roc12");
		attributes.add("roc25");
		
		if(hisList!=null)
			hisList.clear();
		if(quoList!=null)
			quoList.clear();
		if(newquoList!=null)
			newquoList.clear();
		quoid=-1;
		hisid=-1;
		if(type.equals("s"))
		{
			StockInf stockInf=stockInfOperation.selectAllSiid(siid);
			hisid=stockInf.getWeekhisid();////
			quoid=stockInf.getWeekquoid();////
		}
		else if(type.equals("i"))
		{
			IndiceInf indiceInf=indiceInfOperation.selectAllSiid(siid);
			hisid=indiceInf.getWeekhisid();////
			quoid=indiceInf.getWeekquoid();////
		}
		else
		{
			return ;
		}
		if(hisid==-1)
		{
			return ;
		}
		if(quoid==-1)
		{
			createTable(siid);////
		}
		if(quoid==hisid)
		{
			return;
		}
		
		hisList=weekHDataOperation.selectCondition(siid,"where weekid<="+hisid);
		quoList=weekQDataOperation.selectCondition(siid,"where weekid<="+quoid);
		
		for(int i=quoid+1;i<=hisid;i++)
		{
			HistoryData historyData=hisList.get(i);
			QuotaData quotaData=new QuotaData();
			newquoList.add(quotaData);
			quotaData.setId(i);
			quotaData.setDate(historyData.getDate());
			quotaData.setM5(historyData.getClose());
			quotaData.setM10(historyData.getClose());
			quotaData.setM20(historyData.getClose());
			quotaData.setM30(historyData.getClose());
			quotaData.setBoll1(historyData.getClose());
			quotaData.setBoll2(historyData.getClose());
			quotaData.setBoll3(historyData.getClose());
			quotaData.setEma12(historyData.getClose());
			quotaData.setEma26(historyData.getClose());
			quotaData.setEma50(historyData.getClose());
			quotaData.setEma5(historyData.getClose());
			quotaData.setEma35(historyData.getClose());
			quotaData.setEma10(historyData.getClose());
			quotaData.setEma60(historyData.getClose());
			quotaData.setRsv(100);
			quotaData.setK(50);
			quotaData.setD(50);
			quotaData.setJ(50);
			quotaData.setObv(historyData.getVolume());
			
			try{
				setM(siid,quotaData);
				setBias(siid,quotaData);
				setBoll(siid,quotaData);
				setRsi(siid,quotaData);
				setVr(siid,quotaData);
				setRsv(siid,quotaData);
				setKDJ(siid,quotaData);
				setEma(siid,quotaData);
				setDiff(siid,quotaData);
				setDea(siid,quotaData);
				setMacd(siid,quotaData);
				setDmi(siid,quotaData);
				setObv(siid,quotaData);
				setRoc(siid,quotaData);
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		insert(siid,attributes,newquoList);////
		
		if(type.equals("s"))
		{
			stockInfOperation.modify("weekquoid="+hisid+" where sid='"+siid+"'");
		}
		else if(type.equals("i"))
		{
			indiceInfOperation.modify("weekquoid="+hisid+" where iid='"+siid+"'");
		}
	}
	
	public void insert(String siid,List<String> attributes,List<QuotaData> quotaDataList)
	{
		String sql="insert into WeekQData_"+siid+"(";
		sql+=attributes.get(0);
		for(int j=1;j<attributes.size();j++)
		{
			sql+=","+attributes.get(j);
		}
		sql+=") values";
		for(int i=0;i<quotaDataList.size();i++)
		{
			QuotaData quotaData=quotaDataList.get(i);
			for(int j=0;j<attributes.size();j++)
			{
				sql=quotaDataElement(sql,attributes.get(j),j,quotaData,i);
			}
			sql+=")";
		}
//		System.out.println(sql);
		try {
			super.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String quotaDataElement(String sql,String attribute,int j,QuotaData quotaData,int i)
	{
		if(i==0)
		{
			if(j==0)
			{
				sql+="(";
			}
			else
			{
				sql+=",";
			}
		}
		else
		{
			if(j==0)
			{
				sql+=",(";
			}
			else
			{
				sql+=",";
			}
		}
		
		switch(attribute)
		{
		case "weekid":
			sql+=quotaData.getId();
			break;
		case "date":
			sql+="'"+quotaData.getDate()+"'";
			break;
		case "m5":
			sql+=quotaData.getM5();
			break;
		case "m10":
			sql+=quotaData.getM10();
			break;
		case "m20":
			sql+=quotaData.getM20();
			break;
		case "m30":
			sql+=quotaData.getM30();
			break;
		case "bias5":
			sql+=quotaData.getBias5();
			break;
		case "bias10":
			sql+=quotaData.getBias10();
			break;
		case "bias20":
			sql+=quotaData.getBias20();
			break;
		case "boll1":
			sql+=quotaData.getBoll1();
			break;
		case "boll2":
			sql+=quotaData.getBoll2();
			break;
		case "boll3":
			sql+=quotaData.getBoll3();
			break;
		case "rsi":
			sql+=quotaData.getRsi();
			break;
		case "rsi5":
			sql+=quotaData.getRsi5();
			break;
		case "rsi10":
			sql+=quotaData.getRsi10();
			break;
		case "rsi20":
			sql+=quotaData.getRsi20();
			break;
		case "vr":
			sql+=quotaData.getVr();
			break;
		case "rsv":
			sql+=quotaData.getRsv();
			break;
		case "k":
			sql+=quotaData.getK();
			break;
		case "d":
			sql+=quotaData.getD();
			break;
		case "j":
			sql+=quotaData.getJ();
			break;
		case "ema12":
			sql+=quotaData.getEma12();
			break;
		case "ema26":
			sql+=quotaData.getEma26();
			break;
		case "ema50":
			sql+=quotaData.getEma50();
			break;
		case "ema5":
			sql+=quotaData.getEma5();
			break;
		case "ema35":
			sql+=quotaData.getEma35();
			break;
		case "ema10":
			sql+=quotaData.getEma10();
			break;
		case "ema60":
			sql+=quotaData.getEma60();
			break;
		case "diff":
			sql+=quotaData.getDiff();
			break;
		case "dea":
			sql+=quotaData.getDea();
			break;
		case "macd":
			sql+=quotaData.getMacd();
			break;
		case "dmh":
			sql+=quotaData.getDmh();
			break;
		case "dml":
			sql+=quotaData.getDml();
			break;
		case "tr":
			sql+=quotaData.getTr();
			break;
		case "dmh12":
			sql+=quotaData.getDmh12();
			break;
		case "dml12":
			sql+=quotaData.getDml12();
			break;
		case "tr12":
			sql+=quotaData.getTr12();
			break;
		case "dih":
			sql+=quotaData.getDih();
			break;
		case "dil":
			sql+=quotaData.getDil();
			break;
		case "dih12":
			sql+=quotaData.getDih12();
			break;
		case "dil12":
			sql+=quotaData.getDil12();
			break;
		case "dx":
			sql+=quotaData.getDx();
			break;
		case "adx":
			sql+=quotaData.getAdx();
			break;
		case "adxr":
			sql+=quotaData.getAdxr();
			break;
		case "obv":
			sql+=quotaData.getObv();
			break;
		case "roc12":
			sql+=quotaData.getRoc12();
			break;
		case "roc25":
			sql+=quotaData.getRoc25();
			break;
		}
		return sql;
	}
	
	public List<QuotaData> selectCondition(String siid,String condition)
	{
//		System.out.println("select * from HistoryData_"+siid+" "+condition);
		List list=null;
		List<QuotaData> quotaDataList=new ArrayList<QuotaData>();
		Connector conn=new Connector();
		try {
			list = QueryTool.resultSetToList(super.select(conn,"select * from WeekQData_"+siid+" "+condition));
			conn.close();
			if(list.size()==0)
				return null;
			for(int i=0;i<list.size();i++)
			{
				HashMap hm=(HashMap)list.get(i);
	//			System.out.println(hm.get("date").getClass());
				quotaDataList.add(new QuotaData(
						(int)hm.get("weekid"),(Date)hm.get("date"),
						(double)hm.get("m5"),(double)hm.get("m10"),(double)hm.get("m20"),(double)hm.get("m30"),
						(double)hm.get("bias5"),(double)hm.get("bias10"),(double)hm.get("bias20"),
						(double)hm.get("boll1"),(double)hm.get("boll2"),(double)hm.get("boll3"),
						(double)hm.get("rsi"),(double)hm.get("rsi5"),(double)hm.get("rsi10"),(double)hm.get("rsi20"),
						(double)hm.get("vr"),(double)hm.get("rsv"),(double)hm.get("k"),(double)hm.get("d"),(double)hm.get("j"),
						(double)hm.get("ema12"),(double)hm.get("ema26"),(double)hm.get("ema50"),(double)hm.get("ema5"),(double)hm.get("ema35"),(double)hm.get("ema10"),(double)hm.get("ema60"),
						(double)hm.get("diff"),(double)hm.get("dea"),(double)hm.get("macd"),
						(double)hm.get("dmh"),(double)hm.get("dml"),(double)hm.get("tr"),(double)hm.get("dmh12"),(double)hm.get("dml12"),(double)hm.get("tr12"),(double)hm.get("dih"),(double)hm.get("dil"),(double)hm.get("dih12"),(double)hm.get("dil12"),(double)hm.get("dx"),(double)hm.get("adx"),(double)hm.get("adxr"),
						(double)hm.get("obv"),(double)hm.get("roc12"),(double)hm.get("roc25")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quotaDataList;
	}
	
	/**设置均值*/
	public static void setM(String siid,QuotaData quotaData) throws Exception
	{
		if(quotaData.getId()>0)
		{
			int n5=5;
			int n10=10;
			int n20=20;
			int n30=30;
			HistoryData nowhistoryData=hisList.get(quotaData.getId());
			
			if(quotaData.getId()>=n5-1)
			{
				double sum5=0;
				for(int i=quotaData.getId()-n5+1;i<=quotaData.getId();i++)
				{
					sum5+=hisList.get(i).getClose();
				}
				quotaData.setM5(sum5/n5);
			}
			
			if(quotaData.getId()>=n10-1)
			{
				double sum10=0;
				for(int i=quotaData.getId()-n10+1;i<=quotaData.getId();i++)
				{
					sum10+=hisList.get(i).getClose();
				}
				quotaData.setM10(sum10/n10);
			}
			
			if(quotaData.getId()>=n20-1)
			{
				double sum20=0;
				for(int i=quotaData.getId()-n20+1;i<=quotaData.getId();i++)
				{
					sum20+=hisList.get(i).getClose();
				}
				quotaData.setM20(sum20/n20);
			}
			
			if(quotaData.getId()>=n30-1)
			{
				double sum30=0;
				for(int i=quotaData.getId()-n30+1;i<=quotaData.getId();i++)
				{
					sum30+=hisList.get(i).getClose();
				}
				quotaData.setM30(sum30/n30);
			}
		}
	}
	
	/**设置乖离率*/
	public static void setBias(String siid,QuotaData quotaData) throws Exception
	{
		int n5=5;	
		double sum6=0;
		int n10=10;	
		double sum10=0;
		int n20=20;	
		double sum20=0;
		
		HistoryData nowhistoryData=hisList.get(quotaData.getId());
		
		if(quotaData.getId()>=n5-1)
		{
			for(int i=quotaData.getId()-n5+1;i<=quotaData.getId();i++)
			{
				sum6+=hisList.get(i).getClose();
			}
		}
		if(quotaData.getId()>=n10-1)
		{
			for(int i=quotaData.getId()-n10+1;i<=quotaData.getId();i++)
			{
				sum10+=hisList.get(i).getClose();
			}
		}
		if(quotaData.getId()>=n20-1)
		{
			for(int i=quotaData.getId()-n20+1;i<=quotaData.getId();i++)
			{
				sum20+=hisList.get(i).getClose();
			}
		}
		
		if(sum6!=0)
			quotaData.setBias5(100*((nowhistoryData.getClose() - sum6/n5) / (sum6/n5)));
		if(sum10!=0)
			quotaData.setBias10(100*((nowhistoryData.getClose() - sum10/n10) / (sum10/n10)));
		if(sum20!=0)
			quotaData.setBias20(100*((nowhistoryData.getClose() - sum20/n20) / (sum20/n20)));
	}

	/**设置布林线 */
	public static void setBoll(String siid,QuotaData quotaData) throws Exception
	{
		int n20=20;
		if(quotaData.getId()>=n20-1)
		{
			double S = 0.0;
			for (int i =quotaData.getId()-n20+1;i<=quotaData.getId(); i++) {
				S += Math.pow((hisList.get(i).getClose() - quotaData.getM20()), 2);
			}
			S = S / n20;
			S = Math.sqrt(S);
	
			quotaData.setBoll1(quotaData.getM20() + 2 * S);
			quotaData.setBoll2(quotaData.getM20());
			quotaData.setBoll3(quotaData.getM20() - 2 * S);
		}
	}

	/**设置强弱指标 */
	public static void setRsi(String siid,QuotaData quotaData) throws Exception{
		int n6=6;
		int n12=12;
		int n24=24;
		int n14=14;
		
		if(quotaData.getId()>=n14-1)
		{
			double increase=0.0;
			double decrease=0.0;
			double RS = 100.0;
			
			for (int i = quotaData.getId()-n14+1; i <=quotaData.getId(); i++) {
				double d=hisList.get(i).getIncrease();
				if(d>=0){
					increase+=d;
				}else{
					decrease-=d;
				}
			}
			if(decrease!=0){
				RS=increase/decrease;
				quotaData.setRsi(100-100.0/(RS+1));
			}
		}
		if(quotaData.getId()>=n6-1)
		{
			double increase=0.0;
			double decrease=0.0;
			double RS = 100.0;
			
			for (int i = quotaData.getId()-n6+1; i <=quotaData.getId(); i++) {
				double d=hisList.get(i).getIncrease();
				if(d>=0){
					increase+=d;
				}else{
					decrease-=d;
				}
			}
			if(decrease!=0){
				RS=increase/decrease;
				quotaData.setRsi5(100-100.0/(RS+1));
			}
		}
		if(quotaData.getId()>=n12-1)
		{
			double increase=0.0;
			double decrease=0.0;
			double RS = 100.0;
			
			for (int i = quotaData.getId()-n12+1; i <=quotaData.getId(); i++) {
				double d=hisList.get(i).getIncrease();
				if(d>=0){
					increase+=d;
				}else{
					decrease-=d;
				}
			}
			if(decrease!=0){
				RS=increase/decrease;
				quotaData.setRsi10(100-100.0/(RS+1));
			}
		}
		if(quotaData.getId()>=n24-1)
		{
			double increase=0.0;
			double decrease=0.0;
			double RS = 100.0;
			
			for (int i = quotaData.getId()-n24+1; i <=quotaData.getId(); i++) {
				double d=hisList.get(i).getIncrease();
				if(d>=0){
					increase+=d;
				}else{
					decrease-=d;
				}
			}
			if(decrease!=0){
				RS=increase/decrease;
				quotaData.setRsi20(100-100.0/(RS+1));
			}
		}
	}

	/**设置成交量变异率 */
	public static void setVr(String siid,QuotaData quotaData) throws Exception
	{	int n20=20;
		
		if(quotaData.getId()>=n20-1)
		{
			double AVS=0.0;
			double BVS=0.0;
			double CVS = 0.0;

			for (int i = quotaData.getId()-n20+1; i <=quotaData.getId(); i++) {
				double d=hisList.get(i).getIncrease();
				if(d>0){
					AVS+=hisList.get(i).getVolume();
				}else if(d<0){
					BVS+=hisList.get(i).getVolume();
				}else{
					CVS+=hisList.get(i).getVolume();
				}
			}
			if((BVS+CVS/2)!=0){
			quotaData.setVr(100*(AVS+CVS/2)/(BVS+CVS/2));
			}
		}
	}

	/**设置未成熟随机指标 */
	public static void setRsv(String siid,QuotaData quotaData) throws Exception
	{
		int n9=9;
		
		if(quotaData.getId()>=n9-1)
		{
			List<HistoryData> list=hisList.subList(quotaData.getId()-n9+1,quotaData.getId()+1);
			double c=list.get(n9-1).getClose();
			double l=MMSTool.min_low(list);
			double h=MMSTool.max_high(list);
			if(h!=l)
			{
				quotaData.setRsv((c-l)/(h-l)*100);
			}
		}
	}
	
	/**设置随机指标 */
	public static void setKDJ(String siid,QuotaData quotaData) throws Exception
	{
		if(quotaData.getId()>0)
		{
			QuotaData lastQuotaData=null;
			if(newquoList.size()>=2)
				lastQuotaData=newquoList.get(quotaData.getId()-quoid-2);
			else
				lastQuotaData=quoList.get(quoid);
		
			quotaData.setK(2.0/3.0*lastQuotaData.getK()+1.0/3.0*quotaData.getRsv());
			quotaData.setD(2.0/3.0*lastQuotaData.getD()+1.0/3.0*quotaData.getK());
			quotaData.setJ(3*quotaData.getK()-2*quotaData.getD());
		}
	}
	
	/**设置指数平均数 */
	public static void setEma(String siid,QuotaData quotaData) throws Exception
	{
		if(quotaData.getId()>0)
		{
			HistoryData nowhistoryData=hisList.get(quotaData.getId());
			QuotaData lastQuotaData=null;
			if(newquoList.size()>=2)
				lastQuotaData=newquoList.get(quotaData.getId()-quoid-2);
			else
				lastQuotaData=quoList.get(quoid);
			
			quotaData.setEma5(2.0/(5+1)*nowhistoryData.getClose()+(1-2.0/(5+1))*lastQuotaData.getEma5());
			quotaData.setEma35(2.0/(35+1)*nowhistoryData.getClose()+(1-2.0/(35+1))*lastQuotaData.getEma35());
			quotaData.setEma12(2.0/(12+1)*nowhistoryData.getClose()+(1-2.0/(12+1))*lastQuotaData.getEma12());
			quotaData.setEma26(2.0/(26+1)*nowhistoryData.getClose()+(1-2.0/(26+1))*lastQuotaData.getEma26());
			quotaData.setEma50(2.0/(26+1)*nowhistoryData.getClose()+(1-2.0/(50+1))*lastQuotaData.getEma50());
			quotaData.setEma10(2.0/(26+1)*nowhistoryData.getClose()+(1-2.0/(10+1))*lastQuotaData.getEma10());
			quotaData.setEma60(2.0/(26+1)*nowhistoryData.getClose()+(1-2.0/(60+1))*lastQuotaData.getEma60());
		}
	}
	
	/**设置正负差*/
	public static void setDiff(String dea,QuotaData quotaData)
	{
		quotaData.setDiff(quotaData.getEma12()-quotaData.getEma26());
	}	
	
	/**设置异同平均数 */
	public static void setDea(String siid,QuotaData quotaData) throws Exception
	{
		int n9=9;
		if(quotaData.getId()>0)
		{
			QuotaData lastQuotaData=null;
			if(newquoList.size()>=2)
				lastQuotaData=newquoList.get(quotaData.getId()-quoid-2);
			else
				lastQuotaData=quoList.get(quoid);
			
			quotaData.setDea((n9-1.0)/(n9+1.0)*lastQuotaData.getDea()+2.0/(n9+1.0)*quotaData.getDiff());
		}
	}
	
	/**设置指数平滑异同平均线*/
	public static void setMacd(String siid,QuotaData quotaData)
	{
		quotaData.setMacd(quotaData.getDiff()-quotaData.getDea());
	}
	
	/**设置动向指标 */
	public static void setDmi(String siid,QuotaData quotaData) throws Exception
	{
		if(quotaData.getId()>=1)
		{
			QuotaData lastQuotaData=null;
			if(newquoList.size()>=2)
				lastQuotaData=newquoList.get(quotaData.getId()-quoid-2);
			else
				lastQuotaData=quoList.get(quoid);
			
			HistoryData nowhistoryData=hisList.get(quotaData.getId());
			HistoryData lasthistoryData=hisList.get(quotaData.getId()-1);
		
			double dmh=nowhistoryData.getHigh()-lasthistoryData.getHigh();
			double dml=lasthistoryData.getLow()-nowhistoryData.getLow();
			if(dml<0)
				dml=0;
			if(dmh<0)
				dmh=0;

			if(dml<dmh)
				dml=0;
			else if(dml>dmh)
				dmh=0;
			else
			{
				dmh=0;
				dml=0;
			}
			
			quotaData.setDmh(dmh);
			quotaData.setDml(dml);
			
			double a=Math.abs(nowhistoryData.getHigh()-nowhistoryData.getLow());
			double b=Math.abs(nowhistoryData.getHigh()-lasthistoryData.getClose());
			double c=Math.abs(nowhistoryData.getLow()-lasthistoryData.getClose());
			double tr=a;
			if(tr<b)
				tr=b;
			if(tr<c)
				tr=c;
			quotaData.setTr(tr);
			
			double dih=0;
			double dil=0;
			double dx=0;
			if(tr!=0)
			{
				dih=dmh/tr*100;
				dil=dml/tr*100;
				
				quotaData.setDih(dih);
				quotaData.setDil(dil);
				if(dih+dil!=0)
				{
					dx=Math.abs(dih-dil)/(dih+dil)*100;
					quotaData.setDx(dx);
				}
			
			}
			
			
			int n12=12;
			if(quotaData.getId()<n12-1)
			{
				quotaData.setDmh12(dmh);
				quotaData.setDml12(dml);
				quotaData.setTr12(tr);
				if(dih+dil!=0)
				{
					quotaData.setAdx(Math.abs(dih-dil)/(dih+dil)*100);
				}
			}
			else if(quotaData.getId()==n12-1)
			{
				List<QuotaData> list=null;
				if(newquoList.size()>=n12)
				{
					list=newquoList;
				}
				else
				{
					list.addAll(quoList.subList(quoList.size()-(n12-newquoList.size()),quoList.size()));
					list.addAll(newquoList);
				}
				double sumdmh=0;
				double sumdml=0;
				double sumtr=0;
				double sumdx=0;
				for(int i=0;i<n12;i++)
				{
					sumdmh+=list.get(i).getDmh();
					sumdml+=list.get(i).getDml();
					sumtr+=list.get(i).getTr();
					sumdx+=list.get(i).getDx();
				}
				quotaData.setDmh12(sumdmh/n12);
				quotaData.setDml12(sumdml/n12);
				quotaData.setTr12(sumtr/n12);
				quotaData.setAdx(sumdx/n12);
			}
			else
			{
				quotaData.setDmh12(((n12-1)*lastQuotaData.getDmh12()+quotaData.getDmh())/n12);
				quotaData.setDml12(((n12-1)*lastQuotaData.getDml12()+quotaData.getDml())/n12);
				quotaData.setTr12(((n12-1)*lastQuotaData.getTr12()+quotaData.getTr())/n12);
				quotaData.setAdx(((n12-1)*lastQuotaData.getAdx()+quotaData.getDx())/n12);
			}
			if(quotaData.getTr12()!=0)
			{
				quotaData.setDih12(quotaData.getDmh12()/quotaData.getTr12()*100);
				quotaData.setDil12(quotaData.getDml12()/quotaData.getTr12()*100);
			}
			quotaData.setAdxr((quotaData.getAdx()+lastQuotaData.getAdx())/2);
		}		
	}
	
	/**设置能量潮 *///以第一天为基期
	public static void setObv(String siid,QuotaData quotaData) throws Exception
	{
		if(quotaData.getId()>0)
		{
			HistoryData nowhistoryData=hisList.get(quotaData.getId());
			QuotaData lastQuotaData=null;
			if(newquoList.size()>=2)
				lastQuotaData=newquoList.get(quotaData.getId()-quoid-2);
			else
				lastQuotaData=quoList.get(quoid);
			
			if(nowhistoryData.getIncrease()<0)
			{
				quotaData.setObv(lastQuotaData.getObv()-nowhistoryData.getVolume());
			}
			else
			{
				quotaData.setObv(lastQuotaData.getObv()+nowhistoryData.getVolume());
			}
		}
	}

	/**设置变动速率指标*/
	public static void setRoc(String siid,QuotaData quotaData) throws Exception
	{
		int n12=12;
		int n25=25;
		
		HistoryData nowhistoryData=hisList.get(quotaData.getId());
		if(quotaData.getId()>=n12-1)
		{
			HistoryData historyData12=hisList.get(quotaData.getId()-n12+1);
			if(historyData12.getClose()!=0)
			{
				quotaData.setRoc12((nowhistoryData.getClose()-historyData12.getClose())/historyData12.getClose()*100);
			}
		}
		if(quotaData.getId()>=n25-1)
		{
			HistoryData historyData25=hisList.get(quotaData.getId()-n25+1);
			if(historyData25.getClose()!=0)
			{
				quotaData.setRoc25((nowhistoryData.getClose()-historyData25.getClose())/historyData25.getClose()*100);
			}
		}
	}
}
