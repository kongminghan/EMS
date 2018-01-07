package com.example.minghan.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText etEmp;
    EditText etIc;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etEmp = (EditText)findViewById(R.id.add_emp);
        etIc = (EditText)findViewById(R.id.add_ic);
        Button btn_add = (Button)findViewById(R.id.btn_add);
        Spinner spUserType = (Spinner)findViewById(R.id.add_userType);
        spUserType.setOnItemSelectedListener(this);

        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final ProgressDialog progressDialog = new ProgressDialog(AddUser.this);
                progressDialog.setMessage("Storing data");
                progressDialog.setCancelable(false);

                String emp_no = etEmp.getText().toString();
                String ic_no = etIc.getText().toString();

                //check if connection is available
                if (AppStatus.getInstance(AddUser.this).isOnline()){
                    if(validate()){
                        progressDialog.show();
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsResponse = new JSONObject(response);
                                    boolean success = jsResponse.getBoolean("success");

                                    if (success) {
                                        etEmp.setText("");
                                        etIc.setText("");
                                        progressDialog.dismiss();
                                        Context context = getApplicationContext();
                                        CharSequence text = "New employee is added";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    } else {
                                        String errMsg = jsResponse.getString("errMessage");
                                        etIc.setText("");
                                        etEmp.setText("");
                                        progressDialog.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(AddUser.this);
                                        builder.setMessage(errMsg)
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        AddUserRequest addUserRequest = new AddUserRequest(emp_no, ic_no, userType, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(AddUser.this);
                        queue.add(addUserRequest);
                    }
                }
                else{
                    progressDialog.dismiss();
                    Context context = getApplicationContext();
                    CharSequence text = "Network connection is needed!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View v, int position, long id){
        //get usertype from spinner
        userType = parentView.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> parentView){ }

    public boolean validate() {
        boolean valid = true;

        String emp_no = etEmp.getText().toString();
        String ic_no = etIc.getText().toString();
        String pattern = "[B]\\d{3}";
        String pattern2 = "\\d{12}";
        if (emp_no.isEmpty() || !emp_no.matches(pattern)) {
            etEmp.setError("Invalid employee number. Eg: B100");
            valid = false;
        } else {
            etEmp.setError(null);
        }

        if (ic_no.isEmpty() || !ic_no.matches(pattern2)) {
            etIc.setError("required IC format: 941111081111");
            valid = false;
        } else {
            etIc.setError(null);
        }

        return valid;
    }
}
