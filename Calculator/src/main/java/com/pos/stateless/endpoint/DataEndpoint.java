package com.pos.stateless.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class DataEndpoint {

    private static final String NAMESPACE_URI = "http://com.pos.stateless/data";

/*    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRow")
    @ResponsePayload
    public RowResponse(@RequestPayload RowAdd input) {
*//*        AddResponse result = new AddResponse();

        result.setResult(input.getParam1().add(input.getParam2()));

        return result;*//*
    }*/
}
