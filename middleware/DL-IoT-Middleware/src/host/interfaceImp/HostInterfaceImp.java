package host.interfaceImp;

import java.util.Set;
import com.ryg.dynamicload.sample.docommon.HostInterface;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import host.component.BluetoothComponent;
import host.component.FingerprintComponent;
import host.component.HttpURLConComponent;

public class HostInterfaceImp implements HostInterface {
	
//////////////////Bluetooth Component/////////////////////	
	BluetoothComponent btComponent = new BluetoothComponent();
    
    public void hostBtInit(Context c) {
    	btComponent.initBluetooth(c);
    }
       
    public void hostBtConnect(Context c,String btAddress){
    	btComponent.btConnect(c,btAddress);
    } 
    
    public void hostBtPause(Context c){
    	btComponent.btPause(c);
    }
    
    public void hostBtSendData(Context c , String strMsg){
    	btComponent.sendData(c , strMsg);
    }
    
    public String hostBtReadData(Context c){
    	return btComponent.readData(c);
    }
    
    public Set<BluetoothDevice> hostBtDeviceList(){
    	return btComponent.btDeviceList();
    }
//////////////////Bluetooth Component/////////////////////	
    
//////////////////HttpURLConnect Component/////////////////////	
    HttpURLConComponent httpURLcComponent = new HttpURLConComponent();
    
    public String hostHttpUrlGet(String strData,String address)
    {
    	return httpURLcComponent.connectWithGET(strData, address);	
    }

    public String hostHttpUrlPost(String strData,String address)
    {
    	return httpURLcComponent.connectWithPOST(strData, address);	
    }
//////////////////HttpURLConnect Component/////////////////////    
    
//////////////////FingerprintComponent Component/////////////////////
    FingerprintComponent fingerprintComponent = new FingerprintComponent();
    
    public void hostFingerprintInit(Context c)
    {
    	fingerprintComponent.initFingerprint(c);
    }
    
    public char hostFingerprintStart(Context c)
    {
    	return fingerprintComponent.FingerprintStartListening(c);
    }
    
    public void hostFingerprintStop()
    {
    	fingerprintComponent.FingerprintStopListening();
    }
//////////////////FingerprintComponent Component/////////////////////
}
