package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import dev.mavexg.wallettracker.utilities.UAHCash;

public class MainActivity extends AppCompatActivity {
    private UAHCash mCurrentCash;

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
                Intent toLoad = new Intent(MainActivity.this, OperationActivity.class);
                toLoad.putExtra("operation", "adding");
                toLoad.putExtra("current_cash", mCurrentCash);
                startActivity(toLoad);
            }
        });

        findViewById(R.id.button_main_removing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OperationActivity.class);
                intent.putExtra("operation", "removing");
                intent.putExtra("current_cash", mCurrentCash);
                startActivity(intent);
            }
        });
    }
}
