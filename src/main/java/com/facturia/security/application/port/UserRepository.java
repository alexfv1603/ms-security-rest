package com.facturia.security.application.port;

import com.facturia.security.application.dto.RolePermissionDTO;
import com.facturia.security.application.dto.UserDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<UserDetailsDTO> findUserDetailsByUsername(String username);
    List<RolePermissionDTO> findRolesAndPermissionsByUserId(Long userId);

}