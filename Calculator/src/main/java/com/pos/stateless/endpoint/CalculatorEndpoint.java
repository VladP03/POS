package com.pos.stateless.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stateless.pos.com.calculator.AddRequest;

@Endpoint
public class CalculatorEndpoint {

    private static final String NAMESPACE_URI = "http://com.pos.stateless/calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
    @ResponsePayload
    public AddRequest add(@RequestPayload AddRequest input) {
        return null;
    }
}
