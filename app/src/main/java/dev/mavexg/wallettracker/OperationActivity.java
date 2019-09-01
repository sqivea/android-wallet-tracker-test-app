package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.travijuu.numberpicker.library.NumberPicker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

import dev.mavexg.wallettracker.utilities.Constants;
import dev.mavexg.wallettracker.utilities.UAHCash;

public class OperationActivity extends AppCompatActivity {

    private static final String CLASS_TAG = "OperationActivity";

    private enum Mode {
        ADDING, REMOVING
    }

    private UAHCash mCurrentCash = new UAHCash();
    private Mode mMode = Mode.ADDING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        Intent currentIntent = getIntent();
        mCurrentCash = (UAHCash) currentIntent.getSerializableExtra("current_cash");
        setupMode(Objects.requireNonNull(currentIntent.getStringExtra("operation")));
        setListeners();
    }

    private void setupMode(final String operation) {
        if (operation.equals("removing")) {
            mMode = Mode.REMOVING;
            Button button = findViewById(R.id.button_operation_direct);
            button.setBackground(getResources().getDrawable(R.drawable.button_remove_style));
            button.setText(R.string.text_button_remove_direct);
        }
    }

    private void setListeners() {
        findViewById(R.id.button_operation_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoad = new Intent(OperationActivity.this, MainActivity.class);
                toLoad.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                reassignCurrentCash();
                saveData();

                toLoad.putExtra("current_cash", mCurrentCash);
                startActivity(toLoad);
            }
        });
    }

    private void reassignCurrentCash() {
        int hryvni = ((NumberPicker) findViewById(R.id.number_picker_hryvni)).getValue();
        int kopiyky = ((NumberPicker) findViewById(R.id.number_picker_kopiyky)).getValue();
        UAHCash toDealWith = new UAHCash(hryvni, kopiyky);
        if (mMode == Mode.ADDING) {
            mCurrentCash.plus(toDealWith);
        } else {
            mCurrentCash.minus(toDealWith);
        }
    }

    private void saveData() {
        FileOutputStream fos;
        try {
            fos = getApplicationContext().openFileOutput(Constants.CASH_DATA_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(mCurrentCash);
            os.close();
            fos.close();
        } catch (IOException e) {
            Log.e(CLASS_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}
