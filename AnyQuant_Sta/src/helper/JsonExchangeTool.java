package helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import type.HistoryData;
import type.HistoryDataString;
import type.StockInf;
import type.StockInfString;

public class JsonExchangeTool {
	private static final String key="5139c920b79a3560e5e2633e4366e8b3";
	
//	/**从API获取实时数据转化为RealTimeData*/
//	public static RealTimeData getRealTimeData(String gid)
//	{
//		String prefix="http://web.juhe.cn:8080/finance/stock/hs?";
//		
//		RealTimeDataString realTimeDataString=null;
//		RealTimeData realTimeData=null;
//		
//		JSONObject jsonObject_response=null;
//		JSONArray jsonObject_result=null;
//		JSONObject jsonObject_data=null;
//		
//		URL url=null;
//		InputStreamReader inputStreamReader=null;
//		HttpURLConnection httpUrlConnection=null;
//		Scanner scanner=null;
//		StringBuffer stringBuffer=null;
//		try {
//			url=new URL(prefix+"gid="+gid+"&type=&"+"key="+key);
////				System.out.println(prefix+"gid="+gid+"&type=&"+"key="+key);
//			httpUrlConnection=(HttpURLConnection) url.openConnection();
//			inputStreamReader=new InputStreamReader(httpUrlConnection.getInputStream(),"utf-8");
//			scanner=new Scanner(inputStreamReader);
//			stringBuffer=new StringBuffer();
//			while(scanner.hasNext())
//			{
//				stringBuffer.append(scanner.nextLine());
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e){
//			e.printStackTrace();
//		}
//		jsonObject_response=JSONObject.fromObject(stringBuffer.toString());
////			System.out.println(jsonObject_response.get("result"));
//		jsonObject_result=jsonObject_response.getJSONArray("result");
////			System.out.println(jsonObject_result.get(0));
////			System.out.println(jsonObject_data.getJSONObject("data"));
//		jsonObject_data=jsonObject_result.getJSONObject(0).getJSONObject("data");
////			System.out.println(jsonObject_data);
//		realTimeDataString=(RealTimeDataString) JSONObject.toBean(jsonObject_data, RealTimeDataString.class);
////			System.out.println(realTimeStockDataString);
//		realTimeData=new RealTimeData(realTimeDataString);
////			System.out.println(realTimeStockData);
//		return realTimeData;
//	}
	
	/**从API获取股票列表转化为StockInf*/
	@SuppressWarnings("resource")
	public static List<StockInf> getStockInf()
	{
		String prefix="http://web.juhe.cn:8080/finance/stock/";
		
		List<StockInf> list=new ArrayList<StockInf>();
		
		StockInfString infString=null;
		
		JSONObject jsonObject_response=null;
		JSONObject jsonObject_result=null;
		JSONArray jsonObject_data=null;
		
		URL url=null;
		InputStreamReader inputStreamReader=null;
		HttpURLConnection httpUrlConnection=null;
		Scanner scanner=null;
		StringBuffer stringBuffer=null;
		String grailId="sh";
		int count=2;
		while(count>0)
		{
			for(int i=1;i<100;i++)
			{
				try {
					url=new URL(prefix+grailId+"all?key="+key+"&page="+i);
					httpUrlConnection=(HttpURLConnection) url.openConnection();
					inputStreamReader=new InputStreamReader(httpUrlConnection.getInputStream(),"utf-8");
					scanner=new Scanner(inputStreamReader);
					stringBuffer=new StringBuffer();
					while(scanner.hasNext())
					{
						stringBuffer.append(scanner.nextLine());
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e){
					e.printStackTrace();
				}
				
				System.out.println(stringBuffer.toString());
				
				jsonObject_response=JSONObject.fromObject(stringBuffer.toString());
				if((int)jsonObject_response.get("error_code")==0)
				{
					jsonObject_result=jsonObject_response.getJSONObject("result");
					jsonObject_data=jsonObject_result.getJSONArray("data");
					for(int j=0;j<jsonObject_data.size();j++)
					{
						infString=(StockInfString) JSONObject.toBean((JSONObject)jsonObject_data.get(j), StockInfString.class);
						System.out.println(infString);
						list.add(new StockInf(list.size(),infString.getSymbol(),infString.getName(),null,-1,-1,-1,-1,-1,-1,-1,-1));
					}
				}
				else
				{
					break;
				}
			}
			count--;
			grailId="sz";
		}
		return list;
	}

	/**从API获取股票历史数据转化为HistoryData*/
	@SuppressWarnings({ "deprecation", "resource" })
	public static List<HistoryData> getStockHistoryData(String gid,Date date1,Date date2)
	{
		if(date1==null)
		{
			date1=new Date(0,0,1);
		}
		else
		{
			date1=new Date(date1.getTime()+24*60*60*1000);
		}
		
		List<HistoryData> list=new ArrayList<HistoryData>();
		String prefix="http://q.stock.sohu.com/hisHq?code=cn_";
		
		JSONObject jsonObject_response=null;
		JSONObject jsonObject_data=null;
		JSONArray jsonArray_hq=null;
		JSONArray jsonArray_sta=null;
		
		URL url=null;
		InputStreamReader inputStreamReader=null;
		HttpURLConnection httpUrlConnection=null;
		Scanner scanner=null;
		StringBuffer stringBuffer=null;
//			System.out.println(date1);
//			System.out.println(date2);
		try {
			url=new URL(prefix+gid.substring(2,8)+"&start="+DateExchangeTool.dateToString2(date1)+"&end="+DateExchangeTool.dateToString2(date2));
//				System.out.println(prefix+gid.substring(2,8)+"&start="+DateExchangeTool.dateToString2(date1)+"&end="+DateExchangeTool.dateToString2(date2));
			httpUrlConnection=(HttpURLConnection) url.openConnection();
			inputStreamReader=new InputStreamReader(httpUrlConnection.getInputStream(),"utf-8");
			scanner=new Scanner(inputStreamReader);
			stringBuffer=new StringBuffer();
			while(scanner.hasNext())
			{
				stringBuffer.append(scanner.nextLine());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		jsonObject_response=JSONObject.fromObject("{data:"+stringBuffer.toString()+"}");
//			System.out.println(jsonObject_response.get("data"));
			if(jsonObject_response.get("data").toString().equals("{}"))
			{
				return null;
			}
		jsonObject_data=(JSONObject) ((JSONArray)jsonObject_response.get("data")).get(0);
//			System.out.println(jsonObject_data);
		jsonArray_hq=(JSONArray) jsonObject_data.get("hq");
		for(int i=0;i<jsonArray_hq.size();i++)
		{
//			System.out.println(jsonArray_hq.get(i));
			jsonArray_sta=(JSONArray)jsonArray_hq.get(i);
			HistoryDataString historyStockDataString=new HistoryDataString((String)jsonArray_sta.get(0),(String)jsonArray_sta.get(1),(String)jsonArray_sta.get(2),(String)jsonArray_sta.get(3),(String)jsonArray_sta.get(4),(String)jsonArray_sta.get(5),(String)jsonArray_sta.get(6),(String)jsonArray_sta.get(7),(String)jsonArray_sta.get(8),(String)jsonArray_sta.get(9));
//			System.out.println(historyStockDataString);
			HistoryData historyStockData=new HistoryData(historyStockDataString);
			list.add(historyStockData);
		}
		return list;
	}

	/**从API获取指数历史数据转化为HistoryData*/
	@SuppressWarnings({ "deprecation", "resource" })
	public static List<HistoryData> getIndiceHistoryData(String gid,Date date1,Date date2)
	{
		if(date1==null)
		{
			date1=new Date(100,0,1);
		}
		else
		{
			date1=new Date(date1.getTime()+24*60*60*1000);
		}
		
		List<HistoryData> list=new ArrayList<HistoryData>();
		String prefix="http://q.stock.sohu.com/hisHq?code=zs_";
		
		JSONObject jsonObject_response=null;
		JSONObject jsonObject_data=null;
		JSONArray jsonArray_hq=null;
		JSONArray jsonArray_sta=null;
		
		URL url=null;
		InputStreamReader inputStreamReader=null;
		HttpURLConnection httpUrlConnection=null;
		Scanner scanner=null;
		StringBuffer stringBuffer=null;
//			System.out.println(date1);
//			System.out.println(date2);
		try {
			url=new URL(prefix+gid.substring(2,8)+"&start="+DateExchangeTool.dateToString2(date1)+"&end="+DateExchangeTool.dateToString2(date2));
//				System.out.println(prefix+gid.substring(2,8)+"&start="+DateExchangeTool.dateToString(date1)+"&end="+DateExchangeTool.dateToString(date2));
			httpUrlConnection=(HttpURLConnection) url.openConnection();
			inputStreamReader=new InputStreamReader(httpUrlConnection.getInputStream(),"utf-8");
			scanner=new Scanner(inputStreamReader);
			stringBuffer=new StringBuffer();
			while(scanner.hasNext())
			{
				stringBuffer.append(scanner.nextLine());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		jsonObject_response=JSONObject.fromObject("{data:"+stringBuffer.toString()+"}");
//			System.out.println(jsonObject_response);
		if(jsonObject_response.get("data").toString().equals("{}"))
		{
			return null;
		}
		jsonObject_data=(JSONObject) ((JSONArray)jsonObject_response.get("data")).get(0);
//			System.out.println(jsonObject_data);
		jsonArray_hq=(JSONArray) jsonObject_data.get("hq");
		for(int i=0;i<jsonArray_hq.size();i++)
		{
//			System.out.println(jsonArray_hq.get(i));
			jsonArray_sta=(JSONArray)jsonArray_hq.get(i);
			HistoryDataString historyStockDataString=new HistoryDataString((String)jsonArray_sta.get(0),(String)jsonArray_sta.get(1),(String)jsonArray_sta.get(2),(String)jsonArray_sta.get(3),(String)jsonArray_sta.get(4),(String)jsonArray_sta.get(5),(String)jsonArray_sta.get(6),(String)jsonArray_sta.get(7),(String)jsonArray_sta.get(8),(String)jsonArray_sta.get(9));
//			System.out.println(historyStockDataString);
			HistoryData historyStockData=new HistoryData(historyStockDataString);
			list.add(historyStockData);
		}
		return list;
	}

	/**从python获得行业数据转化为IndustryInf*/
//	public static List<IndustryInf> getIndustryInf()
//	{	
//		List<IndustryInf> list=new ArrayList<IndustryInf>();
//		File industry=new File("src/main/resources/python/industry.json");
//		FileReader fr=null;
//		BufferedReader in=null;
//		JSONObject jsonObject_response=null;
//		JSONArray jsonArray_data=null;
//		JSONObject jsonObject_industry=null;
//		try {
//			fr=new FileReader(industry);
//			in= new BufferedReader(fr);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			jsonObject_response=JSONObject.fromObject("{data:"+in.readLine()+"}");
////			System.out.println(jsonObject_response);
//			jsonArray_data=jsonObject_response.getJSONArray("data");
//			for(int i=0;i<jsonArray_data.size();i++)
//			{
//				jsonObject_industry=(JSONObject) jsonArray_data.get(i);
//				String sid=(String)jsonObject_industry.get("code");
//				if(sid.startsWith("6")||sid.startsWith("9"))
//				{
//					sid="sh"+sid;
//				}
//				else if(sid.startsWith("0")||sid.startsWith("3")||sid.startsWith("2"))
//				{
//					sid="sz"+sid;
//				}
//				IndustryInf industryInf=new IndustryInf(sid,
//						(String)jsonObject_industry.get("name"),
//						(String)jsonObject_industry.get("c_name"));
//				list.add(industryInf);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	/**从python获得概念数据转化为ConceptInf*/
//	public static List<ConceptInf> getConceptInf()
//	{	
//		List<ConceptInf> list=new ArrayList<ConceptInf>();
//		File concept=new File("src/main/resources/python/concept.json");
//		FileReader fr=null;
//		BufferedReader in=null;
//		JSONObject jsonObject_response=null;
//		JSONArray jsonArray_data=null;
//		JSONObject jsonObject_concept=null;
//		try {
//			fr=new FileReader(concept);
//			in= new BufferedReader(fr);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			jsonObject_response=JSONObject.fromObject("{data:"+in.readLine()+"}");
////			System.out.println(jsonObject_response);
//			jsonArray_data=jsonObject_response.getJSONArray("data");
//			for(int i=0;i<jsonArray_data.size();i++)
//			{
//				jsonObject_concept=(JSONObject) jsonArray_data.get(i);
//				String sid=(String)jsonObject_concept.get("code");
//				if(sid.startsWith("6")||sid.startsWith("9"))
//				{
//					sid="sh"+sid;
//				}
//				else if(sid.startsWith("0")||sid.startsWith("3")||sid.startsWith("2"))
//				{
//					sid="sz"+sid;
//				}
//				ConceptInf conceptInf=new ConceptInf(sid,
//						(String)jsonObject_concept.get("name"),
//						(String)jsonObject_concept.get("c_name"));
//				list.add(conceptInf);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	/**从python获得地域数据转化为AreaInf*/
//	public static List<AreaInf> getAreaInf()
//	{	
//		List<AreaInf> list=new ArrayList<AreaInf>();
//		File area=new File("src/main/resources/python/area.json");
//		FileReader fr=null;
//		BufferedReader in=null;
//		JSONObject jsonObject_response=null;
//		JSONArray jsonArray_data=null;
//		JSONObject jsonObject_area=null;
//		try {
//			fr=new FileReader(area);
//			in= new BufferedReader(fr);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			jsonObject_response=JSONObject.fromObject("{data:"+in.readLine()+"}");
////			System.out.println(jsonObject_response);
//			jsonArray_data=jsonObject_response.getJSONArray("data");
//			for(int i=0;i<jsonArray_data.size()-1;i++)
//			{
//				jsonObject_area=(JSONObject) jsonArray_data.get(i);
//				String sid=(String)jsonObject_area.get("code");
//				if(sid.startsWith("6")||sid.startsWith("9"))
//				{
//					sid="sh"+sid;
//				}
//				else if(sid.startsWith("0")||sid.startsWith("3")||sid.startsWith("2"))
//				{
//					sid="sz"+sid;
//				}
//				AreaInf areaInf=new AreaInf(sid,
//						(String)jsonObject_area.get("name"),
//						(String)jsonObject_area.get("area"));
//				list.add(areaInf);	
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
}
