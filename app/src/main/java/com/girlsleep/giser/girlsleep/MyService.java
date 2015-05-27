package com.girlsleep.giser.girlsleep;

        import android.app.Service;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Handler;
        import android.os.IBinder;
        import android.util.Log;
        import android.widget.Toast;

        import java.io.DataOutputStream;
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
        Toast.makeText(this, "My Service created", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onCreate");



//        player = MediaPlayer.create(this, R.raw.braincandy);
//        player.setLooping(false);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
                String   str   =   df.format(curDate);

                Date d2 = df.parse(str);//当前时间
                String days = str.split(" ")[0];

                //星期
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_WEEK);
                if(day<2) day = 7;
                else  day = day-1;

                Date d1 = df.parse(days+" 23:00:00");
                if(day==5||day==6)  d1 = df.parse(days+" 23:58:59");

                long diff = d2.getTime() - d1.getTime();

                if(diff>0) {
                    //Toast.makeText(MainActivity.this, "亲，该睡觉了哦！！！", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MyService.this, "shut down", Toast.LENGTH_SHORT).show();
                    Process process = Runtime.getRuntime().exec("su");
                    DataOutputStream out = new DataOutputStream(
                            process.getOutputStream());
                    out.writeBytes("reboot -p\n");
                    out.writeBytes("exit\n");
                    out.flush();
                }
                else
                {
                    Toast.makeText(MyService.this, "ok", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };


    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stoped", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onDestroy");
        player.stop();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Service Start", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onStart");
        handler.postDelayed(runnable, TIME); //每隔1s执行

        //player.start();
    }
}