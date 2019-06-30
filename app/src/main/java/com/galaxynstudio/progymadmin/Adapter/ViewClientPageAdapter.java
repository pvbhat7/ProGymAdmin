
package com.galaxynstudio.progymadmin.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxynstudio.progymadmin.AddClientPage;
import com.galaxynstudio.progymadmin.Dao.DatabaseClient;
import com.galaxynstudio.progymadmin.Model.ClientFeeRemainder;
import com.galaxynstudio.progymadmin.Model.PackageDetails;
import com.galaxynstudio.progymadmin.Model.PaymentData;
import com.galaxynstudio.progymadmin.R;
import com.galaxynstudio.progymadmin.SharedPrefManager;
import com.galaxynstudio.progymadmin.Utils.Utils;
import com.galaxynstudio.progymadmin.ViewClientProfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewClientPageAdapter extends
        RecyclerView.Adapter<ViewClientPageHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private List<PackageDetails> pd;
    private Context context;




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

        if(packageDetails.getStatus().equals("Paid")){
            holder.feeRemainingLayout.setVisibility(View.GONE);
            holder.list_payFeesButton.setVisibility(View.GONE);
            holder.list_SendSmsRemainderButton.setVisibility(View.GONE);
            holder.paymentDetailsLayout.setVisibility(View.VISIBLE);
            // get payment data
            List<PaymentData> paymentDataList=DatabaseClient.getInstance(context).getAppDatabase().paymentDatadao().getPaymentDetailsUsingPackageId(packageDetails.getId());
            holder.list_PackagePaymentDate.setText(paymentDataList.get(paymentDataList.size()-1).getPaymentDate());
            holder.paidFullImage.setVisibility(View.VISIBLE);

        }
        holder.setClickListener( new RecyclerView_OnClickListener.OnClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.list_payFeesButton:
                        showAddItemDialog(context,packageDetails);
                        break;
                    case R.id.list_UpdatePackageButton:
                        Intent intent=new Intent(context, AddClientPage.class);
                        intent.putExtra("client",true);
                        intent.putExtra("clientName",SharedPrefManager.getInstance(context).getClientName());
                        intent.putExtra("clientId",SharedPrefManager.getInstance(context).getClientId());
                        intent.putExtra("clientGender",SharedPrefManager.getInstance(context).getClientGender());
                        intent.putExtra("clientPackageStartDate",packageDetails.getStartDate());
                        intent.putExtra("clientPackageDetailsId",packageDetails.getId());
                        intent.putExtra("clientPackageCurrent",packageDetails.getcPackage().getPackageName()+" ("+ (packageDetails.getcPackage().getPackageCategory()+" )"));
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

    private void showAddItemDialog(Context c, final PackageDetails packageDetails) {
        final Double total=packageDetails.getcPackage().getFees();
        final Double paid=packageDetails.getAmountPaid();
        Double remaining=packageDetails.getcPackage().getFees()-packageDetails.getAmountPaid();
        String packageName=packageDetails.getcPackage().getPackageName()+" ("+packageDetails.getcPackage().getPackageCategory()+")";
        final EditText taskEditText = new EditText(c);
        taskEditText.setHint("Enter Fee Amount");
        taskEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        taskEditText.setText(String.valueOf(packageDetails.getcPackage().getFees()-packageDetails.getAmountPaid()));
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(packageName+" Fees :Rs."+total)
                .setMessage("Fees Paid :Rs."+paid+"\nFee Pending :Rs."+remaining)
                .setView(taskEditText)
                .setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String feeAmountPaying = String.valueOf(taskEditText.getText());
                        packageDetails.setAmountPaid(paid+Double.valueOf(feeAmountPaying));
                        Double clientFeeSt=paid+Double.valueOf(feeAmountPaying);
                        if(clientFeeSt.equals(total))
                            packageDetails.setStatus("Paid");
                        else
                            packageDetails.setStatus("Partially Paid");

                        DatabaseClient.getInstance(context).getAppDatabase().packageDetailsDao().update(packageDetails);
                        PaymentData paymentData=new PaymentData();
                        paymentData.setAmountPaid(Double.valueOf(feeAmountPaying));
                        Date date = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = dateFormat.format(date);
                        paymentData.setPaymentDate(strDate);
                        paymentData.setPackageId(packageDetails.getId());
                        DatabaseClient.getInstance(context).getAppDatabase().paymentDatadao().insert(paymentData);
                        Toast.makeText(context, "Payment Done", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, ViewClientProfile.class);
                        intent.putExtra("clientId",SharedPrefManager.getInstance(context).getClientId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void sendFeeRemainders(PackageDetails packageDetails) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context , new String[]{Manifest.permission.SEND_SMS}, 1);
            return;
        }else{
            ClientFeeRemainder clientFeeRemainder=new ClientFeeRemainder();
            clientFeeRemainder.setName(SharedPrefManager.getInstance(context).getClientName());
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



