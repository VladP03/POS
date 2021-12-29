package com.pos.stateless.endpoint;

import com.pos.stateless.repostitory.Data;
import com.pos.stateless.repostitory.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stateless.pos.com.data.PostEntityRequest;
import stateless.pos.com.data.PostEntityResponse;

@Endpoint
@RequiredArgsConstructor
public class DataEndpoint {

    private final DataRepository dataRepository;


    private static final String NAMESPACE_URI = "http://com.pos.stateless/data";


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postEntityRequest")
    @ResponsePayload
    public PostEntityResponse insertLine(@RequestPayload PostEntityRequest input) {
        PostEntityResponse result = new PostEntityResponse();

        result.setName(input.getName());
        result.setDoubleValue(input.getDoubleValue());
        result.setIntegerValue(input.getIntegerValue());

        Data data = Data.builder()
                .name(input.getName())
                .doubleValue(input.getDoubleValue())
                .integerValue(input.getIntegerValue().intValue())
                .build();

        dataRepository.save(data);

        return result;
    }
}
