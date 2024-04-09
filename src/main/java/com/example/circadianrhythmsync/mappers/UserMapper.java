package com.example.circadianrhythmsync.mappers;

import com.example.circadianrhythmsync.dtos.UserDTO;
import com.example.circadianrhythmsync.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RoleMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    User fromUserDTO(UserDTO dto);
    UserDTO toUserDTO(User user);
    List<User> fromListOfUserDTOs(List<UserDTO> dtos);
    List<UserDTO> toListOfUserDTOs(List<User> users);
}