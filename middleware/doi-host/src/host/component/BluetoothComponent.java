package host.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

public class BluetoothComponent {
	
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private BluetoothDevice btDevice = null;
	//private final static int REQUEST_ENABLE_BT = 1;
	private static final UUID MY_UUID =
		      UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static String btAddress = null;
	private OutputStream outStream = null;
	private InputStream inStream = null;
	
	public void initBluetooth(Context c) //ªì©l¤ÆÂÅªÞ
	{
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		btState(c);
		if(btAdapter!=null)
		{
			btAdapter.startDiscovery();
		}
	}
	
	public void btState(Context c)
	{
		if(btAdapter==null) 
		{ 
			Toast.makeText(c, "Bluetooth Not supported. Aborting.", Toast.LENGTH_LONG).show();
		}
		else
		{
			if(!btAdapter.isEnabled())
			{
				//Intent enableIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				//onActivityResult(enableIntent,REQUEST_ENABLE_BT);
				Toast.makeText( c , "no bluetooth", Toast.LENGTH_LONG).show();
			}
		}		
	}

	public void btConnect(Context c,String address)
	{
		btAddress=address;
		btDevice = btAdapter.getRemoteDevice(btAddress);	
	    try {
	    	btSocket = btDevice.createRfcommSocketToServiceRecord(MY_UUID);
	    } catch (IOException e) {
	    	Toast.makeText( c , btAddress+"In onResume() and socket create failed", Toast.LENGTH_LONG).show();
	    }
	   
	    btAdapter.cancelDiscovery();
	  
	    try {
	    	btSocket.connect();
	    } catch (IOException e) {
	    	try {
	            btSocket.close();
	        } catch (IOException e2) {
	        	Toast.makeText( c , "In onResume() and unable to close socket during connection failure", Toast.LENGTH_LONG).show();
	        }
	    }
	    
	    try {
	    	inStream = btSocket.getInputStream();
	    	outStream = btSocket.getOutputStream();
	    } catch (IOException e) {
	    	Toast.makeText( c , "In onResume() output failed", Toast.LENGTH_LONG).show();
	    }
		
	}
	
	public void btPause(Context c) 
	{
		if (outStream != null) 
		{
			try {
				outStream.flush();
			} catch (IOException e) {
				Toast.makeText( c , "In onPause() failed to flush output", Toast.LENGTH_LONG).show();
			}
		} 
		try{
			btSocket.close();
		} catch (IOException e2) {
			Toast.makeText( c , "In onPause() failed to close socket", Toast.LENGTH_LONG).show();
		}
	}
	
	public void sendData(Context c , String controlMsg)
	{
		byte[] msgBuffer=controlMsg.getBytes();			
		try 
		{
			outStream.write(msgBuffer);
			Toast.makeText( c , controlMsg+"   "+btAddress, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText( c , "write failed", Toast.LENGTH_LONG).show();
		}
	}
	
	public String readData(Context c)
	{
		String data = "";
		try {
			while(inStream.available()>0)
			{
				data += inStream.read();
				data += ",";
			}
			if(data.length()==0)
			{
				data="read failed";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}

	public Set<BluetoothDevice> btDeviceList()
	{
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		return pairedDevices;
	}
}
