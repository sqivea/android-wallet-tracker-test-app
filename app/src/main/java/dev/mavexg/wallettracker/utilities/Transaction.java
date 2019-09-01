package dev.mavexg.wallettracker.utilities;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Transaction implements Serializable {

    private String mType;
    private UAHCash mCash;
    private DateTime mTransactionTime;

    public Transaction(String mType, UAHCash mCash, DateTime mTransactionTime) {
        this.mType = mType;
        this.mCash = mCash;
        this.mTransactionTime = mTransactionTime;
    }

    public String getmType() {
        return mType;
    }

    public UAHCash getmCash() {
        return mCash;
    }

    public DateTime getmTransactionTime() {
        return mTransactionTime;
    }

}
