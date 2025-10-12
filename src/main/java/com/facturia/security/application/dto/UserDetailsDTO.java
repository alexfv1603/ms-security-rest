package com.facturia.security.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Data
public class UserDetailsDTO {

    private Long idUsuario;
    @JsonIgnore
    private String password;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private List<RoleWithPermissionsDTO> roles;

    public UserDetailsDTO(long idUsuario, String password, String nombre, String direccion, String telefono, String correo) {
        this.idUsuario = idUsuario;
        this.password = password;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

}