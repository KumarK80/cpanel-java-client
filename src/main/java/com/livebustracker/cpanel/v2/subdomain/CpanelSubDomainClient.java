/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2.subdomain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import com.livebustracker.cpanel.exceptions.EntityExistsException;
import com.livebustracker.cpanel.exceptions.EntityNotFoundException;
import com.livebustracker.cpanel.v2.CpanelClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client of subdomain domain.
 *
 * @author mani
 */
public class CpanelSubDomainClient extends CpanelClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpanelSubDomainClient.class);

    public CpanelSubDomainClient(CpanelClient.Context context, HttpClient httpClient, ObjectMapper objectMapper) {
        super(context, httpClient, objectMapper);
    }

    public List<CpanelSubDomain> getAllSubDomain() throws IOException {

        String apiEndpointUrl = this.context.getBaseApiEndpointUrl() + "cpanel_jsonapi_module=SubDomain"
                + "&cpanel_jsonapi_func=listsubdomains";
        HttpGet request = new HttpGet(apiEndpointUrl);

        HttpResponse response = null;
        try {
            response = this.httpClient.execute(request);
            JsonNode cpanelResultJsonNode = objectMapper.readTree(response.getEntity()
                    .getContent())
                    .get("cpanelresult");

            return this.objectMapper.readValue(cpanelResultJsonNode.get("data")
                    .toString(), objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, CpanelSubDomain.class));
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
    }

    public void createSubDomain(String subDomainName, boolean wildcard, String dir, boolean disallowDot) throws IOException, EntityExistsException {

        subDomainName = subDomainName.trim();
        if (subDomainName == null || subDomainName.isEmpty() || !subDomainName
                .matches("^[A-Za-z0-9]+[A-Za-z0-9\\-\\.]+[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("Subdomain name must not be null or empty!");
        }
        dir = dir.trim();

        // Notice anything.com in rootdomain parameter. It's because every cpanel account is associated
        // with on domain name. So the create subdomain we basically don't need a rootdomain. So why
        // waste our effort.
        String apiEndpointUrl = this.context.getBaseApiEndpointUrl() + "cpanel_jsonapi_module=SubDomain"
                + "&cpanel_jsonapi_func=addsubdomain&domain=" + subDomainName + "&rootdomain=anything.com";
        if (wildcard) {
            apiEndpointUrl += "&canoff=1";
        }
        if (dir != null && !dir.isEmpty()) {
            apiEndpointUrl += "&dir=" + dir;
        }
        if (disallowDot) {
            apiEndpointUrl += "&disallowdot=1";
        }
        HttpGet request = new HttpGet(apiEndpointUrl);

        HttpResponse response = null;
        try {
            response = this.httpClient.execute(request);
            JsonNode cpanelResultJsonNode = objectMapper.readTree(response.getEntity()
                    .getContent())
                    .get("cpanelresult");
            if (cpanelResultJsonNode.get("error") != null) {
                if (cpanelResultJsonNode.get("error")
                        .asText()
                        .contains("already exists")) {
                    throw new EntityExistsException(cpanelResultJsonNode.get("error")
                            .asText());
                }
            }

        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
    }

    public void destroySubdomain(String subDomainName) throws IOException, EntityNotFoundException {

        String apiEndpointUrl = this.context.getBaseApiEndpointUrl() + "cpanel_jsonapi_module=SubDomain"
                + "&cpanel_jsonapi_func=delsubdomain&domain=" + subDomainName;
        HttpGet request = new HttpGet(apiEndpointUrl);

        HttpResponse response = null;
        try {
            response = this.httpClient.execute(request);
            JsonNode cpanelResultJsonNode = objectMapper.readTree(response.getEntity()
                    .getContent())
                    .get("cpanelresult");

            if (cpanelResultJsonNode.get("error") != null) {
                if (cpanelResultJsonNode.get("error")
                        .asText()
                        .contains("does not exist")) {
                    throw new EntityNotFoundException(cpanelResultJsonNode.get("error")
                            .asText());
                }
            }

        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
    }
}
