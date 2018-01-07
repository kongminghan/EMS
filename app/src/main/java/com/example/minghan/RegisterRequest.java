package com.example.minghan.ems;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MingHan on 24/4/2016.
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://minghan1219.site88.net/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, int age, String username, String password, String email, String emp_no,String ic_no, String gender, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params  = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("age", age + "");
        params.put("password", password);
        params.put("email", email);
        params.put("emp_no", emp_no);
        params.put("ic_no", ic_no);
        params.put("gender", gender);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
