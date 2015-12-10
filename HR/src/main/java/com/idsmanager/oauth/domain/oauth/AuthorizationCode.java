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
package com.idsmanager.oauth.domain.oauth;

import com.idsmanager.oauth.infrastructure.DateUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.code.AuthorizationRequestHolder;

import java.io.Serializable;
import java.util.Date;

/**
 * 2015/10/28
 *
 * @author Shengzhao Li
 */
@Document(collection = "authorization_code_")
public class AuthorizationCode implements Serializable {

    private static final long serialVersionUID = 2775272095671208572L;


    @Id
    private String code;


    @CreatedDate
    private Date createTime = DateUtils.now();

//    @Version
    private int version = 0;

    private byte[] authenticationBytes;


    public AuthorizationCode() {
    }


    public String code() {
        return code;
    }

    public AuthorizationCode code(String code) {
        this.code = code;
        return this;
    }

    public Date createTime() {
        return createTime;
    }


    public int version() {
        return version;
    }


    public AuthorizationRequestHolder authentication() {
        return SerializationUtils.deserialize(authenticationBytes);
    }

    public AuthorizationCode authentication(AuthorizationRequestHolder authentication) {
        this.authenticationBytes = SerializationUtils.serialize(authentication);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", createTime=" + createTime +
                ", version=" + version +
                ", authenticationBytes=" + authenticationBytes +
                '}';
    }
}
