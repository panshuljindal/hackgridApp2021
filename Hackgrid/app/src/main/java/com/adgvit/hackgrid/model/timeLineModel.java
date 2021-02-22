package com.adgvit.hackgrid.model;

public class timeLineModel {
    String description;
    String heading;
    String isCompleted;
    String isFirst;
    String link;
    String time;

    public timeLineModel(){

    }

    public timeLineModel(String description, String heading, String isCompleted, String isFirst, String link, String time) {
        this.description = description;
        this.heading = heading;
        this.isCompleted = isCompleted;
        this.isFirst = isFirst;
        this.link = link;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
