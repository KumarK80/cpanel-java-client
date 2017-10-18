/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2.it;

import java.io.IOException;
import com.livebustracker.cpanel.v2.CpanelClient;
import com.livebustracker.cpanel.v2.CpanelClientFactory;
import org.apache.http.auth.AuthenticationException;
import org.junit.Before;

/**
 *
 * @author mani
 */
public abstract class IntegrationTest {

    protected String defaultIpAddress = "47.241.532.17";
    protected int defaultPort = 2082;
    protected String defaultUsername = "testusername";
    protected String defaultPassword = "testpassword";

    protected CpanelClientFactory clientFactory;

    @Before
    public void setUp() throws AuthenticationException, IOException {

        this.clientFactory = CpanelClientFactory
                .getInstance(new CpanelClient.Context(this.defaultIpAddress, this.defaultPort, this.defaultUsername, this.defaultPassword));
    }
}
