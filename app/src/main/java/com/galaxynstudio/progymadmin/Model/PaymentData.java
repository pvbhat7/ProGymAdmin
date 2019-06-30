package com.galaxynstudio.progymadmin.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = PackageDetails.class,
        parentColumns = "id",
        childColumns = "packageId",
        onDelete = CASCADE))
public class PaymentData {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index =true)
    private int id;

    @ColumnInfo(name = "packageId")
    int packageId;

    @ColumnInfo(name = "paymentDate")
    String paymentDate;

    @ColumnInfo(name = "amountPaid")
    Double amountPaid;

    public PaymentData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
