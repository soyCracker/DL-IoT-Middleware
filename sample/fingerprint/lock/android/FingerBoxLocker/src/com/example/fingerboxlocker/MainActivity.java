package com.example.fingerboxlocker;

import com.ryg.dynamicload.DLBasePluginActivity;
import com.ryg.dynamicload.sample.docommon.HostInterface;
import com.ryg.dynamicload.sample.docommon.HostInterfaceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends DLBasePluginActivity {

	HostInterface hostInterface = HostInterfaceManager.getHostInterface();
	public Button unlockBtn;
	public TextView stateTV;
	
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
		hostInterface.hostBtConnect(that,"00:12:06:18:93:97");
		hostInterface.hostFingerprintInit(that);
	}
	
	@Override
	public void onPause()
	{
		hostInterface.hostBtPause(that);
		hostInterface.hostFingerprintStop();
	}
	
	@Override
	public void onBackPressed() 
	{
		hostInterface.hostBtPause(that);
		hostInterface.hostFingerprintStop();
		return;
	}
	
	public void initView()
	{
		unlockBtn=(Button)findViewById(R.id.unlockBtn);	
		stateTV=(TextView)findViewById(R.id.stateTV);
		unlockBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				unLock();
				stateTV.setText("Fingerprint Listening");
			}		
		});
	}
	
	public void unLock()
	{
		char resultC = hostInterface.hostFingerprintStart(that);
		if(resultC=='S')
		{
			stateTV.setText("Succeeded");
			hostInterface.hostBtSendData(that, "1");		
		}
		else if(resultC=='F')
		{
			stateTV.setText("Failed");
		}
		else if(resultC=='E')
		{
			stateTV.setText("Error");
		}
		hostInterface.hostFingerprintStop();
	}
}
