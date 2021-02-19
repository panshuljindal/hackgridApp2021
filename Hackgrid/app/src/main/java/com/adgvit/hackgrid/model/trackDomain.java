package com.adgvit.hackgrid.model;

public class trackDomain {
    String domainName;
    String noOfTracks;

    public trackDomain(){

    }
    public trackDomain(String domainName, String noOfTracks) {
        this.domainName = domainName;
        this.noOfTracks = noOfTracks;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getNoOfTracks() {
        return noOfTracks;
    }

    public void setNoOfTracks(String noOfTracks) {
        this.noOfTracks = noOfTracks;
    }
}
