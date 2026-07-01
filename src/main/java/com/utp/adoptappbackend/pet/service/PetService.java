package com.utp.adoptappbackend.pet.service;

import com.utp.adoptappbackend.common.model.PageResponse;
import com.utp.adoptappbackend.common.model.enumeration.Sex;
import com.utp.adoptappbackend.common.model.enumeration.Size;
import com.utp.adoptappbackend.common.model.enumeration.Species;
import com.utp.adoptappbackend.common.model.enumeration.Status;
import com.utp.adoptappbackend.pet.model.dto.PetRequest;
import com.utp.adoptappbackend.pet.model.dto.PetResponse;

import java.util.List;

public interface PetService {
    List<PetResponse> findAll();
    PetResponse findById(Long id);
    PetResponse create(PetRequest request);
    PetResponse update(Long id, PetRequest request);
    void delete(Long id);
    List<PetResponse> findByUserId(Long userId);
    PageResponse<PetResponse> findFiltered(Status status, List<Species> species, Size size, Sex sex, String age, String search, int page, int sizeVal);
}
