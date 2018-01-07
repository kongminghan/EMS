package com.example.minghan.ems;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MingHan on 3/5/2016.
 */
public class SalaryRequest extends StringRequest {
    private static final String SALARY_REQUEST_URL = "http://minghan1219.site88.net/Salary.php";
    private Map<String, String> params;

    public SalaryRequest (String username, String month,  Response.Listener<String> listener){
        super(Request.Method.POST, SALARY_REQUEST_URL, listener, null);
        params  = new HashMap<>();
        params.put("username", username);
        params.put("month", month);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
