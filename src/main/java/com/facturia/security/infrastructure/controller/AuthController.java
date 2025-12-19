package com.facturia.security.infrastructure.controller;

import com.facturia.security.application.dto.UserDetailsDTO;
import com.facturia.security.application.service.JwtTokenService;
import com.facturia.security.application.port.UserRepository;
import com.facturia.security.domain.mapper.RolePermissionMapper;
import com.facturia.security.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RolePermissionMapper rolePermissionMapper;

    @Value("${security.jwt.client-id}")
    private String clientId;
    @Value("${security.jwt.client-secret}")
    private String clientSecret;

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> token(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                   @RequestParam("grant_type") String grantType,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password) throws Exception {

        // validate grant type
        if (!"password".equals(grantType)) {
            return ResponseEntity.badRequest().body("unsupported_grant_type");
        }

        // validate client credentials (Basic)
        if (!validateClient(authHeader)) {
            return ResponseEntity.status(401).body("invalid_client");
        }

        // load user
        UserDetailsDTO user = userRepository.findUserDetailsByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(400).body("invalid_grant: user not found");
        }

        // populate roles + permisos from DB (was missing when loading plain UserDetailsDTO)
        try {
            var rolesPermissions = userRepository.findRolesAndPermissionsByUserId(user.getIdUsuario());
            var rolesWithPermissions = rolePermissionMapper.mapToRoleWithPermissions(rolesPermissions);
            user.setRoles(rolesWithPermissions);
        } catch (Exception e) {
            // if roles lookup fails, continue without roles (but log could be added)
        }

        // verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(400).body("invalid_grant: bad credentials");
        }

        List<String> scopes = Arrays.asList("read", "write");
        String token = jwtTokenService.createToken(user, scopes, 3600);

        return ResponseEntity.ok().body(new TokenResponse(token, "bearer", 3600, String.join(" ", scopes)));
    }

    private boolean validateClient(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) return false;
        try {
            String b64 = authHeader.substring(6).trim();
            String decoded = new String(Base64Utils.decodeFromString(b64));
            String[] parts = decoded.split(":" ,2);
            if (parts.length != 2) return false;
            return clientId.equals(parts[0]) && clientSecret.equals(parts[1]);
        } catch (Exception e) {
            return false;
        }
    }

}
