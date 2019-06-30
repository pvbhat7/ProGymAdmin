package com.galaxynstudio.progymadmin.Dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;


import com.galaxynstudio.progymadmin.DataTypeConverter;
import com.galaxynstudio.progymadmin.Model.CPackage;
import com.galaxynstudio.progymadmin.Model.Client;
import com.galaxynstudio.progymadmin.Model.PackageDetails;
import com.galaxynstudio.progymadmin.Model.PaymentData;

@Database(entities = {Client.class, PackageDetails.class , CPackage.class, PaymentData.class}, version = 6,exportSchema = false)
@TypeConverters(DataTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClientDao clientDao();
    public abstract PackageDetailsDao packageDetailsDao();
    public abstract CPackageDao cPackageDao();
    public abstract PaymentDatadao paymentDatadao();
}
