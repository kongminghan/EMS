package com.example.minghan.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SalaryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tvMonth;
    TextView tvAmount;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        tvAmount = (TextView) findViewById(R.id.cardAmount);
        tvMonth = (TextView) findViewById(R.id.cardTitle);

        //get spinner id
        Spinner spinner = (Spinner)findViewById(R.id.month_spinner);

        //add event for spinner
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View v, int position, long id){
        final ProgressDialog progressDialog = new ProgressDialog(SalaryActivity.this);
        progressDialog.setMessage("Reading data from server...");
        progressDialog.setCancelable(false);
        String month = parentView.getItemAtPosition(position).toString();
        if(position==0){
            tvAmount.setText("Net salary: RM");
            tvMonth.setText("Monthly salary");
        }
        else{
            progressDialog.show();
            if (AppStatus.getInstance(this).isOnline()){

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsResponse = new JSONObject(response);
                            boolean found = jsResponse.getBoolean("found");

                            if (found) {
                                String month = jsResponse.getString("date");
                                String salary = jsResponse.getString("amount");
                                tvMonth.setText("Salary of "+ month);
                                tvAmount.setText("Net salary: RM "+ salary);
                                progressDialog.dismiss();
                            } else {
                                tvAmount.setText("Net salary: RM");
                                tvMonth.setText("Monthly salary");
                                progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(SalaryActivity.this);
                                builder.setMessage("No data has found")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                SalaryRequest salaryRequest = new SalaryRequest(username, month, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SalaryActivity.this);
                queue.add(salaryRequest);
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
    }
    public void onNothingSelected(AdapterView<?> parentView){ }
}
