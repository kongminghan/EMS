package com.example.minghan.ems;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MingHan on 4/5/2016.
 */
public class AddUserRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://minghan1219.site88.net/AddUser.php";
    private Map<String, String> params;

    public AddUserRequest(String emp_no, String ic_no, String userType, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params  = new HashMap<>();
        params.put("emp_no", emp_no);
        params.put("ic_no", ic_no);
        params.put("userType", userType);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
