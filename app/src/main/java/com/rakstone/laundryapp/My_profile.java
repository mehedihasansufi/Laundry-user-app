package com.rakstone.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class My_profile extends AppCompatActivity {

    public static String my_profile_email="";
    public static String my_profile_phone="";
    public static String my_profile_name="";

    public static String my_profile_address="";

    TextView name,phone,email,pickup,delivery;

ImageView home_myprofile,profile_cart,profile_lenden;
    Button edit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        pickup=findViewById(R.id.pickup);
        delivery=findViewById(R.id.delivery);
        edit_button=findViewById(R.id.edit_button);
        home_myprofile=findViewById(R.id.home_myprofile);
        profile_cart=findViewById(R.id.profile_cart);
        profile_lenden=findViewById(R.id.profile_lenden);


        name.setText(my_profile_name);
        phone.setText(my_profile_phone);
        email.setText(my_profile_email);
        pickup.setText(my_profile_address);

        onclick();


    }


    private void onclick(){
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_profile.mail=my_profile_email;
                startActivity(new Intent(My_profile.this,Edit_profile.class));
            }
        });


        home_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(My_profile.this,home_page.class));
            }
        });

        profile_lenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(My_profile.this,Order_history.class));
            }
        });

//        of cart

//        profile_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(My_profile.this,Cart.class));
//            }
//        });
    }
}