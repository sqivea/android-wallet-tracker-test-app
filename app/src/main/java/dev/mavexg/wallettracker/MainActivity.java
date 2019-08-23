package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private int mCurrentCashe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListeners();
    }

    private void setListeners() {
        findViewById(R.id.button_main_adding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OperationActivity.class);
                intent.putExtra("operation", "adding");
                intent.putExtra("current_cash", mCurrentCashe);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_main_removing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OperationActivity.class);
                intent.putExtra("operation", "removing");
                intent.putExtra("current_cash", mCurrentCashe);
                startActivity(intent);
            }
        });
    }
}
