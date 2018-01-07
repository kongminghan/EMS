package com.example.minghan.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListUser extends AppCompatActivity {

    private Recycler_View_Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<User> data;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        data = fill_with_data();

//        DeleteUser user = new DeleteUser();
//        user.userData(data);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(ListUser.this);
        recyclerView.setLayoutManager(layoutManager);

        //Finally initializing our adapter
        adapter = new Recycler_View_Adapter(data,ListUser.this);
        adapter.notifyDataSetChanged();
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //configure pull to refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public ArrayList<User> fill_with_data() {
        final ArrayList<User> user = new ArrayList<User>();
        final ProgressDialog progressDialog = new ProgressDialog(ListUser.this);
        progressDialog.setMessage("Reading data from server...");
        progressDialog.setCancelable(false);

        if (AppStatus.getInstance(this).isOnline()) {
            progressDialog.show();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray array;
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        array = jsonResponse.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                if (object.getString("gender").equals("male")) {
                                    user.add(new User(object.getString("name"), object.getString("emp_no"), object.getString("email"), object.getString("gender"), object.getString("ic"), object.getString("username"), object.getString("date"),  R.drawable.male));
                                } else {
                                    user.add(new User(object.getString("name"), object.getString("emp_no"), object.getString("email"), object.getString("gender"), object.getString("ic"), object.getString("username"), object.getString("date"), R.drawable.female));
                                }
                            }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            };
            ListRequest listRequest = new ListRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(ListUser.this);
            queue.add(listRequest);
        }
        else{
            progressDialog.dismiss();
            Context context = getApplicationContext();
            CharSequence text = "Network connection is needed!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        return user;
    }
}