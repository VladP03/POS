package com.pos.stateless.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stateless.pos.com.calculator.*;

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


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "decreaseRequest")
    @ResponsePayload
    public DecreaseResponse decrease(@RequestPayload DecreaseRequest input) {
        DecreaseResponse result = new DecreaseResponse();

        result.setResult(input.getParam1().subtract(input.getParam2()));

        return result;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "divisionRequest")
    @ResponsePayload
    public DivisionResponse divide(@RequestPayload DivisionRequest input) {
        DivisionResponse result = new DivisionResponse();

        result.setResult(input.getParam1().divide(input.getParam2()));

        return result;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "multiplicationRequest")
    @ResponsePayload
    public MultiplicationResponse divide(@RequestPayload MultiplicationRequest input) {
        MultiplicationResponse result = new MultiplicationResponse();

        result.setResult(input.getParam1().multiply(input.getParam2()));

        return result;
    }
}
