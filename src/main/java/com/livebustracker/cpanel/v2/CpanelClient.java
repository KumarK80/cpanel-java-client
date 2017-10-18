/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;

/**
 *
 * @author mani
 */
public abstract class CpanelClient {

    protected Context context;
    protected HttpClient httpClient;
    protected ObjectMapper objectMapper;

    protected CpanelClient(Context context, HttpClient httpClient, ObjectMapper objectMapper) {

        this.context = context;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public static class Context {

        private String ipAddress;
        private int port;
        private String username;
        private String password;
        private String baseApiEndpointUrl;

        public Context(String ipAddress, int port, String username, String password) {
            this.ipAddress = ipAddress;
            this.port = port;
            this.username = username;
            this.password = password;
            this.baseApiEndpointUrl = "http://" + ipAddress + ":" + port + "/json-api/cpanel?cpanel_jsonapi_apiversion=2&";
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public int getPort() {
            return port;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getBaseApiEndpointUrl() {
            return baseApiEndpointUrl;
        }
    }
}
