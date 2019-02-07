package com.example.factrunable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends Activity implements View.OnClickListener {
    private int result;
    private EditText eT;
    private TextView tV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        eT = findViewById(R.id.eT);
        tV = findViewById(R.id.tV);
        findViewById(R.id.bOk).setOnClickListener(this);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("result", result);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result = savedInstanceState.getInt("result");
        tV.setText(String.valueOf(result));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bOk:
                if (!eT.getText().toString().equals("")) {
                    int number = Integer.parseInt(eT.getText().toString());

                    FactTask fact = new FactTask(number);
                    ExecutorService executor = Executors.newFixedThreadPool(1);
                    Future<Integer> future = executor.submit(fact);
                    try {
                        result = future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    tV.setText(String.valueOf(result));
                    executor.shutdown();
                } else Toast.makeText(this, "Enter number", Toast.LENGTH_LONG).show();
        }
    }
}
