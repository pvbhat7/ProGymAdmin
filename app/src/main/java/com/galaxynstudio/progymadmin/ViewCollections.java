package com.galaxynstudio.progymadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.galaxynstudio.progymadmin.Dao.DatabaseClient;
import com.galaxynstudio.progymadmin.Model.PaymentData;
import com.galaxynstudio.progymadmin.Utils.Utils;

import java.util.List;

public class ViewCollections extends AppCompatActivity implements View.OnClickListener{

    Spinner year,month;
    Button viewCustomCollectionButton,viewTotalCollectionButton;
    TextView collectionAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collections);

        year=findViewById(R.id.year);
        month=findViewById(R.id.month);

        viewCustomCollectionButton=findViewById(R.id.viewCustomCollectionButton);
        viewTotalCollectionButton=findViewById(R.id.viewTotalCollectionButton);

        viewCustomCollectionButton.setOnClickListener(this);
        viewTotalCollectionButton.setOnClickListener(this);

        collectionAmount=findViewById(R.id.collectionAmount);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.viewCustomCollectionButton:
                Double collection=0.00;
                String yearr=((TextView)year.getSelectedView()).getText().toString().trim();
                String monthh=((TextView)month.getSelectedView()).getText().toString().trim();

                if(yearr.equals("Select Year") || monthh.equals("Select Month")){
                    collectionAmount.setText("Please select date & year");
                }else
                {
                    List<PaymentData> paymentDataList=DatabaseClient.getInstance(this).getAppDatabase().paymentDatadao().getAllPaymentData();
                    if(paymentDataList.size() > 0){
                        for (PaymentData p:paymentDataList){
                            String year_r=Utils.getYearStringFromDateString(p.getPaymentDate());
                            if(year_r.equals(yearr)){
                                String month_r=Utils.getMonthStringFromDateString(p.getPaymentDate());
                                if(month_r.equals(monthh)){
                                    collection=collection+p.getAmountPaid();
                                }
                            }

                        }
                    }
                    collectionAmount.setText("Rs. "+String.valueOf(collection));
                }


                break;
            case R.id.viewTotalCollectionButton:
                Double collection1=0.00;
                List<PaymentData> paymentDataList1=DatabaseClient.getInstance(this).getAppDatabase().paymentDatadao().getAllPaymentData();
                for(PaymentData p:paymentDataList1){
                    collection1=collection1+p.getAmountPaid();
                }
                collectionAmount.setText("Rs. "+String.valueOf(collection1));
                break;
        }

    }
}
