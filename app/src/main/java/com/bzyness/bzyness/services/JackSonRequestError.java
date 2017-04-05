package com.bzyness.bzyness.services;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.models.ServerError;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Pervacio on 4/4/2017.
 */

public class JackSonRequestError {

    private ObjectMapper mMapper = new ObjectMapper();
    private String jsonString;


    public JackSonRequestError(String jsonString) throws IOException {
       this.jsonString=jsonString;
        BaseActivity.error=mMapper.readValue(jsonString,ServerError.class);
    }


}
