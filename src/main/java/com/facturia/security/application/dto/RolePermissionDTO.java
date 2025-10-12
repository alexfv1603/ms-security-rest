package com.facturia.security.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolePermissionDTO {

    private String rol;
    private String permiso;

}