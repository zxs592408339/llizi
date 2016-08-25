使用AIDL和远程服务实现进程通信
 在Android中, 每个应用程序都有自己的进程，当需要在不同的进程之间传递对象时，该如何实现呢 显然, Java中是不支持跨进程内存共享的。因此要传递对象, 需要把对象解析成操作系统能够理解的数据格式, 以达到跨界对象访问的目的。在JavaEE中，采用RMI通过序列化传递对象。在Android中, 则采用AIDL(Android Interface Definition Language：接口定义语言)方式实现。
AIDL是一种接口定义语言，用于约束两个进程间的通讯规则，供编译器生成代码，实现Android设备上的两个进程间通信(IPC)。AIDL的IPC机制和EJB所采用的CORBA很类似，进程之间的通信信息，首先会被转换成AIDL协议消息，然后发送给对方，对方收到AIDL协议消息后再转换成相应的对象。由于进程之间的通信信息需要双向转换，所以android采用代理类在背后实现了信息的双向转换，代理类由android编译器生成，对开发人员来说是透明的。

实现进程通信，一般需要下面四个步骤：

假设A应用需要与B应用进行通信，调用B应用中的download(String path)方法，B应用以Service方式向A应用提供服务。

需要下面四个步骤: 
1>在B应用中创建*.aidl文件，aidl文件的定义和接口的定义很相类，如：在cn.ittx.aidl包下创建IDownloadService.aidl文件，内容如下：
package cn.ittx.aidl;
interface IDownloadService {
 void download(String path);
}
当完成aidl文件创建后，
eclipse会自动在项目的gen目录中同步生成IDownloadService.java接口文件。
AndroidStudio生成接口文件路径如下：

接口文件中生成一个Stub的抽象类，里面包括aidl定义的方法，还包括一些其它辅助方法。值得关注的是asInterface(IBinder iBinder)，它返回接口类型的实例，对于远程服务调用，远程服务返回给客户端的对象为代理对象，客户端在onServiceConnected(ComponentName name, IBinder service)方法引用该对象时不能直接强转成接口类型的实例，而应该使用asInterface(IBinder iBinder)进行类型转换。

编写Aidl文件时，需要注意下面几点:
1.接口名和aidl文件名相同。
2.接口和方法前不用加访问权限修饰符public,private,protected等,也不能用final,static。
3.aidl默认支持的类型包括java基本类型（int、long、boolean等）和（String、List、Map、CharSequence），使用这些类型时不需要import声明。对于List和Map中的元素类型必须是aidl支持的类型。如果使用自定义类型作为参数或返回值，自定义类型必须实现Parcelable接口。
4.自定义类型和AIDL生成的其它接口类型在aidl描述文件中，应该显式import，即便在该类和定义的包在同一个包中。
5.在aidl文件中所有非Java基本类型参数必须加上in、out、inout标记，以指明参数是输入参数、输出参数还是输入输出参数。
6.Java原始类型默认的标记为in,不能为其它标记。

2>在B应用中实现aidl文件生成的接口（本例是IDownloadService），但并非直接实现接口，而是通过继承接口的Stub来实现（Stub抽象类内部实现了aidl接口），并且实现接口方法的代码。内容如下：
public class ServiceBinder extends IDownloadService.Stub {
 @Override
 public void download(String path) throws RemoteException {
Log.i("DownloadService", path);
 } 
}
3>在B应用中创建一个Service（服务），在服务的onBind(Intent intent)方法中返回实现了aidl接口的对象（本例是ServiceBinder）。内容如下：
public class DownloadService extends Service {
 private ServiceBinder serviceBinder = new ServiceBinder();
 @Override
 public IBinder onBind(Intent intent) {
 return serviceBinder;
 }
 public class ServiceBinder extends IDownloadService.Stub {
 @Override
 public void download(String path) throws RemoteException {
Log.i("DownloadService", path);
 } 
 }
}
其他应用可以通过隐式意图访问服务,意图的动作可以自定义，AndroidManifest.xml配置代码如下：
<service android:name=".DownloadService" >
 <intent-filter>
 <action android:name="cn.ittx.aidl.DownloadService" />
 </intent-filter>
</service>

4>把B应用中aidl文件所在package连同aidl文件一起拷贝到客户端A应用，eclipse会自动在A应用的gen目录中为aidl文件同步生成IDownloadService.java接口文件,接下来就可以在A应用中实现与B应用通信，代码如下：
public class ClientActivity extends Activity {
 private IDownloadService downloadService;
 @Override
 public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
 this.bindService(new Intent("cn.ittx.aidl.DownloadService"), this.serviceConnection, BIND_AUTO_CREATE);//绑定到服务
 }
 @Override
 protected void onDestroy() {
super.onDestroy();
this.unbindService(serviceConnection);//解除服务
 } 

 private ServiceConnection serviceConnection = new ServiceConnection() {
 @Override
 public void onServiceConnected(ComponentName name, IBinder service) {
downloadService = IDownloadService.Stub.asInterface(service);
try {
downloadService.download("http://www.itcast.cn");
} catch (RemoteException e) {
Log.e("ClientActivity", e.toString());
}
 }
 @Override
 public void onServiceDisconnected(ComponentName name) {
downloadService = null;
 }
 };
}


