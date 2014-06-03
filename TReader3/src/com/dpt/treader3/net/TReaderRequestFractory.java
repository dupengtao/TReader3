package com.dpt.treader3.net;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.dpt.tbase.app.net.RequestFractory;
@SuppressWarnings("rawtypes")
public class TReaderRequestFractory implements RequestFractory {

    @Override
    public Request produceStringRequest(int method,String url,Response.Listener<String> listener,Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(method,url,listener, errorListener);
        return stringRequest;
    }

    @Override
    public Request produceJsonRequest(int method,String url,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener) {
        TBaseJsonObjectRequest request = new TBaseJsonObjectRequest(method,url, null,listener,errorListener);
        return request;
    }
}
