package com.idsmanager.oauth.domain.user;

import com.idsmanager.oauth.domain.shared.Repository;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {

    User findByGuid(String guid);

    void saveUser(User user);

    boolean updateUser(User user);

    User findByUsername(String username);

    List<User> findAllUsers();

    boolean removeUser(User user);

}