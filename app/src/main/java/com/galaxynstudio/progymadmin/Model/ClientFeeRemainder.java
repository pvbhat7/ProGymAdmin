package com.galaxynstudio.progymadmin.Model;

public class ClientFeeRemainder {
    String name;
    String mobile;
    String packageName;
    Integer daysLeft;
    String packageEndDate;
    Double remainingFees;

    public Double getRemainingFees() {
        return remainingFees;
    }

    public void setRemainingFees(Double remainingFees) {
        this.remainingFees = remainingFees;
    }

    public String getPackageEndDate() {
        return packageEndDate;
    }

    public void setPackageEndDate(String packageEndDate) {
        this.packageEndDate = packageEndDate;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPackageName() {
        return packageName;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }
}
