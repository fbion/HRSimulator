package com.idsmanager.oauth.service.impl;

import com.idsmanager.oauth.domain.dto.UserDto;
import com.idsmanager.oauth.domain.shared.security.WdcyUserDetails;
import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.oauth.domain.user.UserRepository;
import com.idsmanager.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author Shengzhao Li
 */
@Service("oauthUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null || user.archived()) {
            throw new UsernameNotFoundException("Not found any user of username[" + username + "] or it war archived");
        }

        return new WdcyUserDetails(user);
    }

    @Override
    public UserDto loadCurrentUserJsonDto() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (authentication instanceof OAuth2Authentication &&
                (principal instanceof String || principal instanceof org.springframework.security.core.userdetails.User)) {
            return loadOauthUserJsonDto((OAuth2Authentication) authentication);
        } else {
            final WdcyUserDetails userDetails = (WdcyUserDetails) principal;
            return new UserDto(userRepository.findByGuid(userDetails.user().guid()));
        }
    }

    @Override
    public List<UserDto> loadAllUserDtos() {
        final List<User> users = userRepository.findAllUsers();
        return UserDto.toDtos(users);
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = userDto.newDomain();
        userRepository.saveUser(user);
    }

    @Override
    public void removeUser(String guid) {
        final User user = userRepository.findByGuid(guid);
        userRepository.removeUser(user);
    }


    private UserDto loadOauthUserJsonDto(OAuth2Authentication oAuth2Authentication) {
        UserDto userJsonDto = new UserDto();
        userJsonDto.setUsername(oAuth2Authentication.getName());

        final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            userJsonDto.getPrivileges().add(authority.getAuthority());
        }

        return userJsonDto;
    }
}