package com.galaxynstudio.progymadmin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxynstudio.progymadmin.Dao.DatabaseClient;
import com.galaxynstudio.progymadmin.Model.CPackage;
import com.galaxynstudio.progymadmin.Model.Client;
import com.galaxynstudio.progymadmin.Model.PackageDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddClientPage extends AppCompatActivity implements View.OnClickListener{

    Button selectDateButton,addClientButton,removeClientPackageButton;
    DatePickerDialog picker;
    EditText selectedDate,clientName,clientMobile;
    Spinner clientGender,clientPackage;
    Boolean isClientPresent=false;
    int clientId=0;
    String gender_i,parentcommand;
    TextView formTitle,client_currentPackage,currentPkgLabel;
    int clientPackageDetailsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client_page);
        init();
        Intent intent=getIntent();
        if(intent!=null){
            parentcommand=intent.getStringExtra("Parentcommand");

            if(intent.getBooleanExtra("client",false)){
                isClientPresent=true;
                String name=intent.getStringExtra("clientName");
                clientId=intent.getIntExtra("clientId",0);
                gender_i=intent.getStringExtra("clientGender");

                clientName.setText(name);
                clientMobile.setVisibility(View.GONE);
                clientGender.setVisibility(View.GONE);
            }

            if(parentcommand != null){
                if(parentcommand.equals("updatePkgToExistingClient")){
                    addClientButton.setText("UPDATE PACKAGE");
                    formTitle.setText("Update package Page");
                    selectedDate.setText(intent.getStringExtra("clientPackageStartDate"));
                    currentPkgLabel.setVisibility(View.VISIBLE);
                    client_currentPackage.setVisibility(View.VISIBLE);
                    client_currentPackage.setText(intent.getStringExtra("clientPackageCurrent"));
                    clientPackageDetailsId=intent.getIntExtra("clientPackageDetailsId",0);
                    removeClientPackageButton.setVisibility(View.VISIBLE);

                }else if(parentcommand.equals("addPkgToExistingClient")){
                    addClientButton.setText("ADD PACKAGE");
                    formTitle.setText("Add package Page");
                }
            }
            else{
                addClientButton.setText("ADD CLIENT");
                formTitle.setText("Add New Client Page");
            }




        }



    }
    public void init(){
        clientName=findViewById(R.id.client_name);
        clientMobile=findViewById(R.id.client_mobile);
        clientGender=findViewById(R.id.client_gender);
        clientPackage=findViewById(R.id.client_package);

        formTitle=findViewById(R.id.formTitle);
        client_currentPackage=findViewById(R.id.client_currentPackage);
        currentPkgLabel=findViewById(R.id.client_currentPackageLabel);
        //date
        selectDateButton=findViewById(R.id.selectDateButton);
        selectDateButton.setOnClickListener(this);
        selectedDate=findViewById(R.id.selectedDate);

        addClientButton=findViewById(R.id.addClientButton);
        addClientButton.setOnClickListener(this);

        removeClientPackageButton=findViewById(R.id.removeClientPackageButton);
        removeClientPackageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selectDateButton:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddClientPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selectedDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;

            case R.id.addClientButton:
                //create client object
                if(isClientPresent){
                    // update existing client

                    if(parentcommand.equals("updatePkgToExistingClient")){
                        // UPDATE EXISTING PACKAGE
                        updateExistingPackage(clientId,clientPackageDetailsId);
                    }else if(parentcommand.equals("addPkgToExistingClient")){
                        // ADD NEW PACKAGE
                        addNewpackage();
                    }



                }else{
                    // create new client
                    Client client=new Client();
                    client.setName(clientName.getText().toString().trim());
                    client.setMobile(clientMobile.getText().toString().trim());
                    client.setSex(((TextView)clientGender.getSelectedView()).getText().toString().trim());
                    client.setClientStatus("active");
                    Long clientId=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .clientDao().insert(client);

                    // create package
                    PackageDetails packageDetails=new PackageDetails();
                    packageDetails.setStatus("Not Paid");
                    packageDetails.setAmountPaid(0.00);
                    packageDetails.setStartDate(selectedDate.getText().toString().trim());
                    packageDetails.setcPackage(getPackageByHashing(((TextView)clientPackage.getSelectedView()).getText().toString().trim(),((TextView)clientGender.getSelectedView()).getText().toString().trim()));
                    packageDetails.setCliendId(clientId.intValue());
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .packageDetailsDao().insert(packageDetails);


                    // save to db

                    Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddClientPage.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;

            case R.id.removeClientPackageButton:
                PackageDetails packageDetails=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().getPackageDetailsByClientIdAndPackageId(clientId,clientPackageDetailsId);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().delete(packageDetails);
                Toast.makeText(this, "Package Removed", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,ViewClientProfile.class);
                intent.putExtra("clientId",clientId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }


    }

    public void addNewpackage(){


        PackageDetails packageDetails=new PackageDetails();
        packageDetails.setStartDate(selectedDate.getText().toString().trim());
        packageDetails.setStatus("Not Paid");
        packageDetails.setAmountPaid(0.00);
        packageDetails.setcPackage(getPackageByHashing(((TextView)clientPackage.getSelectedView()).getText().toString().trim(),gender_i));
        packageDetails.setCliendId(clientId);
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .packageDetailsDao().insert(packageDetails);
        Intent intent=new Intent(this,ViewClientProfile.class);
        intent.putExtra("clientId",clientId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void updateExistingPackage(int cId, int pId){
        PackageDetails packageDetails= DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().getPackageDetailsByClientIdAndPackageId(cId,pId);
        packageDetails.setcPackage(getPackageByHashing(((TextView)clientPackage.getSelectedView()).getText().toString().trim(),gender_i));
        packageDetails.setStartDate(selectedDate.getText().toString().trim());
        packageDetails.setAmountPaid(0.00);
        packageDetails.setStatus("Not Paid");
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().update(packageDetails);
        Toast.makeText(this, "PackageUpdated", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,ViewClientProfile.class);
        intent.putExtra("clientId",clientId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

    private CPackage getPackageByHashing(String packageHash, String gender) {
        CPackage cPackage=null;
        char ch='#';
        int index=packageHash.indexOf(ch);
        String no=packageHash.charAt(index+1)+""+packageHash.charAt(index+2);

        if(no.equals("01")){
            cPackage=new CPackage("GYM","1 Month",30,500.00);
        }
        else if(no.equals("02")){
            cPackage=new CPackage("GYM","3 Month",90,1200.00);
        }
        else if(no.equals("03")){
            cPackage=new CPackage("GYM","6 Month",180,2300.00);
        }
        else if(no.equals("04")){
            cPackage=new CPackage("GYM","1 Year",365,4200.00);
        }
        else if(no.equals("05")){
            cPackage=new CPackage("GYM + CARDIO","1 Month",30,800.00);
        }
        else if(no.equals("06")){
            cPackage=new CPackage("GYM + CARDIO","3 Month",90,2100.00);
        }
        else if(no.equals("07")){
            cPackage=new CPackage("GYM + CARDIO","6 Month",180,4000.00);
        }
        else if(no.equals("08")){
            cPackage=new CPackage("GYM + CARDIO","1 Year",365,7000.00);
        }
        else if(no.equals("09")){
            cPackage=new CPackage("GYM","1 Month",30,800.00);
        }
        else if(no.equals("10")){
            cPackage=new CPackage("GYM","3 Month",90,2300.00);
        }
        else if(no.equals("11")){
            cPackage=new CPackage("GYM","6 Month",180,4300.00);
        }
        else if(no.equals("12")){
            cPackage=new CPackage("GYM","1 Year",365,8000.00);
        }
        else if(no.equals("13")){
            cPackage=new CPackage("COUPLE PKG","3 Month",90,4500.00);
        }
        else if(no.equals("14")){
            cPackage=new CPackage("COUPLE PKG","6 Month",180,8500.00);
        }
        else if(no.equals("15")){
            cPackage=new CPackage("COUPLE PKG","1 Year",365,16000.00);
        }
        cPackage.setSex(gender);
        return cPackage;
    }
}

