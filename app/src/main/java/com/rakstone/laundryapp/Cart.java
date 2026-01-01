package com.rakstone.laundryapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class Cart extends AppCompatActivity {

public static String price="";
public static String email="";
public static String item="";
    ImageView cart_myprofile,cart_home,cart_order;


    Button confirm_button;
    TextView total_coast;
    TextView delivery_charge;
    TextView coast;
    LinearLayout express,normal;



    int delivery = 0; // delivery charge
    int total_price = Integer.parseInt(price); // select_wash_item থেকে আসা price

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        connection();
        coast.setText(total_price+" tk");
        onclick();



    }
//  function
    private void onclick(){
        cart_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cart.this, home_page.class));
            }
        });

        cart_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cart.this, My_profile.class));
            }
        });


        cart_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cart.this, Order_history.class));
            }
        });



        normal.setOnClickListener(view -> {
            delivery = 30;
            delivery_charge.setText(delivery + " tk"); // delivery select হতেই দেখাবে
            int total=delivery+total_price;
            total_coast.setText(total+" tk");
        });



        express.setOnClickListener(view -> {
            delivery = 80;
            delivery_charge.setText(delivery + " tk"); // delivery select হতেই দেখাবে
            int total=delivery+total_price;
            total_coast.setText(total+" tk");
        });

        confirm_button.setOnClickListener(view -> {
            if (delivery == 0) {
                Toast.makeText(Cart.this, "Please select a delivery option", Toast.LENGTH_LONG).show();
            } else {

                int final_total = total_price + delivery;
//                total_coast.setText(final_total + " tk"); // total_price + delivery

                String mail=email;
                int price_int=Integer.parseInt(item);

                String url="http://172.20.10.10/apps/order.php?email="+mail+"&cost="+final_total+"&item="+price_int;





                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        new AlertDialog.Builder(Cart.this)
                                .setTitle("server response")
                                .setMessage(response)
                                .show();

//                            My_profile.my_profile_email=email;
//                            My_profile.my_profile_name=name;
//                            My_profile.my_profile_phone=mobile;


                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(Cart.this);
                requestQueue.add(stringRequest);




//
//                // Order Confirm dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
//                builder.setTitle("Order Confirmed!");
//                builder.setMessage("Your order has been placed successfully.\nTotal: " + final_total + " tk");
//                builder.setPositiveButton("OK", (dialog, which) -> {
//                    dialog.dismiss();
//                    // Confirm হলে অন্য page এ নাও
//                    Intent intent = new Intent(Cart.this, Order_history.class);
//                    startActivity(intent);
//                    finish(); // আগের page back এ না যাওয়ার জন্য
//                });
//                builder.setCancelable(false);
//                builder.show();
            }
        });


    }
    private void connection  (){
        cart_home=findViewById(R.id.cart_home);
        cart_myprofile=findViewById(R.id.cart_myprofile);
        cart_order=findViewById(R.id.cart_order);


        total_coast=findViewById(R.id.total_coast);
        delivery_charge=findViewById(R.id.delivery_charge);
        coast=findViewById(R.id.coast);


        normal=findViewById(R.id.normal);
        express=findViewById(R.id.express);
        confirm_button=findViewById(R.id.confirm_button);



    }
//



}