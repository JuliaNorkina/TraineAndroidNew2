package com.example.factasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        editText = findViewById(R.id.editText);
        tv = findViewById(R.id.tv);
        findViewById(R.id.bOk).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bOk:
                MyAsyncTask myAsyncTask = new MyAsyncTask(this);
                myAsyncTask.execute(Integer.parseInt(editText.getText().toString()));
        }

    }

    private static class MyAsyncTask extends AsyncTask<Integer,Void,Integer> {

        private WeakReference<MainActivity> activityReference;

        MyAsyncTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int fact = 1;
            for(int count = integers[0]; count > 1; count--) {
                fact = fact * count;
            }
            return fact;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            MainActivity activity = activityReference.get();
            if (activity == null) return;
            activity.tv.setText(String.valueOf(integer));
        }
    }

}
