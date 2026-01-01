package com.rakstone.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_history extends AppCompatActivity {

    public static String email = "";
    LinearLayout contentLayout;
    ImageView order_myprofile,order_home;
    ArrayList<HashMap<String, String>> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_history);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contentLayout = findViewById(R.id.contentLayout);
        order_home=findViewById(R.id.order_home);
        order_myprofile=findViewById(R.id.order_myprofile);

        // Load orders from server
        String mail = email;
        String url = "http://172.20.10.10/apps/order_history.php?email=" + mail;

        order_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Order_history.this, home_page.class));
            }
        });

        order_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Order_history.this, My_profile.class));
            }
        });


        loadOrders(url);
    }

    private void loadOrders(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        boolean success = jsonObject.getBoolean("success");

                        // Clear previous list before adding new data
                        orderList.clear();

                        if(success){
                            JSONArray ordersArray = jsonObject.getJSONArray("orders");

                            for (int i = 0; i < ordersArray.length(); i++) {
                                JSONObject obj = ordersArray.getJSONObject(i);

                                String id = obj.getString("id");
                                String history = obj.getString("history");
                                String cost = obj.getString("cost");
                                String item = obj.getString("item");

                                HashMap<String, String> map = new HashMap<>();
                                map.put("id", id);
                                map.put("history", history);
                                map.put("cost", cost);
                                map.put("item", item);

                                // Add to the list
                                orderList.add(map);
                            }

                            // Inflate dynamically
                            inflateOrders();

                        } else {
                            String message = jsonObject.getString("message");
                            Toast.makeText(Order_history.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Order_history.this, "JSON Parse Error", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Toast.makeText(Order_history.this, "Volley Error", Toast.LENGTH_SHORT).show();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void inflateOrders() {
        LayoutInflater inflater = LayoutInflater.from(this);

        contentLayout.removeAllViews(); // Clear previous
        for (HashMap<String, String> order : orderList) {
            View view = inflater.inflate(R.layout.order_single, contentLayout, false);

            TextView orderName = view.findViewById(R.id.orderName);
            TextView item = view.findViewById(R.id.item);
            TextView cost = view.findViewById(R.id.cost);
            Button details = view.findViewById(R.id.details);

            String history = order.get("history").toLowerCase();

            // Texts
            orderName.setText(order.get("name"));
            item.setText(order.get("item") + " items");
            cost.setText(order.get("cost") + " tk");

            // Button text + button background color based on history
            switch (history) {
                case "pending":
                    details.setText("Pending");
                    details.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light)); // yellow
                    break;
                case "delivered":
                    details.setText("Delivered");
                    details.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light)); // green
                    break;
                case "ordered":
                default:
                    details.setText("Ordered");
                    details.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light)); // red
                    break;
            }

            // Click listener
            details.setOnClickListener(v ->
                    Toast.makeText(Order_history.this,
                            "Order ID: " + order.get("id") +
                                    "\nStatus: " + order.get("history"),
                            Toast.LENGTH_SHORT).show()
            );

            contentLayout.addView(view);
        }
    }




}
