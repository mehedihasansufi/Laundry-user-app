package com.rakstone.laundryapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class home_page extends AppCompatActivity {

    public static String home_email="";
    TextView for_email;
    RelativeLayout random_color;
    LinearLayout wash,shoe;

    ImageView profile_icon,home_cart,home_lenden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        for_email=findViewById(R.id.for_email);

        wash=findViewById(R.id.wash);
        shoe=findViewById(R.id.shoe);

        for_email.setText(home_email);
        profile_icon=findViewById(R.id.profile_icon);
        home_cart=findViewById(R.id.home_cart);
        home_lenden=findViewById(R.id.home_lenden);
        random_color = findViewById(R.id.random_color);

        // Drawable reference নাও
        GradientDrawable drawable = (GradientDrawable) random_color.getBackground();

        // Random color generate
        int color = getRandomColor();

        // Drawable color change
        drawable.setColor(color);





        onclick();

    }

    private void onclick (){
        wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_page.this,Select_wash_item.class));
            }
        });


        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_page.this, My_profile.class));
            }
        });

        home_lenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_page.this,Order_history.class));
            }
        });


//        of cart

//        home_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(home_page.this, Cart.class));
//            }
//        });

    }

    // 🔹 Random color generate করার method
    private int getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.rgb(red, green, blue);
    }

}