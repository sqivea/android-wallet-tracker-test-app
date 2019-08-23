package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import dev.mavexg.wallettracker.utilities.Constants;
import dev.mavexg.wallettracker.utilities.UAHCash;

public class MainActivity extends AppCompatActivity {

    private static final String CLASS_TAG = "MainActivity";

    private UAHCash mCurrentCash = new UAHCash();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setData();
        setUIData();
        setListeners();
    }

    private void setData() {
        Intent currentIntent = getIntent();
        if (currentIntent.hasExtra("current_cash")) {
            readFromIntentExtras(currentIntent);
        } else {
            readFromAppData();
        }
    }

    private void readFromIntentExtras(final Intent currentIntent) {
        mCurrentCash = (UAHCash) currentIntent.getSerializableExtra("current_cash");
    }

    private void readFromAppData() {
        try {
            FileInputStream fis = getApplicationContext().openFileInput(Constants.DATA_FILE);
            ObjectInputStream ois;
            ois = new ObjectInputStream(fis);
            mCurrentCash = (UAHCash) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            Log.e(CLASS_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void setUIData() {
        ((TextView) findViewById(R.id.cash_amount)).setText(mCurrentCash.toString());
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
