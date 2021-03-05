package com.adgvit.hackgrid.model;

public class SpeakerModel {
    String speakerImage;
    String speakerName;
    String speakerDesignation;

    public SpeakerModel(String speakerImage, String speakerName, String speakerDesignation) {
        this.speakerImage = speakerImage;
        this.speakerName = speakerName;
        this.speakerDesignation = speakerDesignation;
    }

    public String getSpeakerImage() {
        return speakerImage;
    }

    public void setSpeakerImage(String speakerImage) {
        this.speakerImage = speakerImage;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerDesignation() {
        return speakerDesignation;
    }

    public void setSpeakerDesignation(String speakerDesignation) {
        this.speakerDesignation = speakerDesignation;
    }

    public SpeakerModel(){

    }
}
