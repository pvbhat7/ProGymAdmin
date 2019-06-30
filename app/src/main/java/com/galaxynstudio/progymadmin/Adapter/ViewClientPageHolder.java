package com.galaxynstudio.progymadmin.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.galaxynstudio.progymadmin.R;

import org.w3c.dom.Text;


public class ViewClientPageHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {
    // View holder for list recycler view as we used in listview

    TextView packageName,packageStartDate,packageEndDate,packageFeesStatus,packageDaysRemaining,clientPackageAmountPaid,clientPackageAmountRemaining,list_PackagePaymentDate;
    Button list_payFeesButton,list_SendSmsRemainderButton,list_updatePackageButton;
    LinearLayout feeRemainingLayout,paymentDetailsLayout;
    private RecyclerView_OnClickListener.OnClickListener onClickListener;
    ImageView paidFullImage;

    public ViewClientPageHolder(View view) {
        super(view);
            initFields(view);
    }

    public void initFields(View view){
        packageName=view.findViewById(R.id.list_packageName);
        packageStartDate=view.findViewById(R.id.list_PackageStartDate);
        packageEndDate=view.findViewById(R.id.list_PackageEndDate);
        packageFeesStatus=view.findViewById(R.id.list_paymentStatus);
        packageDaysRemaining=view.findViewById(R.id.list_PackageDaysRemaining);
        clientPackageAmountPaid=view.findViewById(R.id.list_PackageAmountPaid);
        clientPackageAmountRemaining=view.findViewById(R.id.list_PackageAmountRemaining);
        list_PackagePaymentDate=view.findViewById(R.id.list_PackagePaymentDate);

        list_payFeesButton=view.findViewById(R.id.list_payFeesButton);
        list_SendSmsRemainderButton=view.findViewById(R.id.list_SendSmsRemainderButton);
        list_updatePackageButton=view.findViewById(R.id.list_UpdatePackageButton);

        list_payFeesButton.setOnClickListener(this);
        list_SendSmsRemainderButton.setOnClickListener(this);
        list_updatePackageButton.setOnClickListener(this);

        feeRemainingLayout=view.findViewById(R.id.feeRemainingLayout);
        paymentDetailsLayout=view.findViewById(R.id.paymentDetailsLayout);

        paidFullImage=view.findViewById(R.id.paidFullImage);
    }

    @Override
    public void onClick(View v) {

        // setting custom listener
        if (onClickListener != null) {
            onClickListener.OnItemClick(v, getAdapterPosition());

        }

    }

    // Setter for listener
    public void setClickListener(
            RecyclerView_OnClickListener.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}