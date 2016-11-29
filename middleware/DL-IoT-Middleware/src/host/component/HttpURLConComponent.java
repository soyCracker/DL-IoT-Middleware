package host.component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConComponent {

	public String connectWithPOST(String strData,String address) 
	{ 
		HttpURLConnection connection = null;  
		String strResponse = null;
	    try {
	    	// 調用URL物件的openConnection方法獲取HttpURLConnection的實例
	    	URL mURL = new URL(address);
	    	connection = (HttpURLConnection) mURL.openConnection();
	    	// 設置請求方式，GET或POST
	    	connection.setRequestMethod("POST");
	    	// 設置連接逾時、讀取超時的時間，單位為毫秒（ms）
	    	connection.setConnectTimeout(8000);
	    	connection.setReadTimeout(8000);
	    	// getInputStream方法獲取伺服器返回的輸入流
	    	
	    	OutputStream out = connection.getOutputStream();// 獲得一個輸出流,向伺服器寫資料
	    	out.write(strData.getBytes());
	    	out.flush();
	    	out.close();
	    	
	    	InputStream in = connection.getInputStream();
	    	// 使用BufferedReader物件讀取返回的資料流程
	    	// 按行讀取，存儲在StringBuider物件response中
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    	StringBuilder response = new StringBuilder();
	    	String line;
	    	
	    	//取得網路回傳之結果並處理
	    	while ((line = reader.readLine()) != null) 
	    	{
	    		response.append(line);
	    	}     	    	
	    	strResponse=response.toString();        
	    	
	    } catch (Exception e){
	    	e.printStackTrace();
	    } finally {
	    	if (connection != null)
	    	{
	    		// 結束後，關閉連接
	            connection.disconnect();
	        }
	    }      
	    return strResponse;
	}
	
	public String connectWithGET(String strData,String address)
	{ 
	    HttpURLConnection connection = null;
	    String strResponse = null;
	    try {
	    	// 調用URL物件的openConnection方法獲取HttpURLConnection的實例
	    	//String url = "http://iotphptest.azurewebsites.net/a.php";
	    	URL mURL = new URL(address+"?"+strData);
	    	connection = (HttpURLConnection) mURL.openConnection();
	    	// 設置請求方式，GET或POST
	    	connection.setRequestMethod("GET");
	    	// 設置連接逾時、讀取超時的時間，單位為毫秒（ms）
	    	connection.setConnectTimeout(8000);
	    	connection.setReadTimeout(8000);
	    	// getInputStream方法獲取伺服器返回的輸入流	    	
	    	InputStream in = connection.getInputStream();
	    	// 使用BufferedReader物件讀取返回的資料流程
	    	// 按行讀取，存儲在StringBuider物件response中
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    	StringBuilder response = new StringBuilder();
	    	String line;
	    	
	    	//取得網路回傳之結果並處理
	    	while ((line = reader.readLine()) != null) 
	    	{
	    		response.append(line);
	    	}   	
	    	strResponse=response.toString();
	    	
	    } catch (Exception e){
	    	e.printStackTrace();
	    } finally {
	    	if (connection != null)
	    	{
	    		// 結束後，關閉連接
	    		connection.disconnect();
	    	}
	    }          
	    return strResponse;
	}
}
