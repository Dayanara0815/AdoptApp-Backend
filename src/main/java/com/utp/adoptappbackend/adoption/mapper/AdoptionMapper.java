package com.utp.adoptappbackend.adoption.mapper;

import com.utp.adoptappbackend.adoption.model.Adoption;
import com.utp.adoptappbackend.adoption.model.dto.AdoptionResponse;
import com.utp.adoptappbackend.pet.mapper.PetMapper;
import com.utp.adoptappbackend.user.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PetMapper.class, UserMapper.class})
public interface AdoptionMapper {
    AdoptionResponse toResponse(Adoption entity);
}
