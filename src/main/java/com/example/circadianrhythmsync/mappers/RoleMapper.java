package com.example.circadianrhythmsync.mappers;

import com.example.circadianrhythmsync.dtos.RoleDTO;
import com.example.circadianrhythmsync.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Role fromRoleDTO(RoleDTO dto);
    RoleDTO toRoleDTO(Role role);

}
