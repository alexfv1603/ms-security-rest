package com.facturia.security.repository.impl;

import com.facturia.security.application.dto.RolePermissionDTO;
import com.facturia.security.application.dto.UserDetailsDTO;
import com.facturia.security.application.port.UserRepository;
import com.facturia.security.commons.Constants;
import com.facturia.security.exceptions.DataBaseException;
import com.facturia.security.domain.model.Users;
import com.facturia.security.repository.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<UserDetailsDTO> findUserDetailsByUsername(String username) {
        try {
            return Optional.of(userJpaRepository.findUserDetailsByUsername(username));
        } catch (DataAccessException e) {
            log.error(Constants.MESSAGE_TECHNICAL_ERROR_ACCESS_DATABASE, e);
            throw new DataBaseException(HttpStatus.SERVICE_UNAVAILABLE,
                    Constants.TECHNICAL_ERROR,
                    Constants.CODE_TECHNICAL_ERROR_ACCESS_DATABASE,
                    Constants.MESSAGE_TECHNICAL_ERROR_ACCESS_DATABASE);
        } catch (Exception e) {
            log.error(Constants.MESSAGE_FUNCTIONAL_DATABASE, e);
            throw new DataBaseException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Constants.FUNCTIONAL_ERROR,
                    Constants.CODE_FUNCTIONAL_ERROR_DATABASE,
                    Constants.MESSAGE_FUNCTIONAL_DATABASE);
        }
    }

    @Override
    public List<RolePermissionDTO> findRolesAndPermissionsByUserId(Long userId) {
        try {
            return userJpaRepository.findRolesAndPermissionsByUserId(userId);
        } catch (DataAccessException e) {
            log.error(Constants.MESSAGE_TECHNICAL_ERROR_ACCESS_DATABASE, e);
            throw new DataBaseException(HttpStatus.SERVICE_UNAVAILABLE,
                    Constants.TECHNICAL_ERROR,
                    Constants.CODE_TECHNICAL_ERROR_ACCESS_DATABASE,
                    Constants.MESSAGE_TECHNICAL_ERROR_ACCESS_DATABASE);
        } catch (Exception e) {
            log.error(Constants.MESSAGE_FUNCTIONAL_DATABASE, e);
            throw new DataBaseException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Constants.FUNCTIONAL_ERROR,
                    Constants.CODE_FUNCTIONAL_ERROR_DATABASE,
                    Constants.MESSAGE_FUNCTIONAL_DATABASE);
        }
    }
}
