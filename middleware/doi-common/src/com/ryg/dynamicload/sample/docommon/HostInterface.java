package com.ryg.dynamicload.sample.docommon;

import java.util.Set;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

public interface HostInterface {
    
//////////////////Bluetooth Component/////////////////////    
	
	//��l���ŪޡA�Y�����~��T�|�^��
    public void hostBtInit(Context c);
    
    //�C�X�j���쪺�Ū޸˸m��}
    public Set<BluetoothDevice> hostBtDeviceList();
    
    //�s���Ū޸˸m�A�������z�LhostBtSetAddress�]�w���s�����Ū޸˸m��}�A�Y�����~��T�|�^��
    public void hostBtConnect(Context c,String btAddress);
    
    //����P�Ū޸˸m�s���A�Y�����~��T�|�^��
    public void hostBtPause(Context c);
    
    //�ǰe�r����Ū޸˸m�A�^�ǰe�X����ƻP�Ūަ�}�A�Y�����~��T�|�^��
    public void hostBtSendData(Context c , String strMsg);
    
    //Ū���Ū޸˸m�ǨӪ���ơA�Y�����~��T�|�^��
    public String hostBtReadData(Context c);  
//////////////////Bluetooth Component/////////////////////	
    
//////////////////HttpURLConnect Component/////////////////////
    
    //�V�ؼк����o�XGET�ШD�A�öǤJ�r��A�è��o�����^��
    public String hostHttpUrlGet(String strData,String address);
    
    //�V�ؼк����o�XPOST�ШD�A�öǤJ�r��A�è��o�����^��
    public String hostHttpUrlPost(String strData,String address);
//////////////////HttpURLConnect Component/////////////////////
    
//////////////////FingerprintComponent Component/////////////////////
    
    public void hostFingerprintInit(Context c);
    public char hostFingerprintStart(Context c);
    public void hostFingerprintStop();
//////////////////FingerprintComponent Component/////////////////////
}
