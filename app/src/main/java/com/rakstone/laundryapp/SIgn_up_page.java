package com.rakstone.laundryapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SIgn_up_page extends AppCompatActivity {

    TextView log_in;
    Button button_sign;
    EditText edit_name, edit_email, edit_phone_number, edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        log_in = findViewById(R.id.log_in);
        button_sign = findViewById(R.id.button_sign);
        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone_number = findViewById(R.id.edit_phone_number);
        edit_password = findViewById(R.id.edit_password);

        setButton_sign();
        log_in_page();
    }

    // ------------------------------------------
    private void log_in_page() {
        log_in.setOnClickListener(view ->
                startActivity(new Intent(SIgn_up_page.this, MainActivity.class)));
    }

    // ------------------------------------------
    // Gmail must end with .com
    private boolean isValidGmail(String email) {
        return email.endsWith("@gmail.com") &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // ------------------------------------------
    private void setButton_sign() {

        button_sign.setOnClickListener(view -> {

            String name = edit_name.getText().toString();
            String email = edit_email.getText().toString();
            String mobile = edit_phone_number.getText().toString();
            String password = edit_password.getText().toString();

            // Empty check
            if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(SIgn_up_page.this, "Fill up all fields", Toast.LENGTH_LONG).show();
                return;
            }

            // Phone check (must be 11 digits)
            if (mobile.length() != 11) {
                Toast.makeText(SIgn_up_page.this, "Phone number must be 11 digits", Toast.LENGTH_LONG).show();
                return;
            }

            // Gmail Validation
            if (!isValidGmail(email)) {
                Toast.makeText(SIgn_up_page.this,
                        "Email must be a valid Gmail ending with @gmail.com",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // API URL
            String url = "http://172.20.10.10/apps/sign_up.php?name=" + name +
                    "&email=" + email + "&mobile=" + mobile + "&password=" + password;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> new AlertDialog.Builder(SIgn_up_page.this)
                            .setTitle("Server Response")
                            .setMessage(response)
                            .show(),

                    error -> button_sign.setText("Error: " + error.getMessage())
            );

            RequestQueue requestQueue = Volley.newRequestQueue(SIgn_up_page.this);
            requestQueue.add(stringRequest);

        });
    }
}
