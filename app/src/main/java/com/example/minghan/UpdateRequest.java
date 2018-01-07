package com.example.minghan.ems;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MingHan on 10/5/2016.
 */
public class UpdateRequest extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://minghan1219.site88.net/UpdateUser.php";
    private Map<String, String> params;

    public UpdateRequest(String name, String username, String email, String date, String ic_no,String emp_no, Response.Listener<String> listener){
        super(Request.Method.POST, UPDATE_REQUEST_URL, listener, null);
        params  = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("email", email);
        params.put("date", date);
        params.put("emp_no", emp_no);
        params.put("ic_no", ic_no);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
