package com.utp.adoptappbackend.user.mapper;

import com.utp.adoptappbackend.user.model.User;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import com.utp.adoptappbackend.user.model.Hostel;
import com.utp.adoptappbackend.user.model.dto.HostelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hostel", ignore = true)
    User toEntity(UserRequest request);

    UserResponse toResponse(User entity);

    HostelResponse toHostelResponse(Hostel entity);
}

