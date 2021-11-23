package com.pos.stateless.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stateless.pos.com.calculator.AddRequest;
import stateless.pos.com.calculator.AddResponse;

@Endpoint
public class CalculatorEndpoint {

    private static final String NAMESPACE_URI = "http://com.pos.stateless/calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
    @ResponsePayload
    public AddResponse add(@RequestPayload AddRequest input) {
        AddResponse result = new AddResponse();

        result.setResult(input.getParam1().add(input.getParam2()));

        return result;
    }
}
