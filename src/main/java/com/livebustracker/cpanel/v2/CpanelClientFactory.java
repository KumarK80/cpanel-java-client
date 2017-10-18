/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import com.livebustracker.cpanel.exceptions.AuthenticationException;
import com.livebustracker.cpanel.v2.domain.CpanelDomainLookupClient;
import com.livebustracker.cpanel.v2.subdomain.CpanelSubDomainClient;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

/**
 * Cpanel provide services of multiple domains like database, domain, subdomain, email. In order to
 * manage this client library codes, each domain has their own client which means this library
 * doesn't have main parent/root client.
 *
 * To serve these clients easily, this factory class is created and recommended to use. User can
 * create each client manually, however that would be tedious to manage and give client context.
 *
 * @author mani
 */
public class CpanelClientFactory {

    private static CpanelClientFactory INSTANCE = null;

    private CpanelClient.Context context;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    private CpanelSubDomainClient subDomainClient;
    private CpanelDomainLookupClient domainLookupClient;

    private CpanelClientFactory(CpanelClient.Context context) throws org.apache.http.auth.AuthenticationException, IOException {

        this.context = context;

        List<Header> defaultHeaders = new ArrayList<>();
        defaultHeaders.add(new BasicHeader("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString((context.getUsername() + ":" + context.getPassword())
                        .getBytes())));

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        this.httpClient = HttpClientBuilder.create()
                .setDefaultHeaders(defaultHeaders)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Checking username/password validity
        this.domainLookupClient = new CpanelDomainLookupClient(context, this.httpClient, this.objectMapper);
        try {
            this.domainLookupClient.getDomainName();
        } catch (AuthenticationException e) {
            throw new org.apache.http.auth.AuthenticationException(e.getMessage());
        }
    }

    public static CpanelClientFactory getInstance(CpanelClient.Context context) throws org.apache.http.auth.AuthenticationException, IOException {

        if (INSTANCE == null) {
            INSTANCE = new CpanelClientFactory(context);
        }
        return INSTANCE;
    }

    public CpanelClient.Context getContext() {
        return context;
    }

    public void setContext(CpanelClient.Context context) {
        this.context = context;
    }

    /**
     * Gets client of subdomain domain.
     *
     * @return
     */
    public CpanelSubDomainClient getSubDomainClient() {

        if (this.subDomainClient == null) {
            this.subDomainClient = new CpanelSubDomainClient(this.context, this.httpClient, this.objectMapper);
        }
        return this.subDomainClient;
    }
}
