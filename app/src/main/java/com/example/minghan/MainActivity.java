package com.example.minghan.ems;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView = null;
    Toolbar toolbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);

        NavigationView navigationHeaderView = (NavigationView)findViewById(R.id.nav_view);
        View hView = navigationHeaderView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.headUsername);
        TextView nav_email = (TextView)hView.findViewById(R.id.headEmail);
//        final TextView headUsername = (TextView) findViewById(R.id.headUsername);
//        final TextView headEmail = (TextView) findViewById(R.id.headEmail);

        Menu menu =navigationHeaderView.getMenu();
        MenuItem target = menu.findItem(R.id.nav_addUser);
        MenuItem target1 = menu.findItem(R.id.nav_updateUser);
        MenuItem target2 = menu.findItem(R.id.nav_deleteUser);



        //Intent intentNav = getIntent();
        //String name = intentNav.getStringExtra("name");
        //username = intentNav.getStringExtra("username");
        //int age = intentNav.getIntExtra("age", -1);
        //String email = intentNav.getStringExtra("email");
        //String userType = intentNav.getStringExtra("userType");

        //set the username and email in the navigation drawer
        nav_user.setText(sharedPreferences.getString("name", null));
        nav_email.setText(sharedPreferences.getString("email", null));

        if(!sharedPreferences.getString("userType", null).equals("admin")){
//            Menu menu =navigationHeaderView.getMenu();
//            MenuItem target = menu.findItem(R.id.nav_addUser);
//            MenuItem target1 = menu.findItem(R.id.nav_updateUser);
//            MenuItem target2 = menu.findItem(R.id.nav_deleteUser);
            target.setVisible(false);
            target1.setVisible(false);
            target2.setVisible(false);
////            navigationHeaderView.getMenu().findItem(R.id.nav_addUser).setVisible(false);
////            navigationHeaderView.getMenu().findItem(R.id.nav_updateUser).setVisible(false);
////            navigationHeaderView.getMenu().findItem(R.id.nav_deleteUser).setVisible(false);
        }

        //Set the fragment initially
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //Set the fragment initially
            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_salary) {
            //start salary activity
            Intent intent_salary = new Intent(MainActivity.this, SalaryActivity.class);
            startActivity(intent_salary);

        } else if (id == R.id.nav_addUser) {
            Intent intent = new Intent(MainActivity.this, AddUser.class);
            startActivity(intent);
        } else if (id == R.id.nav_updateUser) {
            Intent intent = new Intent(MainActivity.this, ListUser.class);
            startActivity(intent);
        } else if (id == R.id.nav_deleteUser) {
//            Intent intent = new Intent(MainActivity.this, DeleteUser.class);
//            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
