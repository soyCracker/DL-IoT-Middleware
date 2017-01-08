package com.iot.boxlocker;

import com.ryg.dynamicload.DLBasePluginActivity;
import com.ryg.dynamicload.sample.docommon.HostInterface;
import com.ryg.dynamicload.sample.docommon.HostInterfaceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends DLBasePluginActivity {

	public Button unlockBtn;
	public EditText passEditText;
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
		hostInterface.hostBtConnect(that,"00:12:06:18:93:97");
	}
	
	@Override
	public void onPause()
	{
		hostInterface.hostBtPause(that);
	}
	
	@Override
	public void onBackPressed() 
	{
		hostInterface.hostBtPause(that);
		return;
	}
	
	public void initView()
	{
		unlockBtn=(Button)findViewById(R.id.unlockBtn);		
		passEditText=(EditText)findViewById(R.id.passEditText);
		unlockBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				try{
					if(passEditText.getText().toString()=="1234")
					{
						hostInterface.hostBtSendData(that , "1");
						unlockBtn.setText("Unlock");
					}
					else
					{
						unlockBtn.setText("Error");
					}
				} catch(Exception e){
					Toast.makeText( MainActivity.this , "unlock Failed , maybe non-bluetooth", Toast.LENGTH_LONG).show();
				}			
			}		
		});
	}
}
