
package com.galaxynstudio.progymadmin.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxynstudio.progymadmin.AddClientPage;
import com.galaxynstudio.progymadmin.Model.ClientFeeRemainder;
import com.galaxynstudio.progymadmin.Model.PackageDetails;
import com.galaxynstudio.progymadmin.R;
import com.galaxynstudio.progymadmin.SharedPrefManager;
import com.galaxynstudio.progymadmin.Utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewClientPageAdapter extends
        RecyclerView.Adapter<ViewClientPageHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private List<PackageDetails> pd;
    private Context context;

    private Button btn_add, img_add, img_remove;
    private TextView item_count;
    private Boolean isProductExist;
    private Integer productQty;
    private Double cartTotal=0.0;


    public ViewClientPageAdapter(Context context,
                             List<PackageDetails> packageDetails) {
        this.context = context;
        this.pd = packageDetails;

    }

    @Override
    public int getItemCount() {
        return (null != pd ? pd.size() : 0);

    }

    @Override
    public ViewClientPageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientprofile, parent, false);
        return new ViewClientPageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewClientPageHolder viewClientPageHolder, int position) {
        final PackageDetails packageDetails=pd.get(position);
        final ViewClientPageHolder holder = (ViewClientPageHolder) viewClientPageHolder;

        holder.packageName.setText(packageDetails.getcPackage().getPackageName()+" ("+packageDetails.getcPackage().getPackageCategory()+")");
        holder.packageStartDate.setText(packageDetails.getStartDate());
        final Calendar cal=Utils.getEndDateFromStartDateUsingPackageName(packageDetails.getStartDate(),packageDetails.getcPackage().getPackageName());
        int day=cal.getTime().getDate();
        int month=cal.getTime().getMonth()+1;
        int year=cal.getTime().getYear()+1900;
        String endDate=day+"/"+month+"/"+year;
        holder.packageEndDate.setText(endDate);
        holder.packageFeesStatus.setText(packageDetails.getStatus());
        holder.packageDaysRemaining.setText(String.valueOf(Utils.getDaysRemaining(packageDetails.getStartDate(),packageDetails.getcPackage().getPackageName())));
        holder.clientPackageAmountPaid.setText(String.valueOf(packageDetails.getAmountPaid()));
        holder.clientPackageAmountRemaining.setText(String.valueOf(packageDetails.getcPackage().getFees()-packageDetails.getAmountPaid()));

        holder.setClickListener( new RecyclerView_OnClickListener.OnClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.list_payFeesButton:
                        break;
                    case R.id.list_UpdatePackageButton:
                        Intent intent=new Intent(context, AddClientPage.class);
                        intent.putExtra("client",true);
                        intent.putExtra("clientName",SharedPrefManager.getInstance(context).getClientName());
                        intent.putExtra("clientId",SharedPrefManager.getInstance(context).getClientId());
                        intent.putExtra("clientGender",SharedPrefManager.getInstance(context).getClientGender());
                        intent.putExtra("clientPackageStartDate",packageDetails.getStartDate());
                        intent.putExtra("Parentcommand","updatePkgToExistingClient");
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.list_SendSmsRemainderButton:
                        sendFeeRemainders(packageDetails);
                        break;
                }
            }
        });



    }

    private void sendFeeRemainders(PackageDetails packageDetails) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context , new String[]{Manifest.permission.SEND_SMS}, 1);
            return;
        }else{
            ClientFeeRemainder clientFeeRemainder=new ClientFeeRemainder();
            clientFeeRemainder.setPackageName(SharedPrefManager.getInstance(context).getClientName());
            clientFeeRemainder.setMobile(SharedPrefManager.getInstance(context).getClientMobile());
            clientFeeRemainder.setPackageName(packageDetails.getcPackage().getPackageName());
            Calendar cal=Utils.getEndDateFromStartDateUsingPackageName(packageDetails.getStartDate(),packageDetails.getcPackage().getPackageName());
            int day=cal.getTime().getDate();
            int month=cal.getTime().getMonth()+1;
            int year=cal.getTime().getYear()+1900;
            String endDate=day+"/"+month+"/"+year;
            clientFeeRemainder.setPackageEndDate(endDate);
            clientFeeRemainder.setRemainingFees(packageDetails.getcPackage().getFees()-packageDetails.getAmountPaid());
            final ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Sending SMS Remainder");
            pd.show();
            Utils.sendSms(clientFeeRemainder,context);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    pd.dismiss();
                }
            }, 2500);
            Toast.makeText(context,"SMS Remainder sent successfully",Toast.LENGTH_LONG).show();
        }
    }


}



