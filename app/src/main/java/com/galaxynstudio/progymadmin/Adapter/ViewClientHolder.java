package com.galaxynstudio.progymadmin.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.galaxynstudio.progymadmin.R;


public class ViewClientHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {
    // View holder for list recycler view as we used in listview
    TextView clientNameList,clientPackageList,clientPackageStartdate,clientPackageDaysLeft,clientFeesStatus;
    public RelativeLayout listLayout;
    LinearLayout pkg_details,fee_status,pkg_notFound;
    ImageView image;



    private RecyclerView_OnClickListener.OnClickListener onClickListener;

    public ViewClientHolder(View view) {
        super(view);

        this.clientNameList=(TextView)itemView.findViewById(R.id.clientNameList);
        this.clientPackageList=(TextView)itemView.findViewById(R.id.clientPackageList);
        this.clientPackageStartdate=(TextView)itemView.findViewById(R.id.clientPackageStartdate);
        this.clientPackageDaysLeft=(TextView)itemView.findViewById(R.id.clientPackageDaysLeft);
        this.clientFeesStatus=(TextView)itemView.findViewById(R.id.clientFeesStatus);


        this.listLayout = (RelativeLayout) view.findViewById(R.id.rl_root);
        this.image=view.findViewById(R.id.profile_pic);
        this.listLayout.setOnClickListener(this);

        pkg_details=view.findViewById(R.id.pkg_details);
        fee_status=view.findViewById(R.id.pkg_fee_status);
        pkg_notFound=view.findViewById(R.id.noPackageFound);
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