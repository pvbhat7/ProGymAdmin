package com.galaxynstudio.progymadmin.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.galaxynstudio.progymadmin.Model.Client;
import com.galaxynstudio.progymadmin.Model.PackageDetails;

import java.util.List;

@Dao
public interface PackageDetailsDao {
    @Insert
    Long insert(PackageDetails packageDetails);

    @Update
    void update(PackageDetails packageDetails);

    @Delete
    void delete(PackageDetails packageDetails);

    @Insert
    void insertBatchPackageDetails(List<PackageDetails> packageDetailsList);

    @Query("select * from packageDetails where cliendId = :id")
    List<PackageDetails> getPackageDetailsByClientId(int id);

    @Query("select * from packageDetails where cliendId = :cId and id = :pId")
    PackageDetails getPackageDetailsByClientIdAndPackageId(int cId,int pId);

}
