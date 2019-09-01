package dev.mavexg.wallettracker.utilities;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Transaction implements Serializable {

    private String mType;
    private String mTag;
    private UAHCash mCash;
    private DateTime mTransactionTime;

    public Transaction(final String type,
                       final String tag,
                       final UAHCash cash,
                       final DateTime transactionTime) {
        this.mType = type;
        this.mTag = tag;
        this.mCash = cash;
        this.mTransactionTime = transactionTime;
    }

    public static List<Transaction> getTransactionsFromObjectSafely(final Object object) {
        List<Transaction> result = new ArrayList<>();
        if (object instanceof List) {
            for (int i = 0; i < ((List<?>) object).size(); ++i) {
                Object item = ((List<?>) object).get(i);
                if (item instanceof Transaction) {
                    result.add((Transaction) item);
                }
            }
        }
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return mType + ": " + mCash.toString() + " (" + mTag + "), " + mTransactionTime.toString();
    }
}
