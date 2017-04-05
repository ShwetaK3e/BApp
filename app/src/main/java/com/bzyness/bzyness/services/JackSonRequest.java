package com.bzyness.bzyness.services;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.bzyness.bzyness.activity.RegisterActivity;
import com.bzyness.bzyness.models.ServerError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Pervacio on 4/4/2017.
 */

public class JackSonRequest<K,T> extends Request<T> {

    private ObjectMapper mMapper = new ObjectMapper();

    private static final String PROTOCOL_CHARSET = "utf-8";


    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private final Response.Listener<T> mListener;
    private final K mRequestBody;
    private final Class<T> mClass;
    private final Map<String, String> mHeaders;




    public JackSonRequest(int method, String url, K requestBody,
                              Response.ErrorListener errorListener, Response.Listener<T> listener,
                              Map<String, String> headers,  Class<T> claz) {
        super(method, url, errorListener);

        this.mListener = listener;
        this.mRequestBody = requestBody;
        this.mHeaders = headers;
        this.mClass = claz;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        int statusCode=response.statusCode;
        String jsonString = new String(response.data);
        if(statusCode==200) {
            try {
                T result = mMapper.readValue(jsonString, mClass);
                return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                new JackSonRequestError(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
         mListener.onResponse(response);
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mRequestBody==null?null:mMapper.writeValueAsBytes(mRequestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders==null?super.getHeaders():mHeaders;
    }
}
