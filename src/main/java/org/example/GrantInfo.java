package org.example;

import java.sql.Date;

public class GrantInfo {
    private Date taxPeriodEndDate;
    private String returnTypeCode;
    private Date taxPeriodBeginDate;
    private String ein;
    private String filerName;
    private String filerStreet;
    private String filerCity;
    private String filerState;
    private String filerZip;
    private String recipientName;
    private int grantAmount;

    private String recipientStreet;
    private String recipientCity;
    private String recipientState;
    private String recipientZip;
    private String recipientRelationship;
    private String recipientFoundationStatus;
    private String grantPurpose;

    public GrantInfo() {}

    public GrantInfo(Date taxPeriodEndDate, String returnTypeCode, Date taxPeriodBeginDate, String ein, String filerName, String filerStreet, String filerCity, String filerState, String filerZip, String recipientName, int grantAmount, String recipientStreet, String recipientCity, String recipientState, String recipientZip, String recipientRelationship, String recipientFoundationStatus, String grantPurpose) {
        this.taxPeriodEndDate = taxPeriodEndDate;
        this.returnTypeCode = returnTypeCode;
        this.taxPeriodBeginDate = taxPeriodBeginDate;
        this.ein = ein;
        this.filerName = filerName;
        this.filerStreet = filerStreet;
        this.filerCity = filerCity;
        this.filerState = filerState;
        this.filerZip = filerZip;
        this.recipientName = recipientName;
        this.grantAmount = grantAmount;
        this.recipientStreet = recipientStreet;
        this.recipientCity = recipientCity;
        this.recipientState = recipientState;
        this.recipientZip = recipientZip;
        this.recipientRelationship = recipientRelationship;
        this.recipientFoundationStatus = recipientFoundationStatus;
        this.grantPurpose = grantPurpose;
    }

    public Date getTaxPeriodEndDate() {
        return taxPeriodEndDate;
    }

    public void setTaxPeriodEndDate(Date taxPeriodEndDate) {
        this.taxPeriodEndDate = taxPeriodEndDate;
    }

    public String getReturnTypeCode() {
        return returnTypeCode;
    }

    public void setReturnTypeCode(String returnTypeCode) {
        this.returnTypeCode = returnTypeCode;
    }

    public Date getTaxPeriodBeginDate() {
        return taxPeriodBeginDate;
    }

    public void setTaxPeriodBeginDate(Date taxPeriodBeginDate) {
        this.taxPeriodBeginDate = taxPeriodBeginDate;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

    public String getFilerName() {
        return filerName;
    }

    public void setFilerName(String filerName) {
        this.filerName = filerName;
    }

    public String getFilerStreet() {
        return filerStreet;
    }

    public void setFilerStreet(String filerStreet) {
        this.filerStreet = filerStreet;
    }

    public String getFilerCity() {
        return filerCity;
    }

    public void setFilerCity(String filerCity) {
        this.filerCity = filerCity;
    }

    public String getFilerState() {
        return filerState;
    }

    public void setFilerState(String filerState) {
        this.filerState = filerState;
    }

    public String getFilerZip() {
        return filerZip;
    }

    public void setFilerZip(String filerZip) {
        this.filerZip = filerZip;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public int getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(int grantAmount) {
        this.grantAmount = grantAmount;
    }

    public String getRecipientStreet() {
        return recipientStreet;
    }

    public void setRecipientStreet(String recipientStreet) {
        this.recipientStreet = recipientStreet;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientState() {
        return recipientState;
    }

    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecipientZip() {
        return recipientZip;
    }

    public void setRecipientZip(String recipientZip) {
        this.recipientZip = recipientZip;
    }

    public String getRecipientRelationship() {
        return recipientRelationship;
    }

    public void setRecipientRelationship(String recipientRelationship) {
        this.recipientRelationship = recipientRelationship;
    }

    public String getRecipientFoundationStatus() {
        return recipientFoundationStatus;
    }

    public void setRecipientFoundationStatus(String recipientFoundationStatus) {
        this.recipientFoundationStatus = recipientFoundationStatus;
    }

    public String getGrantPurpose() {
        return grantPurpose;
    }

    public void setGrantPurpose(String grantPurpose) {
        this.grantPurpose = grantPurpose;
    }
}

