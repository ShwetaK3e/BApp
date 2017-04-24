package com.bzyness.bzyness.services;

import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.models.ServerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Pervacio on 4/4/2017.
 */

public class JackSonRequestError {

    private ObjectMapper mMapper = new ObjectMapper();
    private String jsonString;


    public JackSonRequestError(String jsonString) throws IOException {
       this.jsonString=jsonString;
        BaseActivity.error=mMapper.readValue(jsonString,ServerResponse.class);
    }


}
