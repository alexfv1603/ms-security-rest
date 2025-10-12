package com.facturia.security.domain.mapper;

import com.facturia.security.application.dto.RolePermissionDTO;
import com.facturia.security.application.dto.RoleWithPermissionsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RolePermissionMapper {

    public List<RoleWithPermissionsDTO> mapToRoleWithPermissions(List<RolePermissionDTO> rolesPermissions) {
        Map<String, List<String>> groupedPermissions = rolesPermissions.stream()
                .collect(Collectors.groupingBy(
                        RolePermissionDTO::getRol,
                        Collectors.mapping(RolePermissionDTO::getPermiso, Collectors.toList())
                ));

        return groupedPermissions.entrySet().stream()
                .map(entry -> new RoleWithPermissionsDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}