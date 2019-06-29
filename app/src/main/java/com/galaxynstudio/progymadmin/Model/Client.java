package com.galaxynstudio.progymadmin.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.galaxynstudio.progymadmin.DataTypeConverter;

import java.util.List;

@Entity(tableName = "client")
public class Client {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "mobile")
    String mobile;

    @TypeConverters(DataTypeConverter.class)
    @ColumnInfo(name = "packageDetails")
    List<PackageDetails> packageDetails;

    @ColumnInfo(name = "clientStatus")
    String clientStatus;

    @ColumnInfo(name = "sex")
    String sex;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPackageDetails(List<PackageDetails> packageDetails) {
        this.packageDetails = packageDetails;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public List<PackageDetails> getPackageDetails() {
        return packageDetails;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", packageDetails=" + packageDetails +
                ", clientStatus='" + clientStatus + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
