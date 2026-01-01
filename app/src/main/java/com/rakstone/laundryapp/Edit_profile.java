package com.rakstone.laundryapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Edit_profile extends AppCompatActivity {

    public static String mail="";
    ImageView home_edit, profile, cart, edit_lenden;
    EditText ed_name, ed_mobile, ed_mail, edit_address;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bottom navigation
        home_edit = findViewById(R.id.home_edit);
        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        edit_lenden = findViewById(R.id.edit_lenden);

        // EditTexts
        ed_name = findViewById(R.id.ed_name);
        ed_mobile = findViewById(R.id.ed_mobile);
        ed_mail = findViewById(R.id.ed_mail);
        edit_address = findViewById(R.id.edit_address);

        // Save button (তুমি xml-এ add করো)
        button_save = findViewById(R.id.button_save);

        // Click listeners
        onclick();

        // Save button listener
        button_save.setOnClickListener(view -> updateProfile());
    }

    private void onclick() {
        home_edit.setOnClickListener(view -> startActivity(new Intent(Edit_profile.this, home_page.class)));
        profile.setOnClickListener(view -> startActivity(new Intent(Edit_profile.this, My_profile.class)));
        edit_lenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Edit_profile.this,Order_history.class));
            }
        });
    }

    private void updateProfile() {
        String name = ed_name.getText().toString().trim();
        String mobile = ed_mobile.getText().toString().trim();
        String email = mail;
        String address = edit_address.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Only append non-empty fields
        StringBuilder urlBuilder = new StringBuilder("http://172.20.10.10/apps/update_profile.php?email=" + email);
        if (!name.isEmpty()) urlBuilder.append("&name=").append(name);
        if (!mobile.isEmpty()) urlBuilder.append("&mobile=").append(mobile);
        if (!address.isEmpty()) urlBuilder.append("&address=").append(address);

        String url = urlBuilder.toString();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> new AlertDialog.Builder(Edit_profile.this)
                        .setTitle("Server Response")
                        .setMessage(response)
                        .show(),
                error -> Toast.makeText(Edit_profile.this, "Error: " + (error.getMessage() != null ? error.getMessage() : "Check server"), Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
