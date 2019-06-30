package com.galaxynstudio.progymadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.galaxynstudio.progymadmin.Dao.DatabaseClient;
import com.galaxynstudio.progymadmin.Model.Client;
import com.galaxynstudio.progymadmin.Model.PackageDetails;
import com.galaxynstudio.progymadmin.R;
import com.galaxynstudio.progymadmin.SharedPrefManager;
import com.galaxynstudio.progymadmin.Utils.Utils;
import com.galaxynstudio.progymadmin.ViewClientProfile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Util;


public class ViewClientAdapter extends
        RecyclerView.Adapter<ViewClientHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private List<Client> clientList;
    private Context context;




    public ViewClientAdapter(Context context,
                             List<Client> clientList) {
        this.context = context;
        this.clientList = clientList;

    }

    @Override
    public int getItemCount() {
        return (null != clientList ? clientList.size() : 0);

    }

    @Override
    public void onBindViewHolder(ViewClientHolder viewClientHolder, int position) {

        final Client client = clientList.get(position);

        final ViewClientHolder holder = (ViewClientHolder) viewClientHolder;

        holder.clientNameList.setText(client.getName());
        if(client.getName().equals("Prashant Bhat"))
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.prashant));
        else if(client.getName().equals("Pranav Patil"))
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pranav));
        else if(client.getName().equals("Sujay Bhosale"))
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.sujay));
        else
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_person));
        List<PackageDetails> packageDetailsList=DatabaseClient.getInstance(context).getAppDatabase().packageDetailsDao().getPackageDetailsByClientId(client.getId());

        if(packageDetailsList.size() > 0){
            String packageName=(packageDetailsList.get(packageDetailsList.size()-1).getcPackage().getPackageName());
            holder.clientPackageList.setText(packageName);
            String startdate=(packageDetailsList.get(packageDetailsList.size()-1).getStartDate());
            holder.clientPackageStartdate.setText(startdate);

            int days=Utils.getDaysRemaining(startdate,packageName);
            if(days > 0)
                holder.clientPackageDaysLeft.setText(String.valueOf(days));
            else
                holder.clientPackageDaysLeft.setText(String.valueOf(Math.abs(days))+" days extra");

            holder.clientFeesStatus.setText((packageDetailsList.get(packageDetailsList.size()-1).getStatus()));
        }else
        {
            holder.pkg_notFound.setVisibility(View.VISIBLE);
            holder.pkg_details.setVisibility(View.GONE);
            holder.fee_status.setVisibility(View.GONE);
        }


        //Glide.with(context).load(model.getImage()).into(holder.image);

        // Implement click listener over layout
        holder.setClickListener(new RecyclerView_OnClickListener.OnClickListener() {

            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_root:

                        Intent i = new Intent(view.getContext(), ViewClientProfile.class);
                        // sending data to new activity
                        i.putExtra("clientId", client.getId());
                        SharedPrefManager.getInstance(context).setClientName(client.getName());
                        SharedPrefManager.getInstance(context).setClientMobile(client.getMobile());
                        SharedPrefManager.getInstance(context).setClientGender(client.getSex());
                        SharedPrefManager.getInstance(context).setClientId(client.getId());
                        view.getContext().startActivity(i);



                        break;

                    //case R.id.btn_add:
                        /*int counter = SharedPrefManager.getInstance(context).getCounter(model.getId()) + 1;
                        SharedPrefManager.getInstance(context).setCounter(model.getId(), counter);
                        holder.plusMinus.setVisibility(View.VISIBLE);
                        holder.addButton.setVisibility(View.GONE);
                        holder.counter.setText(String.valueOf(SharedPrefManager.getInstance(context).getCounter(model.getId())));
                        isProductExists(model.getId(),model,"add");
                        Log.i("Cart total :",String.valueOf(cartTotal));
                        //holder.cartTotalAmount.setText(String.valueOf(cartTotal));
                        cartTotal=0.0;*/
                        //getCartItemCount();
                        //cartTotal=cartTotal+50;

                       // break;

                    //case R.id.img_add:
                        /*int counter2 = SharedPrefManager.getInstance(context).getCounter(model.getId()) + 1;
                        SharedPrefManager.getInstance(context).setCounter(model.getId(), counter2);
                        holder.counter.setText(String.valueOf(SharedPrefManager.getInstance(context).getCounter(model.getId())));
                        isProductExists(model.getId(),model,"add");
                        Log.i("Cart total :",String.valueOf(cartTotal));
                        cartTotal=0.0;*/
                        //holder.cartTotalAmount.setText(String.valueOf(cartTotal));
                        //getCartItemCount();
                        //holder.cartTotalAmount.setText(String.valueOf(cartTotal));
                        //break;

                    //case R.id.img_remove:
                        /*int counter1 = SharedPrefManager.getInstance(context).getCounter(model.getId());
                        isProductExists(model.getId(),model,"remove");
                        Log.i("Cart total :",String.valueOf(cartTotal));
                        //holder.cartTotalAmount.setText(String.valueOf(cartTotal));
                        cartTotal=0.0;
                        //getCartItemCount();
                        //holder.cartTotalAmount.setText(String.valueOf(cartTotal));
                        if (counter1 == 1) {
                            SharedPrefManager.getInstance(context).setCounter(model.getId(), counter1 - 1);
                            holder.plusMinus.setVisibility(View.GONE);
                            holder.addButton.setVisibility(View.VISIBLE);
                        } else {
                            SharedPrefManager.getInstance(context).setCounter(model.getId(), counter1 - 1);
                            holder.counter.setText(String.valueOf(SharedPrefManager.getInstance(context).getCounter(model.getId())));
                        }*/

                      //  break;

                    /*case R.id.list_delete:
                        // remove selected item
                        leafyVegetableList.remove(position);
                        notifyItemRemoved(position);
                        break;*/

                }
            }

        });

    }


    @Override
    public ViewClientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new ViewClientHolder(view);
    }




}



