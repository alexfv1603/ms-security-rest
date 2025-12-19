package com.facturia.security.application.service;

import com.facturia.security.application.dto.UserDetailsDTO;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JwtTokenService {

    @Value("${security.signing-key}")
    private String signingKey;

    public String createToken(UserDetailsDTO user, List<String> scopes, long ttlSeconds) throws Exception {
        byte[] keyBytes = ensureKeyLength(signingKey);
        JWSSigner signer = new MACSigner(keyBytes);

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttlSeconds);

        // Construir el subject como cadena JSON con la forma solicitada
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> sub = new HashMap<>();
            sub.put("idUsuario", user.getIdUsuario());
            sub.put("nombre", user.getNombre());
            sub.put("direccion", user.getDireccion());
            sub.put("telefono", user.getTelefono());
            sub.put("correo", user.getCorreo());

            if (user.getRoles() != null) {
                List<Map<String, Object>> rolesJson = user.getRoles().stream().map(r -> {
                    Map<String, Object> rm = new HashMap<>();
                    rm.put("id", r.getId());
                    rm.put("permisos", r.getPermisos());
                    return rm;
                }).collect(Collectors.toList());
                sub.put("roles", rolesJson);
            }

            String subjectJson = mapper.writeValueAsString(sub);

            JWTClaimsSet.Builder claims = new JWTClaimsSet.Builder()
                    .subject(subjectJson)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(exp))
                    .claim("user_id", user.getIdUsuario())
                    .claim("scope", String.join(" ", scopes));

            // additionally keep roles as claim (array of ids) for easier RBAC checks
            if (user.getRoles() != null) {
                List<String> roles = user.getRoles().stream().map(r -> r.getId()).collect(Collectors.toList());
                claims.claim("roles", roles);
            }

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims.build());
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private byte[] ensureKeyLength(String key) {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        if (bytes.length >= 32) return bytes;
        byte[] padded = new byte[32];
        for (int i = 0; i < 32; i++) {
            padded[i] = bytes[i % bytes.length];
        }
        return padded;
    }

}
