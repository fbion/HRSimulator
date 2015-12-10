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
package com.idsmanager.oauth.infrastructure.mongo;

import com.idsmanager.oauth.domain.shared.BeanProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;

/**
 * 2015/10/28
 *
 * @author Shengzhao Li
 */
public abstract class AbstractMongoSupport {

    protected static final String ID = "_id";

    @Autowired
    private MongoOperations mongoTemplate;


    protected AbstractMongoSupport() {
    }

  

    protected <T extends Serializable> T findById(Class<T> clazz, Object id) {
        Query query = new Query(new Criteria(ID).is(id));
        return mongoTemplate().findOne(query, clazz);
    }


    protected MongoOperations mongoTemplate() {
		// TODO Auto-generated method stub
		return mongoTemplate;
	}



	protected Query createIDQuery(Object id) {
        final Criteria criteria = new Criteria(ID).is(id);
        return new Query(criteria);
    }


    
}
