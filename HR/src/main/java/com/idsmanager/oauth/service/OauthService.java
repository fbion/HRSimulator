package com.idsmanager.oauth.service;

import com.idsmanager.oauth.domain.dto.OauthClientDetailsDto;
import com.idsmanager.oauth.domain.oauth.OauthClientDetails;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OauthClientDetailsDto formDto);
}