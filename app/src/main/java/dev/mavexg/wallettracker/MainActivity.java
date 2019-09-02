package dev.mavexg.wallettracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.mavexg.wallettracker.utilities.Constants;
import dev.mavexg.wallettracker.utilities.Transaction;
import dev.mavexg.wallettracker.utilities.UAHCash;

public class MainActivity extends AppCompatActivity {

    private static final String CLASS_TAG = "MainActivity";

    private UAHCash mCurrentCash = new UAHCash();
    private List<Transaction> mTransactions = new ArrayList<>();

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
        if (currentIntent.hasExtra("current_cash")
                && currentIntent.hasExtra("transactions")) {
            readFromIntentExtras(currentIntent);
        } else {
            readFromAppData();
        }
    }

    private void readFromIntentExtras(final Intent currentIntent) {
        mCurrentCash = (UAHCash) currentIntent.getSerializableExtra("current_cash");
        mTransactions = Transaction.getTransactionsFromObjectSafely(currentIntent.getSerializableExtra("transactions"));
    }

    private void readFromAppData() {
        try {
            readCashData();
            readTransactionsData();
        } catch (IOException | ClassNotFoundException e) {
            Log.e(CLASS_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void readCashData() throws IOException, ClassNotFoundException {
        FileInputStream fis = getApplicationContext().openFileInput(Constants.CASH_DATA_FILE);
        ObjectInputStream ois;
        ois = new ObjectInputStream(fis);
        mCurrentCash = (UAHCash) ois.readObject();
        ois.close();
        fis.close();
    }

    private void readTransactionsData() throws IOException, ClassNotFoundException {
        FileInputStream fis = getApplicationContext().openFileInput(Constants.TRANSACTIONS_DATA_FILE);
        ObjectInputStream ois;
        ois = new ObjectInputStream(fis);
        mTransactions = Transaction.getTransactionsFromObjectSafely(ois.readObject());
        ois.close();
        fis.close();
    }

    private void setUIData() {
        ((TextView) findViewById(R.id.cash_amount)).setText(mCurrentCash.toString());
    }

    private void setListeners() {
        findViewById(R.id.button_main_adding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOperationActivity("adding");
            }
        });

        findViewById(R.id.button_main_removing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOperationActivity("removing");
            }
        });

        findViewById(R.id.button_main_showing_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadShowListActivity();
            }
        });
    }

    private void loadOperationActivity(final String operation) {
        Intent toLoad = new Intent(MainActivity.this, OperationActivity.class);
        toLoad.putExtra("operation", operation);
        toLoad.putExtra("current_cash", mCurrentCash);
        toLoad.putExtra("transactions", (ArrayList<Transaction>) mTransactions);
        startActivity(toLoad);
    }

    private void loadShowListActivity() {
        Intent toLoad = new Intent(MainActivity.this, TransactionsActivity.class);
        toLoad.putExtra("transactions", (ArrayList<Transaction>) mTransactions);
        startActivity(toLoad);
    }
}
