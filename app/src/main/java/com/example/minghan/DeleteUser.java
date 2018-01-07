//package com.example.minghan.ems;
//
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class DeleteUser extends AppCompatActivity {
//    private ArrayList<User> user;
//    private DeleteAdapter adapter;
//    private RecyclerView recyclerView;
//    private LinearLayoutManager layoutManager;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_user);
//
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(DeleteUser.this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        //Finally initializing our adapter
//        adapter = new DeleteAdapter(user,DeleteUser.this);
//        adapter.notifyDataSetChanged();
//        //Adding adapter to recyclerview
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//        //configure pull to refresh
//        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Refresh items
//                refreshItems();
//            }
//        });
//    }
//
//    void refreshItems() {
//        // Load items
//        // ...
//
//        // Load complete
//        onItemsLoadComplete();
//    }
//
//    void onItemsLoadComplete() {
//        // Update the adapter and notify data set changed
//        // ...
//
//        // Stop refresh animation
//        mSwipeRefreshLayout.setRefreshing(false);
//    }
//
//    public void userData(ArrayList<User> user){
//        this.user = user;
//    }
//}
