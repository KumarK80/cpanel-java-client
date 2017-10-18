/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2.it;

import java.io.IOException;
import com.livebustracker.cpanel.exceptions.EntityExistsException;
import com.livebustracker.cpanel.exceptions.EntityNotFoundException;
import org.junit.Test;

/**
 * @author mani
 */
public class CpanelSubDomainClientIT extends IntegrationTest {

    @Test
    public void testGettingAllSubDomain_ShouldNotGetAnyException() throws IOException {

        this.clientFactory.getSubDomainClient()
                .getAllSubDomain();
    }

    @Test
    public void testCreatingSubDomain_ShouldNotGetAnyException() throws IOException, EntityExistsException {

        this.clientFactory.getSubDomainClient()
                .createSubDomain("another", false, "/public_html/another", false);
    }

    @Test(expected = EntityExistsException.class)
    public void testCreatingDuplicateSubDomain_ShouldGetEntityExistsException() throws IOException, EntityExistsException {

        this.clientFactory.getSubDomainClient()
                .createSubDomain("another", false, "public_html/another", false);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDestroyingNonExistingSubDomain_ShouldGetEntityNotFoundException() throws IOException, EntityNotFoundException {

        this.clientFactory.getSubDomainClient()
                .destroySubdomain("test");
    }

    @Test
    public void testDestroyingSubDomain_ShouldNotGetAnyException() throws IOException, EntityNotFoundException {

        this.clientFactory.getSubDomainClient()
                .destroySubdomain("another");
    }
}
