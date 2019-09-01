package dev.mavexg.wallettracker.utilities;

import org.joda.time.DateTime;

import java.io.Serializable;

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

    public String getmType() {
        return mType;
    }

    public String getTag() {
        return mTag;
    }

    public UAHCash getmCash() {
        return mCash;
    }

    public DateTime getmTransactionTime() {
        return mTransactionTime;
    }

}
