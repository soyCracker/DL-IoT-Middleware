# 開發文檔

使用之技術來自[dynamic-load-apk](https://github.com/singwhatiwanna/dynamic-load-apk)

## 觀念簡介
DL-lib為library  
doi-common為API的interface，透過它使用預先設定好的middleware API  
doi-host為middleware本體  
插件(Plugin apk)為動態載入至Middleware的APP  

## 這裡將簡單敘述幾個開發時的注意事項：  
### 1.  
在Plugin apk的Java Build Path>>Projects中加入DL-lib及doi-common  
### 2.  
將程式碼：  
```java
public class MainActivity extends Activity
```
改成：
```java
public class MainActivity extends DLBasePluginActivity
```
### 3.  
若要使用middleware API，宣告：
```java
HostInterface hostInterface = HostInterfaceManager.getHostInterface();
```
之後便可以開始使用API，如初始化藍芽：
```java
hostInterface.hostBtInit(that);
```  
  
關於API請參看[apiList]( https://github.com/soyCracker/DL-IoT-Middleware/blob/master/middleware/apiList.md)，  
若想了解運作原理及有開發上的疑問，請參看技術來源[dynamic-load-apk](https://github.com/singwhatiwanna/dynamic-load-apk)  

## API擴充
DL-IoT-Middleware的API具有可擴充性，擴充方式也非常簡單，將function class放在doi-host的host.component，  
把interface implement寫入HostInterfaceImp.java，最後將interface放入doi-common的HostInterface.java，  
就能在Plugin apk中使用
