package dev.mavexg.wallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class OperationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        Intent currentIntent = getIntent();
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
                startActivity(new Intent(OperationActivity.this, MainActivity.class));
            }
        });
    }
}
