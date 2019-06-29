package com.galaxynstudio.progymadmin.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.galaxynstudio.progymadmin.Model.Client;
import com.galaxynstudio.progymadmin.Model.PackageDetails;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert
    void insert(Client client);

    @Query("SELECT * FROM client")
    List<Client> getAllClients();

    @Query("SELECT * FROM client where id = :id")
    Client getClient(Integer id);

    @Query("UPDATE client SET packageDetails = :packageDetails where id = :id")
    void updateClient(Integer id, List<PackageDetails> packageDetails);

    @Query("SELECT packageDetails FROM client where id = :clientId")
    String  getClientPackageDetailsById(Integer clientId);



}
