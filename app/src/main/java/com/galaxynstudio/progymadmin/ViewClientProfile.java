package com.galaxynstudio.progymadmin;

import android.content.Intent;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxynstudio.progymadmin.Adapter.ViewClientAdapter;
import com.galaxynstudio.progymadmin.Adapter.ViewClientPageAdapter;
import com.galaxynstudio.progymadmin.Adapter.ViewClientPageHolder;
import com.galaxynstudio.progymadmin.Dao.DatabaseClient;
import com.galaxynstudio.progymadmin.Model.Client;

import java.util.List;

public class ViewClientProfile extends AppCompatActivity implements View.OnClickListener{

    private static RecyclerView listRecyclerView;
    private static ViewClientPageAdapter adapter;
    TextView name,mobile,gender,accountStatus;
    ImageView imageView;
    Button singleProfile_addpackage;
    Client client=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_profile);

        name=findViewById(R.id.singleProfile_name);
        mobile=findViewById(R.id.singleProfile_mobile);
        gender=findViewById(R.id.singleProfile_gender);
        accountStatus=findViewById(R.id.singleProfile_accountStatus);
        imageView=findViewById(R.id.singleProfile_image);
        singleProfile_addpackage=findViewById(R.id.singleProfile_addpackage);
        singleProfile_addpackage.setOnClickListener(this);

        Intent i = getIntent();
        // getting attached intent data
        int clientId = i.getIntExtra("clientId",0);
        client= DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().clientDao().getClient(clientId);

        name.setText(client.getName());
        mobile.setText(client.getMobile());
        gender.setText(client.getSex());
        accountStatus.setText(client.getClientStatus());
        if(client.getName().equals("Prashant Bhat"))
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.prashant));
        else if(client.getName().equals("Pranav Patil"))
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pranav));
        else if(client.getName().equals("Sujay Bhosale"))
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sujay));
        else
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person));
        if(client != null){
            listRecyclerView = (RecyclerView)findViewById(R.id.viewClientProfile_recyclerview);
            listRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// for
            listRecyclerView.setHasFixedSize(true);
            adapter = new ViewClientPageAdapter(this, client.getPackageDetails());
            listRecyclerView.setAdapter(adapter);// set adapter on recyclerview
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.singleProfile_addpackage:
                Intent intent=new Intent(this,AddClientPage.class);
                intent.putExtra("client",true);
                intent.putExtra("clientName",client.getName());
                intent.putExtra("clientId",client.getId());
                intent.putExtra("clientGender",client.getSex());
                intent.putExtra("Parentcommand","addPkgToExistingClient");
                startActivity(intent);
                break;
        }
    }
}
