package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.mavexg.wallettracker.utilities.Transaction;

public class TransactionsActivity extends AppCompatActivity {

    private List<Transaction> mTransactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        mTransactions = Transaction.getTransactionsFromObjectSafely(getIntent().getSerializableExtra("transactions"));
        Collections.reverse(mTransactions);
        setupRecycler();
    }

    private void setupRecycler() {
        RecyclerView transactionsView = findViewById(R.id.transactionsView);
        transactionsView.setHasFixedSize(true);
        transactionsView.setLayoutManager(new LinearLayoutManager(this));
        transactionsView.setAdapter(new TransactionsViewAdapter(mTransactions));
    }
}
