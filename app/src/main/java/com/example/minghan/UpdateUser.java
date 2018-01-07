package com.example.minghan.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUser extends AppCompatActivity {
    EditText u_tvName;
    EditText u_tvUsername;
    Spinner u_tvGender;
    EditText u_tvEmail;
    EditText u_tvDate;
    EditText u_tvIC;
    EditText u_tvEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        u_tvName = (EditText)findViewById(R.id.updName);
        u_tvUsername = (EditText)findViewById(R.id.updUsername);
        u_tvGender = (Spinner)findViewById(R.id.updGender);
        u_tvEmail = (EditText)findViewById(R.id.updEmail);
        u_tvDate = (EditText)findViewById(R.id.updEdate);
        u_tvIC = (EditText)findViewById(R.id.updIC);
        u_tvEmp = (EditText)findViewById(R.id.updEmp);
        final Button btn_update = (Button)findViewById(R.id.b_Update);

        Intent intent = getIntent();

        u_tvName.setText(intent.getStringExtra("name"));
        u_tvUsername.setText(intent.getStringExtra("username"));
        u_tvEmail.setText(intent.getStringExtra("email"));
        u_tvDate.setText(intent.getStringExtra("date"));
        u_tvIC.setText(intent.getStringExtra("ic"));
        u_tvEmp.setText(intent.getStringExtra("emp_no"));

        String compareValue = intent.getStringExtra("gender");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u_tvGender.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            u_tvGender.setSelection(spinnerPosition);
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = u_tvName.getText().toString();
                final String username = u_tvUsername.getText().toString();
                final String email = u_tvEmail.getText().toString();
                final String date = u_tvDate.getText().toString();
                final String ic = u_tvIC.getText().toString();
                final String emp_no = u_tvEmp.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(UpdateUser.this);
                progressDialog.setMessage("Authenticating...");
                progressDialog.setCancelable(false);

                if (AppStatus.getInstance(UpdateUser.this).isOnline()){
                    if(validate()){
                        progressDialog.show();
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(UpdateUser.this, ListUser.class);
                                        startActivity(intent);
                                    } else {
                                        progressDialog.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUser.this);
                                        builder.setMessage("Update failed")
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
                        UpdateRequest updateRequest = new UpdateRequest(name, username, email, date, ic, emp_no, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(UpdateUser.this);
                        queue.add(updateRequest);
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

    public boolean validate(){
        boolean valid = true;

        String name = u_tvName.getText().toString();
        String username = u_tvUsername.getText().toString();
        String email = u_tvEmail.getText().toString().trim();
        //String date = u_tvDate.getText().toString();
        String ic = u_tvIC.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (username.isEmpty() || username.length() < 3 || username.length() > 20) {
            u_tvUsername.setError("between 3 and 20 characters");
            valid = false;
        } else {
            u_tvUsername.setError(null);
        }

        if (name.isEmpty() || name.length() < 3) {
            u_tvName.setError("at least 3 characters");
            valid = false;
        } else {
            u_tvName.setError(null);
        }

        if (email.isEmpty() || !email.matches(emailPattern)) {
            u_tvEmail.setError("invalid email address");
            valid = false;
        } else {
            u_tvEmail.setError(null);
        }

        if (ic.isEmpty() || ic.length() < 3) {
            u_tvIC.setError("required format Eg: 890607085544");
            valid = false;
        } else {
            u_tvIC.setError(null);
        }
        return valid;
    }
}
