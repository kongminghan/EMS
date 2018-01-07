package com.example.minghan.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginRegister extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        //set shared preference
        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        final SharedPreferences.Editor editor = settings.edit();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(LoginRegister.this, RegisterActivity.class);
                LoginRegister.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoginRegister.this);
                progressDialog.setMessage("Authenticating...");
                progressDialog.setCancelable(false);

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if (AppStatus.getInstance(LoginRegister.this).isOnline()){
                    if(validate()){
                        progressDialog.show();
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        String name = jsonResponse.getString("name");
                                        //int age = jsonResponse.getInt("age");
                                        //String username = jsonResponse.getString("username");
                                        //String password = jsonResponse.getString("password");
                                        String email = jsonResponse.getString("email");
                                        String userType = jsonResponse.getString("userType");
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.putString("userType", userType);
                                        editor.commit();

                                        Intent intentToNav = new Intent(LoginRegister.this, MainActivity.class);
                                        LoginRegister.this.startActivity(intentToNav);
                                    } else {
                                        progressDialog.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
                                        builder.setMessage("Login failed")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                }
                                catch(JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(LoginRegister.this);
                        queue.add(loginRequest);
                    }
                }
                else{
                    progressDialog.dismiss();
                    Context context = getApplicationContext();
                    CharSequence text = "Network connection is needed";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || username.length() < 3 || username.length() > 20) {
            etUsername.setError("between 3 and 10 characters");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        if (password.isEmpty() || password.length() < 3 || password.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

}
