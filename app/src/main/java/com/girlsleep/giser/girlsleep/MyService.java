package com.girlsleep.giser.girlsleep;

        import android.app.Service;
        import android.content.Intent;
        import android.location.Location;
        import android.media.MediaPlayer;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.SystemClock;
        import android.util.Log;
        import android.widget.Toast;

        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLConnection;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;

public class MyService extends Service {
    private static final String TAG = "MyService";
    MediaPlayer player;

    private int TIME = 1000*5;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        handler.postDelayed(runnable, TIME); //每隔1s执行
//        player = MediaPlayer.create(this, R.raw.braincandy);
//        player.setLooping(false);
    }

    Handler handler = new Handler();
                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
                        // handler自带方法实现定时器
                        try {

                handler.postDelayed(runnable, TIME); //每隔1s执行

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
                        //    Date curDate   = getWebTime();
                                    String   str   =   df.format(curDate);

                Date d2 = df.parse(str);//当前时间
                String days = str.split(" ")[0];

                //判断监听的时间
                Date d0 = df.parse(days+" 22:50:00");
                long dis0 = -1000*3600*6;//解决0点时候的 夜晚也开机
                long diff2 = d2.getTime() - d0.getTime();
                if(diff2>0||diff2<dis0) {
                   TIME = 1000*5;//5 秒 监听一次
                }
                else   TIME = 1000*60*5;//5分钟 监听一次

                //星期
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_WEEK);
                if(day<2) day = 7;
                else  day = day-1;

                Date d1 = df.parse(days+" 23:00:00");
                if(day==5||day==6)  d1 = df.parse(days+" 23:58:59");

                long diff = d2.getTime() - d1.getTime();

                long dis = -1000*3600*6;//解决0点时候的
                long hournow = d2.getHours();
                if(diff>0||hournow<7) {
                    Process process = Runtime.getRuntime().exec("su");
                    DataOutputStream out = new DataOutputStream(
                            process.getOutputStream());
                    out.writeBytes("reboot -p\n");
                    out.writeBytes("exit\n");
                    out.flush();
                }
                else
                {
                    //提醒
                    long mini = diff/1000/60;//分钟
                    if(mini>-3)
                    {
                        if(mini>0)
                        Toast.makeText(MyService.this, "亲，还有"+String.valueOf(-mini)+"分钟关机！", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(MyService.this, "亲，还有"+String.valueOf(-diff/1000)+"秒关机！", Toast.LENGTH_SHORT).show();
                    }

                   // Toast.makeText(MyService.this, "运行中", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };

    public  Date getWebTime() throws Exception {
        URL url; Date date = null;
        try {
            url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect(); // 发出连接
            long ld = uc.getDate(); // 取得网站日期时间
             date = new Date(ld); // 转换为标准时间对象
            // 分别取得时间中的小时，分钟和秒，并输出
            Log.d("multicast",
                    date + ", " + date.getHours() + "时" + date.getMinutes()
                            + "分" + date.getSeconds() + "秒");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 取得资源对象
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return date;
    }



    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Log.i(TAG, "onStart");
    }
}