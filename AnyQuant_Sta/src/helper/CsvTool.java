package helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvTool {
	private InputStreamReader fr = null;  
    private BufferedReader br = null;  
  
    public CsvTool(String f) throws IOException {  
        fr = new InputStreamReader(new FileInputStream(f));  
    }  
  
    /** 
     * 解析csv文件 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中 
     */  
	public List<List<String>> readCSVFile() throws IOException {  
        br = new BufferedReader(fr);  
        String rec = null;// 一行  
//        String str;// 一个单元格  
        List<List<String>> listFile = new ArrayList<List<String>>();  
        try {  
            // 读取一行  
            while ((rec = br.readLine()) != null) {  
                List<String> cells = new ArrayList<String>();// 每行记录一个list  
                // 读取每个单元格 
                String[] line=rec.split(",");
                for(int i=0;i<line.length;i++)
                {
                	cells.add(line[i].toString());
                }
                listFile.add(cells);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fr != null) {  
                fr.close();  
            }  
            if (br != null) {  
                br.close();  
            }  
        }  
        return listFile;  
    }
}
