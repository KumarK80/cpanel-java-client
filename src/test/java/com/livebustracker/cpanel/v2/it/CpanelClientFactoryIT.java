/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2.it;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import com.livebustracker.cpanel.v2.CpanelClient;
import com.livebustracker.cpanel.v2.CpanelClientFactory;
import org.apache.http.auth.AuthenticationException;
import org.junit.Test;

/**
 *
 * @author mani
 */
public class CpanelClientFactoryIT extends IntegrationTest {

    @Override
    public void setUp() throws AuthenticationException, IOException {
    }

    @Test(expected = UnknownHostException.class)
    public void testGettingClientFactoryWithWrongIpAddress_ShouldGetUnknownHostException() throws AuthenticationException, IOException {

        CpanelClientFactory
                .getInstance(new CpanelClient.Context("47.241.532.18", this.defaultPort, this.defaultUsername, this.defaultPassword));
    }

    @Test(expected = ConnectException.class)
    public void testGettingClientFactoryWithWrongPort_ShouldGetHttpHostConnectException() throws AuthenticationException, IOException {

        CpanelClientFactory
                .getInstance(new CpanelClient.Context(this.defaultIpAddress, 2085, this.defaultUsername, this.defaultPassword));
    }

    @Test(expected = AuthenticationException.class)
    public void testGettingClientFactoryWithWrongCredentials_ShouldGetAuthenticationException() throws AuthenticationException, IOException {

        CpanelClientFactory
                .getInstance(new CpanelClient.Context(this.defaultIpAddress, this.defaultPort, "wrongusername", "wrongpassword"));
    }
}
