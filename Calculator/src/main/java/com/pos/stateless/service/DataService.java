package com.pos.stateless.service;

import com.pos.stateless.repostitory.Data;
import com.pos.stateless.repostitory.DataRepository;
import com.pos.stateless.service.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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

@Component
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;


    public PostEntityResponse postEntity(PostEntityRequest input) {
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

        result.setResultType("Created");

        return result;
    }


    public PutEntityResponse putEntity(PutEntityRequest input) {
        PutEntityResponse result = new PutEntityResponse();

        Data data = dataRepository.save(Data.builder()
                .id(input.getID())
                .name(input.getName())
                .doubleValue(input.getDoubleValue())
                .integerValue(input.getIntegerValue().intValue())
                .build());

        result.setResultType("No Content");

        return result;
    }


    public GetEntityResponse getEntity(GetEntityRequest input) {
        GetEntityResponse result = new GetEntityResponse();

        Optional<Data> data = dataRepository.findById(input.getID());

        if (data.isPresent()) {
            result.setID(data.get().getId());
            result.setName(data.get().getName());
            result.setDoubleValue(data.get().getDoubleValue());
            result.setIntegerValue(BigInteger.valueOf(data.get().getIntegerValue()));

            result.setResultType("Ok");

            return result;
        }

        throw new DataNotFoundException(String.format("id %x not found", input.getID()));
    }


    public DeleteEntityResponse deleteEntity(DeleteEntityRequest input) {
        DeleteEntityResponse deleteEntityResponse = new DeleteEntityResponse();

        Optional<Data> data = dataRepository.findById(input.getID());

        if (data.isPresent()) {
            dataRepository.delete(data.get());

            deleteEntityResponse.setResultType("No Content");

            return deleteEntityResponse;
        } else {
            throw new DataNotFoundException(String.format("id %x not found", input.getID()));
        }
    }
}
