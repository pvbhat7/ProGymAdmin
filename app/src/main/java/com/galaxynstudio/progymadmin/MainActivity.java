package com.galaxynstudio.progymadmin;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.galaxynstudio.progymadmin.Dao.DatabaseClient;
import com.galaxynstudio.progymadmin.Model.CPackage;
import com.galaxynstudio.progymadmin.Model.Client;
import com.galaxynstudio.progymadmin.Model.ClientFeeRemainder;
import com.galaxynstudio.progymadmin.Model.PackageDetails;
import com.galaxynstudio.progymadmin.Model.PaymentData;
import com.galaxynstudio.progymadmin.Utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button addclient,viewclients,sendFeesRemainders,viewCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
/*
        List<Client> clients=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .clientDao().getAllClients();
        Toast.makeText(this, clients.get(0).toString(), Toast.LENGTH_SHORT).show();*/

        addclient=findViewById(R.id.addClient);
        viewclients=findViewById(R.id.viewClients);
        sendFeesRemainders=findViewById(R.id.sendFeesRemainders);
        viewCollection=findViewById(R.id.viewCollections);

        viewCollection.setOnClickListener(this);

        addclient.setOnClickListener(this);
        viewclients.setOnClickListener(this);
        sendFeesRemainders.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Client> clients= DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .clientDao().getAllClients();
        if(clients.size() == 0)
        addClients();
    }

    public void addClients(){
        Client client=new Client();
        client.setName("Prashant Bhat");
        client.setMobile("8796238220");
        client.setSex("male");
        client.setClientStatus("active");

        Long cId_1=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().clientDao().insert(client);

List<PackageDetails> packageDetailsList=new ArrayList<>();

        PackageDetails packageDetails1=new PackageDetails();
        packageDetails1.setStartDate("10/06/2019");
        packageDetails1.setStatus("Not Paid");
        CPackage cPackage=new CPackage();
        cPackage.setSex("male");
        cPackage.setPackageCategory("GYM");
        cPackage.setPackageName("1 Month");
        cPackage.setDays(30);
        cPackage.setFees(new Double(300.00));
        packageDetails1.setcPackage(cPackage);
        packageDetails1.setAmountPaid(0.00);
        packageDetails1.setCliendId(cId_1.intValue());
        packageDetailsList.add(packageDetails1);
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().insert(packageDetails1);

        PackageDetails packageDetails2=new PackageDetails();
        packageDetails2.setStartDate("10/06/2019");
        packageDetails2.setStatus("Paid");
        CPackage cPackage2=new CPackage();
        cPackage2.setSex("male");
        cPackage2.setPackageCategory("GYM");
        cPackage2.setPackageName("1 Month");
        cPackage2.setDays(30);
        cPackage2.setFees(new Double(300.00));
        packageDetails2.setcPackage(cPackage2);
        packageDetails2.setAmountPaid(300.00);
        packageDetails2.setCliendId(cId_1.intValue());
        Long pkgId=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().insert(packageDetails2);
        PaymentData paymentData=new PaymentData();
        paymentData.setPackageId(pkgId.intValue());
        paymentData.setAmountPaid(300.0);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        paymentData.setPaymentDate(strDate);
        DatabaseClient.getInstance(this).getAppDatabase().paymentDatadao().insert(paymentData);

        PackageDetails packageDetails3=new PackageDetails();
        packageDetails3.setStartDate("10/06/2019");
        packageDetails3.setStatus("Not Paid");
        CPackage cPackage3=new CPackage();
        cPackage3.setSex("male");
        cPackage3.setPackageCategory("GYM");
        cPackage3.setPackageName("1 Month");
        cPackage3.setDays(30);
        cPackage3.setFees(new Double(300.00));
        packageDetails3.setcPackage(cPackage3);
        packageDetails3.setAmountPaid(0.00);
        packageDetails3.setCliendId(cId_1.intValue());
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().insert(packageDetails3);


        Client client1=new Client();
        client1.setName("Pranav Patil");
        client1.setMobile("8796655176");
        client1.setSex("male");
        client1.setClientStatus("active");
        Long cId_2=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().clientDao().insert(client1);

        PackageDetails packageDetails12=new PackageDetails();
        packageDetails12.setStartDate("31/05/2019");
        packageDetails12.setStatus("Not Paid");
        CPackage cPackage1=new CPackage();
        cPackage1.setSex("male");
        cPackage1.setPackageCategory("GYM");
        cPackage1.setPackageName("1 Month");
        cPackage1.setDays(30);
        cPackage1.setFees(new Double(300.00));
        packageDetails12.setcPackage(cPackage1);
        packageDetails12.setAmountPaid(0.00);
        packageDetails12.setCliendId(cId_2.intValue());
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().insert(packageDetails12);


        PackageDetails packageDetails123=new PackageDetails();
        packageDetails123.setStartDate("31/05/2019");
        packageDetails123.setStatus("Not Paid");
        CPackage cPackage133=new CPackage();
        cPackage133.setSex("male");
        cPackage133.setPackageCategory("GYM");
        cPackage133.setPackageName("1 Month");
        cPackage133.setDays(30);
        cPackage133.setFees(new Double(300.00));
        packageDetails123.setcPackage(cPackage133);
        packageDetails123.setAmountPaid(0.00);
        packageDetails123.setCliendId(cId_2.intValue());
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().insert(packageDetails123);



        //client 3
        Client client3=new Client();
        client3.setName("Sujay Bhosale");
        client3.setMobile("9762615487");
        client3.setSex("male");
        client3.setClientStatus("active");
        Long cId_3=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().clientDao().insert(client3);


        PackageDetails packageDetails1233=new PackageDetails();
        packageDetails1233.setStartDate("31/05/2019");
        packageDetails1233.setStatus("Not Paid");
        CPackage cPackage14=new CPackage();
        cPackage14.setSex("male");
        cPackage14.setPackageCategory("GYM");
        cPackage14.setPackageName("1 Month");
        cPackage14.setDays(30);
        cPackage14.setFees(new Double(300.00));
        packageDetails1233.setcPackage(cPackage14);
        packageDetails1233.setAmountPaid(0.00);
        packageDetails1233.setCliendId(cId_3.intValue());
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().insert(packageDetails1233);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addClient:
                Intent intent=new Intent(this,AddClientPage.class);
                startActivity(intent);
                break;
            case R.id.viewClients:
                Intent intent1=new Intent(this,ViewClientPage.class);
                startActivity(intent1);
                break;

            case R.id.sendFeesRemainders:
                sendFeeRemainderFunction();
                break;
            case R.id.viewCollections:
                Intent intent3=new Intent(this,ViewCollections.class);
                startActivity(intent3);
                break;

        }
    }

    private void sendFeeRemainderFunction() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                return;
            }else{
                final ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("Sending SMS Remainder");
                pd.show();

                SmsManager sms = SmsManager.getDefault();
                PendingIntent sentPI;
                String SENT = "SMS_SENT";
                sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
                List<ClientFeeRemainder> pendingFeeClientList=getClientsMobileWithUnpaidFeesStatus();
                if(pendingFeeClientList.size() > 0){
                    for(ClientFeeRemainder c:pendingFeeClientList){
                        sms.sendTextMessage(c.getMobile(), null, "ProGym Kalamba,Kolhapur\nHello "+c.getName()+",\nYour Package "+c.getPackageName()+" will expire on "+c.getPackageEndDate()+". Kindly pay fees before it.", sentPI, null);
                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pd.dismiss();
                    }
                }, 2500);
                Toast.makeText(this, "SMS Remainders sent successfully", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public List<ClientFeeRemainder> getClientsMobileWithUnpaidFeesStatus(){

        List<ClientFeeRemainder> remainderList=new ArrayList<>();
        List<Client> clients= DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .clientDao().getAllClients();
        if(clients.size() > 0){
            for(Client c:clients){
                List<PackageDetails> packageDetails=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().packageDetailsDao().getPackageDetailsByClientId(c.getId());
for(PackageDetails p:packageDetails){
    if(p.getStatus().equals("Not Paid")){
        ClientFeeRemainder remainder=new ClientFeeRemainder();
        remainder.setName(c.getName());
        remainder.setMobile("+91"+c.getMobile());

        remainder.setPackageName(p.getcPackage().getPackageName()+" "+p.getcPackage().getPackageCategory());

        String startdate=p.getStartDate();
        String[] str=startdate.split("/");

        //month is -1
        // year is -> 0 means 1900 , 117 means 2017

        Date startDate=new Date(Integer.parseInt(str[2])-1900,Integer.parseInt(str[1])-1,Integer.parseInt(str[0]));

        //if startdata before current date then calculate days remaining else assign total days

        if(startDate.before(new Date())){
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);



            String packageName=p.getcPackage().getPackageName();
            // formula total days - (startdate & currentdate difference)
            int days=0;
            if(packageName.equals("1 Month")){
                cal.add(Calendar.MONTH, 1);
                days=30- Utils.daysDiff(startDate,new Date());
            }else if(packageName.equals("3 Month")){
                cal.add(Calendar.MONTH, 3);
                days=90-Utils.daysDiff(startDate,new Date());
            }else if(packageName.equals("6 Month")){
                cal.add(Calendar.MONTH, 6);
                days=180-Utils.daysDiff(startDate,new Date());
            }else if(packageName.equals("1 Year")){
                cal.add(Calendar.MONTH, 12);
                days=365-Utils.daysDiff(startDate,new Date());
            }

            if(days < 4)
            {
                remainder.setDaysLeft(days);
                remainder.setPackageEndDate(cal.getTime().getDate()+"/"+cal.getTime().getMonth()+1+"/"+cal.getTime().getYear()+1900);
                remainderList.add(remainder);
            }
        }
    }
}


            }// end enhanced for loop
        }
        return remainderList;
    }
}
