package com.galaxynstudio.progymadmin.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "packageDetails")
public class PackageDetails {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer packageDetailsid;

    @ColumnInfo(name = "startDate")
    String startDate;


    @Embedded
    CPackage cPackage;

    @ColumnInfo(name = "status")
    String status;

    @ColumnInfo(name = "amountPaid")
    Double amountPaid;

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setPackageDetailsid(int packageDetailsid) {
        this.packageDetailsid = packageDetailsid;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public void setcPackage(CPackage cPackage) {
        this.cPackage = cPackage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPackageDetailsid() {
        return packageDetailsid;
    }

    public String getStartDate() {
        return startDate;
    }

    public CPackage getcPackage() {
        return cPackage;
    }

    public String getStatus() {
        return status;
    }
}
