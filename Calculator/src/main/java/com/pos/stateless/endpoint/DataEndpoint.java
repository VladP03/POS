package com.pos.stateless.endpoint;

import com.pos.stateless.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stateless.pos.com.data.DeleteEntityRequest;
import stateless.pos.com.data.DeleteEntityResponse;
import stateless.pos.com.data.GetEntityRequest;
import stateless.pos.com.data.GetEntityResponse;
import stateless.pos.com.data.PostEntityRequest;
import stateless.pos.com.data.PostEntityResponse;
import stateless.pos.com.data.PutEntityRequest;
import stateless.pos.com.data.PutEntityResponse;

@Endpoint
@RequiredArgsConstructor
public class DataEndpoint {

    private static final String NAMESPACE_URI = "http://com.pos.stateless/data";

    private final DataService dataService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postEntityRequest")
    @ResponsePayload
    public PostEntityResponse postEntity(@RequestPayload PostEntityRequest input) {
        return dataService.postEntity(input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "putEntityRequest")
    @ResponsePayload
    public PutEntityResponse putEntity(@RequestPayload PutEntityRequest input) {
        return dataService.putEntity(input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEntityRequest")
    @ResponsePayload
    public GetEntityResponse getEntity(@RequestPayload GetEntityRequest input) {
        return dataService.getEntity(input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEntityRequest")
    @ResponsePayload
    public DeleteEntityResponse deleteEntity(@RequestPayload DeleteEntityRequest input) {
        return dataService.deleteEntity(input);
    }
}
