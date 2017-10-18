/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.v2.subdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity model of subdomain domain.
 *
 * @author mani
 */
public class CpanelSubDomain {

    @JsonProperty("domainkey")
    private String domainKey;
    @JsonProperty("rootdomain")
    private String rootDomain;
    private String status;
    @JsonProperty("subdomain")
    private String subDomain;
    @JsonProperty("reldir")
    private String relDir;
    @JsonProperty("basedir")
    private String baseDir;
    private String dir;
    @JsonProperty("domain")
    private String domainName;

    /**
     * Get subdomain domain name in subdomain, an underscore, and the main domain format. For
     * example: subdomain_example.com
     *
     * @return
     */
    public String getDomainKey() {
        return domainKey;
    }

    /**
     * Get cPanel account's main domain.
     *
     * @return
     */
    public String getRootDomain() {
        return rootDomain;
    }

    /**
     * Gets the status of this subdomain. For example: not redirected
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets subdomain name.
     *
     * @return
     */
    public String getSubDomain() {
        return subDomain;
    }

    /**
     * Gets path to the domain's document root, relative to the cPanel account's home directory. For
     * example: home:public_html/subdomain
     *
     * @return
     */
    public String getRelDir() {
        return relDir;
    }

    /**
     * Gets directory that contains the subdomain's document root.
     *
     * @return
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * Gets absolute path to the subdomain's document root.
     *
     * @return
     */
    public String getDir() {
        return dir;
    }

    /**
     * Gets actual subdomain name.
     *
     * @param domainName
     */
    public String getDomainName() {
        return domainName;
    }
}
