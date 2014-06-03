package com.dpt.treader3.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class TBaseJsonObjectRequest extends JsonObjectRequest {

    public TBaseJsonObjectRequest(int method, String url, JSONObject jsonRequest,
            Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        return headers;
    }
    /**
     * 删除的时候要根据它
     */
    @Override
    public Object getTag() {
        return super.getTag();
    }
}
