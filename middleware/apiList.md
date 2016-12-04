Bluetooth API：
```java
    //初始化藍芽，若有錯誤資訊會回傳
    public void hostBtInit(Context c);
    
    //列出搜索到的藍芽裝置位址
    public Set<BluetoothDevice> hostBtDeviceList();
    
    //連結藍芽裝置，必須先透過hostBtSetAddress設定欲連結之藍芽裝置位址，若有錯誤資訊會回傳
    public void hostBtConnect(Context c,String btAddress);
    
    //中止與藍芽裝置連結，若有錯誤資訊會回傳
    public void hostBtPause(Context c);
    
    //傳送字串至藍芽裝置，回傳送出的資料與藍芽位址，若有錯誤資訊會回傳
    public void hostBtSendData(Context c , String strMsg);
    
    //讀取藍芽裝置傳來的資料，若有錯誤資訊會回傳
    public String hostBtReadData(Context c);  
```
httpURLconnect API：
```java
    //向目標網頁發出GET請求，並傳入字串，並取得網頁回覆
    public String hostHttpUrlGet(String strData,String address);
    
    //向目標網頁發出POST請求，並傳入字串，並取得網頁回覆
    public String hostHttpUrlPost(String strData,String address);
```
Fingerprint API(測試中)：
```java
    public void hostFingerprintInit(Context c);
    public char hostFingerprintStart(Context c);
    public void hostFingerprintStop();
```
