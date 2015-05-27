package com.girlsleep.giser.girlsleep;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.widget.Button;

    public class MainActivity extends Activity implements OnClickListener {
        private static final String TAG = "ServiceDemo";
        Button buttonStart, buttonStop;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            startService(new Intent(this, MyService.class));
        }

        public void onClick(View src) {
            switch (src.getId()) {
                case R.id.buttonStart:
                    Log.i(TAG, "onClick: starting service");
                    startService(new Intent(this, MyService.class));
                    break;
                case R.id.buttonStop:
                    Log.i(TAG, "onClick: stopping service");
                    stopService(new Intent(this, MyService.class));
                    break;
            }
        }
    }