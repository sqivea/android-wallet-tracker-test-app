package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.travijuu.numberpicker.library.NumberPicker;

import org.joda.time.DateTime;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.mavexg.wallettracker.utilities.Constants;
import dev.mavexg.wallettracker.utilities.Transaction;
import dev.mavexg.wallettracker.utilities.UAHCash;

public class OperationActivity extends AppCompatActivity {

    private static final String CLASS_TAG = "OperationActivity";

    private enum Mode {
        ADDING, REMOVING
    }

    private UAHCash mCurrentCash = new UAHCash();
    private List<Transaction> mTransactions = new ArrayList<>();
    private Mode mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        Intent currentIntent = getIntent();
        mCurrentCash = (UAHCash) currentIntent.getSerializableExtra("current_cash");
        mTransactions = Transaction.getTransactionsFromObjectSafely(currentIntent.getSerializableExtra("transactions"));
        mMode = Objects.requireNonNull(currentIntent.getStringExtra("operation"))
                .equals("adding") ? Mode.ADDING : Mode.REMOVING;

        setupMode();
        setListeners();
    }

    private void setupMode() {
        setupSpinner();

        switch (mMode) {
            case ADDING:
                findViewById(R.id.spinnerTags).setVisibility(View.INVISIBLE);
                break;
            case REMOVING:
            default:
                Button button = findViewById(R.id.button_operation_direct);
                button.setBackground(getResources().getDrawable(R.drawable.button_remove_style));
                button.setText(R.string.text_button_remove_direct);
        }
    }

    private void setupSpinner() {
        Spinner spinnerTags = findViewById(R.id.spinnerTags);
        List<String> tags = new ArrayList<String>() {
            {
                if (mMode == Mode.ADDING) {
                    add("Получено");
                } else {
                    add("Еда");
                    add("Одежда");
                    add("Для души");
                    add("Утилити");
                    add("Проезд/пропуск");
                    add("На других");
                    add("Утрачено");
                }
            }
        };
        ArrayAdapter<String> tagsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tags);
        spinnerTags.setAdapter(tagsAdapter);
    }

    private void setListeners() {
        findViewById(R.id.button_operation_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoad = new Intent(OperationActivity.this, MainActivity.class);
                toLoad.addCategory(Intent.CATEGORY_HOME);
                toLoad.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                updateCurrentCash();
                updateTransactions();
                saveData();

                toLoad.putExtra("current_cash", mCurrentCash);
                toLoad.putExtra("transactions", (ArrayList<Transaction>) mTransactions);
                startActivity(toLoad);
            }
        });
    }

    private UAHCash getCashToDealWith() {
        int hryvni = ((NumberPicker) findViewById(R.id.number_picker_hryvni)).getValue();
        int kopiyky = ((NumberPicker) findViewById(R.id.number_picker_kopiyky)).getValue();
        return new UAHCash(hryvni, kopiyky);
    }

    private void updateCurrentCash() {
        UAHCash toDealWith = getCashToDealWith();
        if (mMode == Mode.ADDING) {
            mCurrentCash.plus(toDealWith);
        } else {
            mCurrentCash.minus(toDealWith);
        }
    }

    private void updateTransactions() {
        mTransactions.add(new Transaction(
                mMode == Mode.ADDING ? "+" : "-",
                ((Spinner) findViewById(R.id.spinnerTags)).getSelectedItem().toString(),
                getCashToDealWith(),
                DateTime.now()
        ));

        if (mTransactions.size() > 50) {
            mTransactions.remove(mTransactions.get(0));
        }
    }

    private void saveData() {
        try {
            saveCashData();
            saveTransactionsData();
        } catch (IOException e) {
            Log.e(CLASS_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void saveCashData() throws IOException {
        FileOutputStream fos;
        fos = getApplicationContext().openFileOutput(Constants.CASH_DATA_FILE, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(mCurrentCash);
        os.close();
        fos.close();
    }

    private void saveTransactionsData() throws IOException {
        FileOutputStream fos;
        fos = getApplicationContext().openFileOutput(Constants.TRANSACTIONS_DATA_FILE, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(mTransactions);
        os.close();
        fos.close();
    }
}
