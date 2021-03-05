package com.adgvit.hackgrid.model;

public class SponsorsModel {
    String sponsorImage;
    String sponsorLink;

    public SponsorsModel(String sponsorImage, String sponsorLink) {
        this.sponsorImage = sponsorImage;
        this.sponsorLink = sponsorLink;
    }

    public String getSponsorImage() {
        return sponsorImage;
    }

    public void setSponsorImage(String sponsorImage) {
        this.sponsorImage = sponsorImage;
    }

    public String getSponsorLink() {
        return sponsorLink;
    }

    public void setSponsorLink(String sponsorLink) {
        this.sponsorLink = sponsorLink;
    }

    public SponsorsModel(){

    }

}
