package com.example.minghan.ems;

/**
 * Created by MingHan on 6/5/2016.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by MingHan on 3/5/2016.
 */
public class ListRequest extends StringRequest {
    private static final String LIST_REQUEST_URL = "http://minghan1219.site88.net/ListUser.php";

    public ListRequest (Response.Listener<String> listener){
        super(Request.Method.POST, LIST_REQUEST_URL, listener, null);
    }
}

