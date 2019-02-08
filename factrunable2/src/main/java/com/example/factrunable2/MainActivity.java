package com.example.factrunable2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private TextView tv;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        editText = findViewById(R.id.eT);
        tv = findViewById(R.id.tV);
        findViewById(R.id.bOk).setOnClickListener(this);

        handler = new MyHandler(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("MyLog", "count of threads: " + Thread.activeCount());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int result = 1;
                for (int count = Integer.parseInt(editText.getText().toString()); count > 1; count--) {
                    result = result * count;
                }
                handler.sendEmptyMessage(result);
            }
        };
        new Thread(runnable).start();
        Log.d("MyLog", "count of threads: " + Thread.activeCount());
    }

    private static class MyHandler extends Handler{
        private WeakReference<MainActivity> activityReference;

        MyHandler (MainActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            activityReference.get().tv.setText(String.valueOf(msg.what));
        }
    }
}
