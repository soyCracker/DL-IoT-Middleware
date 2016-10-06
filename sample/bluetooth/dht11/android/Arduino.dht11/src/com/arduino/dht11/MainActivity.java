package com.arduino.dht11;

import java.util.Set;

import com.ryg.dynamicload.DLBasePluginActivity;
import com.ryg.dynamicload.sample.docommon.HostInterface;
import com.ryg.dynamicload.sample.docommon.HostInterfaceManager;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends DLBasePluginActivity {

	public Spinner btSpinner;
	public Button showButton;
	public TextView showTextView;
	HostInterface hostInterface = HostInterfaceManager.getHostInterface();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		hostInterface.hostBtInit(that);
		initView();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onResume()
    {
    	super.onResume();
    	hostInterface.hostBtConnect(that);
    }
	
	public void onPause()
	{
		super.onPause();
		hostInterface.hostBtPause(that);
	}
	
	public void initView()
	{
		btSpinner = (Spinner)findViewById(R.id.btSpinner);
		showButton = (Button)findViewById(R.id.showButton);
		showTextView = (TextView)findViewById(R.id.showTextView);
		
		Set<BluetoothDevice> pairedDevices = hostInterface.hostBtDeviceList();
		if(pairedDevices.size()>0)
		{
			ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			btSpinner.setAdapter(adapter);
			for(BluetoothDevice device : pairedDevices)
			{
				adapter.add(device.getAddress());
			}
		}
		btSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> adapterview, View view, int position, long id) {
				// TODO Auto-generated method stub
				hostInterface.hostBtSetAddress(adapterview.getSelectedItem().toString());
				hostInterface.hostBtConnect(that);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub			
			}		
		});
		
		showButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				try{
					hostInterface.hostBtSendData(that , "1");
				} catch(Exception e){
					Toast.makeText( MainActivity.this , "send data Failed", Toast.LENGTH_LONG).show();
				}	
				
				uiThread();
			}		
		});
	}
	
	private void uiThread()
	{
		runOnUiThread(new Thread(new Runnable(){
			public void run(){
				String str;
				try{		
					str = hostInterface.hostBtReadData(that);
					showTextView.setText(str+" *C");
				} catch(Exception e){
					Toast.makeText( MainActivity.this , "read data Failed", Toast.LENGTH_LONG).show();
				}	
			}
		}));
	}
	
}
