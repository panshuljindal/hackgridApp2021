package com.adgvit.hackgrid.model;

public class problemStatementModel {
    String domainName,statementDownload,statementInfo,statementName,statementFileName;
    public problemStatementModel(){

    }

    public problemStatementModel(String domainName, String statementDownload, String statementInfo, String statementName, String statementFileName) {
        this.domainName = domainName;
        this.statementDownload = statementDownload;
        this.statementInfo = statementInfo;
        this.statementName = statementName;
        this.statementFileName = statementFileName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getStatementDownload() {
        return statementDownload;
    }

    public void setStatementDownload(String statementDownload) {
        this.statementDownload = statementDownload;
    }

    public String getStatementInfo() {
        return statementInfo;
    }

    public void setStatementInfo(String statementInfo) {
        this.statementInfo = statementInfo;
    }

    public String getStatementName() {
        return statementName;
    }

    public void setStatementName(String statementName) {
        this.statementName = statementName;
    }

    public String getStatementFileName() {
        return statementFileName;
    }

    public void setStatementFileName(String statementFileName) {
        this.statementFileName = statementFileName;
    }
}
