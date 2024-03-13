package org.tbs;

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
    private long totalRev;
    private long totalAssetsBOY;
    private long totalAssetsEOY;
    private long distributableAmount;
    private long remainingDistribution;
    private String recipientName;
    private long grantAmount;
    private String recipientStreet;
    private String recipientCity;
    private String recipientState;
    private String recipientZip;
    private String recipientRelationship;
    private String recipientFoundationStatus;
    private String grantPurpose;
    private String standardizedRecipientName;

    public GrantInfo() {}

    public GrantInfo(Date taxPeriodEndDate, String returnTypeCode, Date taxPeriodBeginDate, String ein, String filerName, String filerStreet, String filerCity, String filerState, String filerZip, long totalRev, long totalAssetsBOY, long totalAssetsEOY, long distributableAmount, long remainingDistribution, String recipientName, long grantAmount, String recipientStreet, String recipientCity, String recipientState, String recipientZip, String recipientRelationship, String recipientFoundationStatus, String grantPurpose, String standardizedRecipientName) {
        this.taxPeriodEndDate = taxPeriodEndDate;
        this.returnTypeCode = returnTypeCode;
        this.taxPeriodBeginDate = taxPeriodBeginDate;
        this.ein = ein;
        this.filerName = filerName;
        this.filerStreet = filerStreet;
        this.filerCity = filerCity;
        this.filerState = filerState;
        this.filerZip = filerZip;
        this.totalRev = totalRev;
        this.totalAssetsBOY = totalAssetsBOY;
        this.totalAssetsEOY = totalAssetsEOY;
        this.distributableAmount = distributableAmount;
        this.remainingDistribution = remainingDistribution;
        this.recipientName = recipientName;
        this.grantAmount = grantAmount;
        this.recipientStreet = recipientStreet;
        this.recipientCity = recipientCity;
        this.recipientState = recipientState;
        this.recipientZip = recipientZip;
        this.recipientRelationship = recipientRelationship;
        this.recipientFoundationStatus = recipientFoundationStatus;
        this.grantPurpose = grantPurpose;
        this.standardizedRecipientName = standardizedRecipientName;

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

    public long getTotalRev() {
        return totalRev;
    }

    public void setTotalRev(long totalRev) {
        this.totalRev = totalRev;
    }

    public long getTotalAssetsBOY() {
        return totalAssetsBOY;
    }

    public void setTotalAssetsBOY(long totalAssetsBOY) {
        this.totalAssetsBOY = totalAssetsBOY;
    }

    public long getTotalAssetsEOY() {
        return totalAssetsEOY;
    }

    public void setTotalAssetsEOY(long totalAssetsEOY) {
        this.totalAssetsEOY = totalAssetsEOY;
    }

    public long getDistributableAmount() {
        return distributableAmount;
    }

    public void setDistributableAmount(long distributableAmount) {
        this.distributableAmount = distributableAmount;
    }

    public long getRemainingDistribution() {
        return remainingDistribution;
    }

    public void setRemainingDistribution(long remainingDistribution) {
        this.remainingDistribution = remainingDistribution;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public long getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(long grantAmount) {
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

    public String getStandardizedRecipientName() {
        return standardizedRecipientName;
    }

    public void setStandardizedRecipientName(String standardizedRecipientName) {
        this.standardizedRecipientName = standardizedRecipientName;
    }

    @Override
    public String toString() {
        return "GrantInfo{" +
                "taxPeriodEndDate=" + taxPeriodEndDate +
                ", returnTypeCode='" + returnTypeCode + '\'' +
                ", taxPeriodBeginDate=" + taxPeriodBeginDate +
                ", ein='" + ein + '\'' +
                ", filerName='" + filerName + '\'' +
                ", filerStreet='" + filerStreet + '\'' +
                ", filerCity='" + filerCity + '\'' +
                ", filerState='" + filerState + '\'' +
                ", filerZip='" + filerZip + '\'' +
                ", totalRev=" + totalRev +
                ", totalAssetsBOY=" + totalAssetsBOY +
                ", totalAssetsEOY=" + totalAssetsEOY +
                ", distributableAmount=" + distributableAmount +
                ", remainingDistribution=" + remainingDistribution +
                ", recipientName='" + recipientName + '\'' +
                ", grantAmount=" + grantAmount +
                ", recipientStreet='" + recipientStreet + '\'' +
                ", recipientCity='" + recipientCity + '\'' +
                ", recipientState='" + recipientState + '\'' +
                ", recipientZip='" + recipientZip + '\'' +
                ", recipientRelationship='" + recipientRelationship + '\'' +
                ", recipientFoundationStatus='" + recipientFoundationStatus + '\'' +
                ", grantPurpose='" + grantPurpose + '\'' +
                ", standardizedRecipientName='" + standardizedRecipientName + '\'' +
                '}';
    }
}

