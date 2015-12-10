package com.idsmanager.oauth.service;

import com.idsmanager.oauth.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public interface UserService extends UserDetailsService {

    UserDto loadCurrentUserJsonDto();

    List<UserDto> loadAllUserDtos();

    void createUser(UserDto userDto);

    void removeUser(String guid);
}