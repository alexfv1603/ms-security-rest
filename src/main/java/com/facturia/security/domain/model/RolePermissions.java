package com.facturia.security.domain.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "role_permissions")
public class RolePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permissions permission;
}