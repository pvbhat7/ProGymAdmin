package com.galaxynstudio.progymadmin.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.galaxynstudio.progymadmin.Model.Client;
import com.galaxynstudio.progymadmin.Model.PackageDetails;
import com.galaxynstudio.progymadmin.Model.PaymentData;

import java.util.List;

@Dao
public interface PaymentDatadao {

    @Insert
    Long insert(PaymentData paymentData);

    @Query("select * from paymentdata where packageId = :id")
    List<PaymentData> getPaymentDetailsUsingPackageId(int id);

    @Query("select * from paymentdata")
    List<PaymentData> getAllPaymentData();
}
