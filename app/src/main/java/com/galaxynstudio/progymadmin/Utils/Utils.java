package com.galaxynstudio.progymadmin.Utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.galaxynstudio.progymadmin.Model.ClientFeeRemainder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Utils {

    public static final long MILLISECONDS_IN_DAY = (long) (1000 * 60 * 60 * 24);
    public static int daysDiff(Date from, Date until) {
        Calendar cFrom = Calendar.getInstance();
        cFrom.setTime(getDateAtNoon(from));
        int cFromDSTOffset = cFrom.get(Calendar.DST_OFFSET);
        long cFromTime = cFrom.getTime().getTime() + (long) cFromDSTOffset;
        Calendar cUntil = Calendar.getInstance();
        cUntil.setTime(getDateAtNoon(until));
        int cUntilDSTOffset = cUntil.get(Calendar.DST_OFFSET);
        long cUntilTime = cUntil.getTime().getTime() + (long) cUntilDSTOffset;
        return (int) ((cUntilTime - cFromTime) / MILLISECONDS_IN_DAY);
    }

    public static Date getDateAtNoon(Date datetime) {
        if (datetime == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getDaysRemaining(String startdate, String packageName) {
        int days = 0;
        String[] str=startdate.split("/");

        //month is -1
        // year is -> 0 means 1900 , 117 means 2017

        Date startDate=new Date(Integer.parseInt(str[2])-1900,Integer.parseInt(str[1])-1,Integer.parseInt(str[0]));

        //if startdata before current date then calculate days remaining else assign total days

        if(startDate.before(new Date())){
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);

            // formula total days - (startdate & currentdate difference)
            if(packageName.equals("1 Month")){
                cal.add(Calendar.MONTH, 1);
                days=30-Utils.daysDiff(startDate,new Date());
            }else if(packageName.equals("3 Month")){
                days=90-Utils.daysDiff(startDate,new Date());
            }else if(packageName.equals("6 Month")){
                days=180-Utils.daysDiff(startDate,new Date());
            }else if(packageName.equals("1 Year")){
                days=365-Utils.daysDiff(startDate,new Date());
            }

        }
        else{
            //set total days
            if(packageName.equals("1 Month")){
                days=30;
            }else if(packageName.equals("3 Month")){
                days=90;
            }else if(packageName.equals("6 Month")){
                days=180;
            }else if(packageName.equals("1 Year")){
                days=365;
            }
        }
        return days;
    }

    public static Calendar getEndDateFromStartDateUsingPackageName(String sd,String packageName){
        String [] str=sd.split("/");
        Date startDate=new Date(Integer.parseInt(str[2])-1900,Integer.parseInt(str[1])-1,Integer.parseInt(str[0]));

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
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

        return cal;
    }

    public static void sendSms(ClientFeeRemainder c, Context context){
        SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI;
        String SENT = "SMS_SENT";
        sentPI = PendingIntent.getBroadcast(context, 0,new Intent(SENT), 0);
        sms.sendTextMessage(c.getMobile(), null, "ProGym Kalamba,Kolhapur\nHello "+c.getName()+",\nYour Package ( "+c.getPackageName()+" ) will expire on "+c.getPackageEndDate()+". Kindly pay remaining fees :Rs. "+c.getRemainingFees()+"/-", sentPI, null);
    }


    public static String getMonthStringFromDateString(String paymentDate) {
        String month=null;
        String date=paymentDate;
        String[] str=date.split("/");

        int monthInt=Integer.parseInt(str[1]);
        if (monthInt == 1)
            month="January";
        else if (monthInt == 2)
            month="February";
        else if (monthInt == 3)
            month="March";
        else if (monthInt == 4)
            month="April";
        else if (monthInt == 5)
            month="May";
        else if (monthInt == 6)
            month="June";
        else if (monthInt == 7)
            month="July";
        else if (monthInt == 8)
            month="August";
        else if (monthInt == 9)
            month="September";
        else if (monthInt == 10)
            month="October";
        else if (monthInt == 11)
            month="November";
        else if (monthInt == 12)
            month="December";

        return month;
    }

    public static String getYearStringFromDateString(String paymentDate) {
        String date=paymentDate;
        String[] str=date.split("/");
        int year=Integer.parseInt(str[2]);
        return String.valueOf(year);
    }
}
