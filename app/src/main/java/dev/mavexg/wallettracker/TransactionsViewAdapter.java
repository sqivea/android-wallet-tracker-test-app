package dev.mavexg.wallettracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mavexg.wallettracker.utilities.Transaction;

class TransactionsViewAdapter extends RecyclerView.Adapter<TransactionsViewAdapter.ViewHolder> {

    private List<Transaction> mTransactions;

    TransactionsViewAdapter(final List<Transaction> transactions) {
        this.mTransactions = transactions;
    }

    @NonNull
    @Override
    public TransactionsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_info_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewAdapter.ViewHolder holder, int position) {
        holder.mTransactionInfo.setText(mTransactions.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTransactionInfo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTransactionInfo = itemView.findViewById(R.id.transaction_info);
        }
    }
}
