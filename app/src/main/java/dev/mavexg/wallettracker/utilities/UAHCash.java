package dev.mavexg.wallettracker.utilities;

import java.io.Serializable;

public class UAHCash implements Serializable {

    private int mHryvni;
    private int mKopiyky;

    public UAHCash(final int hryvni, final int kopiyky) {
        mHryvni = hryvni;
        mKopiyky = kopiyky;

        if (isInvalid()) {
            validate();
        }
    }

    /**
     * @return self
     */
    public UAHCash plus(final UAHCash other) {
        if (isInvalid() || other.isInvalid()) {
            throw new AssertionError();
        }

        mHryvni += other.mHryvni;
        mKopiyky += other.mKopiyky;

        if (isInvalid()) {
            validate();
        }

        return this;
    }

    /**
     * @return self
     */
    public UAHCash minus(final UAHCash other) {
        if (isInvalid() || other.isInvalid()) {
            throw new AssertionError();
        }

        mHryvni -= other.mHryvni;
        mKopiyky -= other.mKopiyky;

        if (isInvalid()) {
            validate();
        }

        return this;
    }

    private boolean isInvalid() {
        return !isValidatable() || mKopiyky >= 100;
    }

    private boolean isValidatable() {
        return mHryvni >= 0 && mKopiyky >= 0;
    }

    private void validate() {
        if (isValidatable()) {
            UAHCash toAdd = fromKopiyky(mKopiyky);
            mHryvni += toAdd.mHryvni;
            mKopiyky = toAdd.mKopiyky;
        } else {
            mHryvni = 0;
            mKopiyky = 0;
        }
    }

    private UAHCash fromKopiyky(final int kopiyky) {
        int hryvni = kopiyky / 100;
        int leftover = kopiyky % 100;
        return new UAHCash(hryvni, leftover);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UAHCash)) {
            return false;
        }

        UAHCash uahCash = (UAHCash) o;
        return mHryvni == uahCash.mHryvni &&
                mKopiyky == uahCash.mKopiyky;
    }

    @Override
    public String toString() {
        return mHryvni + ","
                + (mKopiyky < 10 ? "0" + mKopiyky : mKopiyky)
                + "UAH";
    }
}
