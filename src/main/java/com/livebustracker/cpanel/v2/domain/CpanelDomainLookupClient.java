/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import com.livebustracker.cpanel.exceptions.AuthenticationException;
import com.livebustracker.cpanel.v2.CpanelClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author mani
 */
public class CpanelDomainLookupClient extends CpanelClient {

    public CpanelDomainLookupClient(Context context, HttpClient httpClient, ObjectMapper objectMapper) {
        super(context, httpClient, objectMapper);
    }

    public String getDomainName() throws IOException {

        String apiEndpointUrl = this.context.getBaseApiEndpointUrl() + "cpanel_jsonapi_module=DomainLookup"
                + "&cpanel_jsonapi_func=getmaindomain";
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
                        .equalsIgnoreCase("Access denied")) {
                    System.out.println("Running....");
                    throw new AuthenticationException(cpanelResultJsonNode.get("error")
                            .asText());
                }
            }

            return cpanelResultJsonNode.get("data")
                    .get(0)
                    .get("main_domain")
                    .asText();
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
    }
}
