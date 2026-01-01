package com.rakstone.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Select_wash_item extends AppCompatActivity {

    ImageView back;

    TextView shirt_minus, shirt_item, shirt_plus,
            pant_minus, pant_item, pant_plus,
            hoodie_minus, hoodie_item, hoodie_plus,
            saree_minus, saree_item, saree_plus,
            total_cost, total_item;

    Button cart;

    int shirt_total = 0;
    int pant_total = 0;
    int hoodie_total = 0;
    int saree_total = 0;

    int item_total = 0;
    int total_price = 0;

    // প্রতিটির দাম (তুমি চাইলে নিজের মতো সেট করতে পারো)
    int shirt_price = 20;
    int pant_price = 25;
    int hoodie_price = 30;
    int saree_price = 35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_wash_item);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        connection();
        shirtFunction();
        pantFunction();
        hoodieFunction();
        sareeFunction();
        onclick();
    }



    private void onclick() {
        back.setOnClickListener(v ->
                startActivity(new Intent(Select_wash_item.this, home_page.class))
        );


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item_total == 0){
                    Toast.makeText(Select_wash_item.this, "Please select at least one item", Toast.LENGTH_LONG).show();
                } else {
                    String priceString = Integer.toString(total_price);
                    Cart.price=priceString;
               String itemString=Integer.toString(item_total);
               Cart.item=itemString;

                   startActivity(new Intent(Select_wash_item.this,Cart.class));
                }
            }
        });

    }

    private void shirtFunction() {
        shirt_plus.setOnClickListener(v -> {
            shirt_total++;
            shirt_item.setText(String.valueOf(shirt_total));
            updateTotal();
        });

        shirt_minus.setOnClickListener(v -> {
            if (shirt_total > 0) shirt_total--;
            shirt_item.setText(String.valueOf(shirt_total));
            updateTotal();
        });
    }

    private void pantFunction() {
        pant_plus.setOnClickListener(v -> {
            pant_total++;
            pant_item.setText(String.valueOf(pant_total));
            updateTotal();
        });

        pant_minus.setOnClickListener(v -> {
            if (pant_total > 0) pant_total--;
            pant_item.setText(String.valueOf(pant_total));
            updateTotal();
        });
    }

    private void hoodieFunction() {
        hoodie_plus.setOnClickListener(v -> {
            hoodie_total++;
            hoodie_item.setText(String.valueOf(hoodie_total));
            updateTotal();
        });

        hoodie_minus.setOnClickListener(v -> {
            if (hoodie_total > 0) hoodie_total--;
            hoodie_item.setText(String.valueOf(hoodie_total));
            updateTotal();
        });
    }

    private void sareeFunction() {
        saree_plus.setOnClickListener(v -> {
            saree_total++;
            saree_item.setText(String.valueOf(saree_total));
            updateTotal();
        });

        saree_minus.setOnClickListener(v -> {
            if (saree_total > 0) saree_total--;
            saree_item.setText(String.valueOf(saree_total));
            updateTotal();
        });
    }

    // 🧾 Total update function
    private void updateTotal() {
        item_total = shirt_total + pant_total + hoodie_total + saree_total;
        total_price = (shirt_total * shirt_price) +
                (pant_total * pant_price) +
                (hoodie_total * hoodie_price) +
                (saree_total * saree_price);

        total_item.setText(String.valueOf(item_total+" items"));
        total_cost.setText(total_price + " tk");
    }

    private void connection() {
        total_cost = findViewById(R.id.total_cost);
        total_item = findViewById(R.id.total_item);

        saree_minus = findViewById(R.id.saree_minus);
        saree_item = findViewById(R.id.saree_item);
        saree_plus = findViewById(R.id.saree_plus);

        hoodie_minus = findViewById(R.id.hoodie_minus);
        hoodie_item = findViewById(R.id.hoodie_item);
        hoodie_plus = findViewById(R.id.hoodie_plus);

        shirt_minus = findViewById(R.id.shirt_minus);
        shirt_item = findViewById(R.id.shirt_item);
        shirt_plus = findViewById(R.id.shirt_plus);

        pant_minus = findViewById(R.id.pant_minus);
        pant_item = findViewById(R.id.pant_item);
        pant_plus = findViewById(R.id.pant_plus);

        back = findViewById(R.id.back);

        cart=findViewById(R.id.cart);
    }
}
