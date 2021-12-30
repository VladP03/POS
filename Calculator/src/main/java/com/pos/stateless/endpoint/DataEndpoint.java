package com.pos.stateless.endpoint;

import com.pos.stateless.repostitory.Data;
import com.pos.stateless.repostitory.DataRepository;
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

import java.math.BigInteger;
import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class DataEndpoint {

    private static final String NAMESPACE_URI = "http://com.pos.stateless/data";

    private final DataRepository dataRepository;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postEntityRequest")
    @ResponsePayload
    public PostEntityResponse postEntity(@RequestPayload PostEntityRequest input) {
        PostEntityResponse result = new PostEntityResponse();

        Data data = dataRepository.save(Data.builder()
                .name(input.getName())
                .doubleValue(input.getDoubleValue())
                .integerValue(input.getIntegerValue().intValue())
                .build());

        result.setID(data.getId());
        result.setName(data.getName());
        result.setDoubleValue(data.getDoubleValue());
        result.setIntegerValue(BigInteger.valueOf(data.getIntegerValue()));

        return result;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "putEntityRequest")
    @ResponsePayload
    public PutEntityResponse putEntity(@RequestPayload PutEntityRequest input) {
        PutEntityResponse result = new PutEntityResponse();

        Data data = dataRepository.save(Data.builder()
                .id(input.getID())
                .name(input.getName())
                .doubleValue(input.getDoubleValue())
                .integerValue(input.getIntegerValue().intValue())
                .build());

        result.setID(data.getId());
        result.setName(data.getName());
        result.setDoubleValue(data.getDoubleValue());
        result.setIntegerValue(BigInteger.valueOf(data.getIntegerValue()));

        return result;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEntityRequest")
    @ResponsePayload
    public GetEntityResponse getEntity(@RequestPayload GetEntityRequest input) {
        GetEntityResponse result = new GetEntityResponse();

        Optional<Data> data = dataRepository.findById(input.getID());

        if (data.isPresent()) {
            result.setID(data.get().getId());
            result.setName(data.get().getName());
            result.setDoubleValue(data.get().getDoubleValue());
            result.setIntegerValue(BigInteger.valueOf(data.get().getIntegerValue()));

            return result;
        }

        throw new RuntimeException("id not found");
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEntityRequest")
    @ResponsePayload
    public void deleteEntity(@RequestPayload DeleteEntityRequest input) {
        DeleteEntityResponse result = new DeleteEntityResponse();

        Optional<Data> data = dataRepository.findById(input.getID());

        if (data.isPresent()) {
            dataRepository.delete(data.get());
        } else {
            throw new RuntimeException("id not found");
        }
    }
}
