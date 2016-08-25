ʹ��AIDL��Զ�̷���ʵ�ֽ���ͨ��
 ��Android��, ÿ��Ӧ�ó������Լ��Ľ��̣�����Ҫ�ڲ�ͬ�Ľ���֮�䴫�ݶ���ʱ�������ʵ���� ��Ȼ, Java���ǲ�֧�ֿ�����ڴ湲��ġ����Ҫ���ݶ���, ��Ҫ�Ѷ�������ɲ���ϵͳ�ܹ��������ݸ�ʽ, �Դﵽ��������ʵ�Ŀ�ġ���JavaEE�У�����RMIͨ�����л����ݶ�����Android��, �����AIDL(Android Interface Definition Language���ӿڶ�������)��ʽʵ�֡�
AIDL��һ�ֽӿڶ������ԣ�����Լ���������̼��ͨѶ���򣬹����������ɴ��룬ʵ��Android�豸�ϵ��������̼�ͨ��(IPC)��AIDL��IPC���ƺ�EJB�����õ�CORBA�����ƣ�����֮���ͨ����Ϣ�����Ȼᱻת����AIDLЭ����Ϣ��Ȼ���͸��Է����Է��յ�AIDLЭ����Ϣ����ת������Ӧ�Ķ������ڽ���֮���ͨ����Ϣ��Ҫ˫��ת��������android���ô������ڱ���ʵ������Ϣ��˫��ת������������android���������ɣ��Կ�����Ա��˵��͸���ġ�

ʵ�ֽ���ͨ�ţ�һ����Ҫ�����ĸ����裺

����AӦ����Ҫ��BӦ�ý���ͨ�ţ�����BӦ���е�download(String path)������BӦ����Service��ʽ��AӦ���ṩ����

��Ҫ�����ĸ�����: 
1>��BӦ���д���*.aidl�ļ���aidl�ļ��Ķ���ͽӿڵĶ�������࣬�磺��cn.ittx.aidl���´���IDownloadService.aidl�ļ����������£�
package cn.ittx.aidl;
interface IDownloadService {
 void download(String path);
}
�����aidl�ļ�������
eclipse���Զ�����Ŀ��genĿ¼��ͬ������IDownloadService.java�ӿ��ļ���
AndroidStudio���ɽӿ��ļ�·�����£�

�ӿ��ļ�������һ��Stub�ĳ����࣬�������aidl����ķ�����������һЩ��������������ֵ�ù�ע����asInterface(IBinder iBinder)�������ؽӿ����͵�ʵ��������Զ�̷�����ã�Զ�̷��񷵻ظ��ͻ��˵Ķ���Ϊ������󣬿ͻ�����onServiceConnected(ComponentName name, IBinder service)�������øö���ʱ����ֱ��ǿת�ɽӿ����͵�ʵ������Ӧ��ʹ��asInterface(IBinder iBinder)��������ת����

��дAidl�ļ�ʱ����Ҫע�����漸��:
1.�ӿ�����aidl�ļ�����ͬ��
2.�ӿںͷ���ǰ���üӷ���Ȩ�����η�public,private,protected��,Ҳ������final,static��
3.aidlĬ��֧�ֵ����Ͱ���java�������ͣ�int��long��boolean�ȣ��ͣ�String��List��Map��CharSequence����ʹ����Щ����ʱ����Ҫimport����������List��Map�е�Ԫ�����ͱ�����aidl֧�ֵ����͡����ʹ���Զ���������Ϊ�����򷵻�ֵ���Զ������ͱ���ʵ��Parcelable�ӿڡ�
4.�Զ������ͺ�AIDL���ɵ������ӿ�������aidl�����ļ��У�Ӧ����ʽimport�������ڸ���Ͷ���İ���ͬһ�����С�
5.��aidl�ļ������з�Java�������Ͳ����������in��out��inout��ǣ���ָ�����������������������������������������
6.Javaԭʼ����Ĭ�ϵı��Ϊin,����Ϊ������ǡ�

2>��BӦ����ʵ��aidl�ļ����ɵĽӿڣ�������IDownloadService����������ֱ��ʵ�ֽӿڣ�����ͨ���̳нӿڵ�Stub��ʵ�֣�Stub�������ڲ�ʵ����aidl�ӿڣ�������ʵ�ֽӿڷ����Ĵ��롣�������£�
public class ServiceBinder extends IDownloadService.Stub {
 @Override
 public void download(String path) throws RemoteException {
Log.i("DownloadService", path);
 } 
}
3>��BӦ���д���һ��Service�����񣩣��ڷ����onBind(Intent intent)�����з���ʵ����aidl�ӿڵĶ��󣨱�����ServiceBinder�����������£�
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
����Ӧ�ÿ���ͨ����ʽ��ͼ���ʷ���,��ͼ�Ķ��������Զ��壬AndroidManifest.xml���ô������£�
<service android:name=".DownloadService" >
 <intent-filter>
 <action android:name="cn.ittx.aidl.DownloadService" />
 </intent-filter>
</service>

4>��BӦ����aidl�ļ�����package��ͬaidl�ļ�һ�𿽱����ͻ���AӦ�ã�eclipse���Զ���AӦ�õ�genĿ¼��Ϊaidl�ļ�ͬ������IDownloadService.java�ӿ��ļ�,�������Ϳ�����AӦ����ʵ����BӦ��ͨ�ţ��������£�
public class ClientActivity extends Activity {
 private IDownloadService downloadService;
 @Override
 public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
 this.bindService(new Intent("cn.ittx.aidl.DownloadService"), this.serviceConnection, BIND_AUTO_CREATE);//�󶨵�����
 }
 @Override
 protected void onDestroy() {
super.onDestroy();
this.unbindService(serviceConnection);//�������
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


