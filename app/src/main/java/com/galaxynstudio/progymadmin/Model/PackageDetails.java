package com.galaxynstudio.progymadmin.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Client.class,
        parentColumns = "id",
        childColumns = "cliendId",
        onDelete = CASCADE))
public class PackageDetails {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index =true)
    private int id;

    @ColumnInfo(name = "startDate")
    String startDate;

    @Embedded
    public CPackage cPackage;

    @ColumnInfo(name = "status")
    String status;

    @ColumnInfo(name = "amountPaid")
    Double amountPaid;

    @ColumnInfo(name="cliendId")
    public int cliendId;





    public int getCliendId() {
        return cliendId;
    }

    public void setCliendId(int cliendId) {
        this.cliendId = cliendId;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
