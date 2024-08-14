package com.example.nerdysoft_java_test.facade;

import com.example.nerdysoft_java_test.dto.request.ApiRequest;
import com.example.nerdysoft_java_test.dto.response.ApiResponse;

import java.util.Collection;
import java.util.List;

public interface CrudFacade<REQ extends ApiRequest, RES extends ApiResponse> {
    void create(REQ entity);

    void update(Long id, REQ entity);

    void delete(Long id);

    RES findById(Long id);

    List<RES> findAll();
}
