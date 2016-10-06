package com.httpurlcon.httptest;

import com.ryg.dynamicload.DLBasePluginActivity;
import com.ryg.dynamicload.sample.docommon.HostInterface;
import com.ryg.dynamicload.sample.docommon.HostInterfaceManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends DLBasePluginActivity {

	public Button button;
	public TextView textView;
	HostInterface hostInterface = HostInterfaceManager.getHostInterface();
	HandlerThread httpHT=new HandlerThread("httpurlcon");
    Handler httpH;
	String url="http://iotphptest.azurewebsites.net/a.php";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
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
		httpHT.start();
		httpH=new Handler(httpHT.getLooper());
		
		button=(Button)findViewById(R.id.button);
		textView=(TextView)findViewById(R.id.textView);	
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				httpH.post(httpRun);
			}		
		});
	}
	
	private Runnable httpRun=new Runnable()
	{
		public void run()
		{
			try{
				String response = hostInterface.hostHttpUrlGet("1", url);
				textView.setText(response);
			}catch(Exception e){
                e.printStackTrace();
            }
		}
	};
}
