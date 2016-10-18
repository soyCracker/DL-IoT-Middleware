package com.arduino.ledcontrol;

import com.ryg.dynamicload.DLBasePluginActivity;
import com.ryg.dynamicload.sample.docommon.HostInterface;
import com.ryg.dynamicload.sample.docommon.HostInterfaceManager;
import java.util.Set;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends DLBasePluginActivity {
	
	public Spinner btSpinner;
	public Button ONbutton,OFFbutton;
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
	
	public void initView()
	{
		btSpinner=(Spinner)findViewById(R.id.btSpinner);
		ONbutton=(Button)findViewById(R.id.ONbutton);
		OFFbutton=(Button)findViewById(R.id.OFFbutton);
			
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
				hostInterface.hostBtConnect(that,adapterview.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub			
			}		
		});
		
		ONbutton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				try{
					hostInterface.hostBtSendData(that , "1");
				} catch(Exception e){
					Toast.makeText( MainActivity.this , "turn on Failed , maybe non-bluetooth", Toast.LENGTH_LONG).show();
				}			
			}		
		});
		
		OFFbutton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				try{
					hostInterface.hostBtSendData(that , "0");
				} catch(Exception e){
					Toast.makeText( MainActivity.this , "turn off Failed , maybe non-bluetooth", Toast.LENGTH_LONG).show();
				}
			}		
		});
	}
	
	@Override
	public void onBackPressed() 
	{
		hostInterface.hostBtPause(that);
		return;
	}
}

