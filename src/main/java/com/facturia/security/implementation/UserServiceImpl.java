package com.facturia.security.implementation;

import com.facturia.security.application.dto.RolePermissionDTO;
import com.facturia.security.application.dto.RoleWithPermissionsDTO;
import com.facturia.security.application.dto.UserDetailsDTO;
import com.facturia.security.application.port.UserRepository;
import com.facturia.security.domain.mapper.RolePermissionMapper;
import com.facturia.security.exception.ModeloNotFoundException;
import com.facturia.security.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final RolePermissionMapper rolePermissionMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Fetching user details for username: {}", username);

		Optional<UserDetailsDTO> userOptional = userRepository.findUserDetailsByUsername(username);
		UserDetailsDTO user = userOptional.orElseThrow(() -> new ModeloNotFoundException("Usuario no existe"));

		List<RolePermissionDTO> rolesPermissions = userRepository.findRolesAndPermissionsByUserId(user.getIdUsuario());
		List<RoleWithPermissionsDTO> rolesWithPermissions = rolePermissionMapper.mapToRoleWithPermissions(rolesPermissions);

		user.setRoles(rolesWithPermissions);
		log.info("User details: {}", JsonUtils.convertToJson(user));

		List<GrantedAuthority> authorities = new ArrayList<>();
		return new User(JsonUtils.convertToJson(user), user.getPassword(), authorities);
	}
}