package com.girlsleep.giser.girlsleep;

        import android.app.Service;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.SystemClock;
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
        Log.i(TAG, "onCreate");
        handler.postDelayed(runnable, TIME); //ÿ��1sִ��
//        player = MediaPlayer.create(this, R.raw.braincandy);
//        player.setLooping(false);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler�Դ�����ʵ�ֶ�ʱ��
            try {

                handler.postDelayed(runnable, TIME); //ÿ��1sִ��

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��
                String   str   =   df.format(curDate);

                Date d2 = df.parse(str);//��ǰʱ��
                String days = str.split(" ")[0];

                //�жϼ�����ʱ��
                Date d0 = df.parse(days+" 22:50:00");
                long dis0 = -1000*3600*6;//���0��ʱ��� ҹ��Ҳ����
                long diff2 = d2.getTime() - d0.getTime();
                if(diff2>0||diff2<dis0) {
                   TIME = 1000*5;//5 �� ����һ��
                }
                else   TIME = 1000*60*5;//5���� ����һ��

                //����
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_WEEK);
                if(day<2) day = 7;
                else  day = day-1;

                Date d1 = df.parse(days+" 23:00:00");
                if(day==5||day==6)  d1 = df.parse(days+" 23:58:59");

                long diff = d2.getTime() - d1.getTime();

                long dis = -1000*3600*6;//���0��ʱ��� ҹ��Ҳ����
                if(diff>0||diff<dis) {
                    Toast.makeText(MyService.this, "���裬��˯����Ŷ������5���Զ��ػ�������", Toast.LENGTH_SHORT).show();

                    SystemClock.sleep(5000);

                    Process process = Runtime.getRuntime().exec("su");
                    DataOutputStream out = new DataOutputStream(
                            process.getOutputStream());
                    out.writeBytes("reboot -p\n");
                    out.writeBytes("exit\n");
                    out.flush();
                }
                else
                {
                    //����
                    long mini = diff/1000/60;//����
                    if(mini>-3)
                    {
                        if(mini>0)
                        Toast.makeText(MyService.this, "�ף�����"+String.valueOf(-mini)+"���ӹػ���", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(MyService.this, "�ף�����"+String.valueOf(-diff/1000)+"��ػ���", Toast.LENGTH_SHORT).show();
                    }

                   // Toast.makeText(MyService.this, "������", Toast.LENGTH_SHORT).show();
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
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Log.i(TAG, "onStart");
    }
}