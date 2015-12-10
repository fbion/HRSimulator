package com.idsmanager.oauth.domain.user;

import java.util.Arrays;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public enum Privilege {

    USER,          //Default privilege

    WEB,
    API;


    public static List<Privilege> availablePrivileges() {
        return Arrays.asList(WEB, API);
    }
}