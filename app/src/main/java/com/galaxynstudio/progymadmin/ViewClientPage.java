package com.galaxynstudio.progymadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxynstudio.progymadmin.Adapter.ViewClientAdapter;
import com.galaxynstudio.progymadmin.Dao.DatabaseClient;
import com.galaxynstudio.progymadmin.Model.Client;

import java.util.List;

public class ViewClientPage extends AppCompatActivity {

    TextView clientdata;
    private static RecyclerView listRecyclerView;
    private static ViewClientAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_page);

        List<Client> clients= DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .clientDao().getAllClients();

        if(clients.size() != 0){
            listRecyclerView = (RecyclerView)findViewById(R.id.cartSummary_recyclerview);
            listRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// for
            listRecyclerView.setHasFixedSize(true);
            adapter = new ViewClientAdapter(getApplicationContext(), clients);
            listRecyclerView.setAdapter(adapter);// set adapter on recyclerview
            adapter.notifyDataSetChanged();
        }
    }
}
