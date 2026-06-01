package com.utp.adoptappbackend.adoption.service;

import com.utp.adoptappbackend.adoption.model.dto.AdoptionRequest;
import com.utp.adoptappbackend.adoption.model.dto.AdoptionResponse;
import com.utp.adoptappbackend.common.model.PageResponse;

public interface AdoptionService {
    PageResponse<AdoptionResponse> findFiltered(String search, int page, int size);
    AdoptionResponse create(AdoptionRequest request);
}
