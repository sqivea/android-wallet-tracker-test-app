package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

import dev.mavexg.wallettracker.utilities.UAHCash;

public class OperationActivity extends AppCompatActivity {

    private static final String CLASS_TAG = "OperationActivity";
    private static final String DATA_FILE = "WalletTrackerDataFile";

    private UAHCash mCurrentCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        Intent currentIntent = getIntent();
        mCurrentCash = (UAHCash) currentIntent.getSerializableExtra("data");
        setupButton(Objects.requireNonNull(currentIntent.getStringExtra("operation")));
        setListeners();
    }

    private void setupButton(final String operation) {
        if (operation.equals("removing")) {
            Button button = findViewById(R.id.button_operation_direct);
            button.setBackground(getResources().getDrawable(R.drawable.button_remove_style));
            button.setText(R.string.text_button_remove_direct);
        }
    }

    private void setListeners() {
        findViewById(R.id.button_operation_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent toLoad = new Intent(OperationActivity.this, MainActivity.class);
                toLoad.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(toLoad);
            }
        });
    }

    private void saveData() {
        FileOutputStream fos;
        try {
            fos = getApplicationContext().openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(mCurrentCash);
            os.close();
            fos.close();
        } catch (IOException e) {
            Log.e(CLASS_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}
