/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.idsmanager.oauth.infrastructure;


import com.idsmanager.oauth.domain.shared.GuidGenerator;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * 2015/7/15
 *
 * @author Shengzhao Li
 */
public abstract class PasswordHandler {


    //private, singleton
    private PasswordHandler() {
    }

    /**
     * Return a random password from {@link java.util.UUID},
     * the length is 8.
     *
     * @return Random password
     */
    public static String randomPassword() {
        String uuid = GuidGenerator.generate();
        return uuid.substring(0, 8);
    }


    /**
     * MD5 encrypt
     *
     * @param originalPassword Password
     * @return Encrypted password
     */
    public static String encryptPassword(String originalPassword) {
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        return md5PasswordEncoder.encodePassword(originalPassword, null);
    }

}
