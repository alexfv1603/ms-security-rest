package com.facturia.security.repository;

import com.facturia.security.application.dto.RolePermissionDTO;
import com.facturia.security.application.dto.UserDetailsDTO;
import com.facturia.security.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<Users, Long> {

    @Query("SELECT new com.facturia.security.application.dto.UserDetailsDTO(u.id, u.password, p.name, p.address, p.telephone, p.email) " +
            "FROM Users u JOIN Peoples p ON u.id = p.id WHERE u.username = :username")
    UserDetailsDTO findUserDetailsByUsername(@Param("username") String username);

    @Query("SELECT new com.facturia.security.application.dto.RolePermissionDTO(r.name, pr.name) " +
            "FROM Users u " +
            "JOIN Roles r ON r.id = u.role.id " +
            "JOIN RolePermissions rp ON rp.role.id = r.id " +
            "JOIN Permissions pr ON pr.id = rp.permission.id " +
            "WHERE u.id = :userId")
    List<RolePermissionDTO> findRolesAndPermissionsByUserId(@Param("userId") Long userId);

}