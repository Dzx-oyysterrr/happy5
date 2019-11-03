package com.example.happy;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class List<S> extends ListActivity implements Runnable {

    private static final String TAG = "ALL";
    String data[] = {"one","two","three"};
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        //ListAdapter adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);

        //setListAdapter(adapter);
        Thread thread = new Thread(this);
        //thread.start();

        handler = new Handler(){

            /*public void handleMessage(@NonNull Message msg1) {
                if (msg1.what==7){
                    ArrayList<String> list2 = (ArrayList<String>) msg1.obj;
                    ListAdapter adapter1 = new ArrayAdapter<String>(List.this,android.R.layout.simple_list_item_1,(java.util.List<String>)list2);
                    setListAdapter(adapter1);
                }
                super.handleMessage(msg1);
            }*/

            public void handleMessage(@NonNull Message msg) {
                if (msg.what==7){
                    ArrayList<String> list2 = (ArrayList<String>) msg.obj;
                    //ArrayList<String> list1 = (ArrayList<String>) msg1.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(List.this,android.R.layout.simple_list_item_1,(java.util.List<String>)list2);
                    //ListAdapter adapter1 = new ArrayAdapter<String>(List.this,android.R.layout.simple_list_item_1,(java.util.List<String>)list1);
                    setListAdapter(adapter);
                    //setListAdapter(adapter1);
                }
                super.handleMessage(msg);
                //super.handleMessage(msg1);
            }
        };

    }
    @Override
    public void run() {
        //get data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> retlist = new ArrayList<String>();
        ArrayList<String> retlist1 = new ArrayList<String>();
        Document doc = null;
        try{
            doc = Jsoup.connect("https://soccer.hupu.com/table/England.html").get();
            Log.i(TAG,"TUN + ..." + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            Elements tds = table6.getElementsByTag("tr");

            for(int i=2; i<tds.size();i+=15){

                Element td1 = tds.get(i);
                Element td2= tds.get(i-2);
                Element td3= tds.get(i+1);
                Element td4= tds.get(i+2);
                Element td5= tds.get(i+3);
                Element td6= tds.get(i+4);
                Element td7= tds.get(i+5);
                Element td8= tds.get(i+6);
                Element td9= tds.get(i+7);
                Element td10= tds.get(i+12);

                String name = td1.text();
                String  rank = td2.text();
                String  num = td3.text();
                String  win = td4.text();
                String  draw = td5.text();
                String  lost = td6.text();
                String  gol = td7.text();
                String  beigol = td8.text();
                String  jin = td9.text();
                String  poi = td10.text();

                    Log.i(TAG, "rank = " + td1.text());
                    //retlist.add(name);
                    retlist.add(rank+" "+name+"    "+num+" "+win+" "+draw+" "+lost+" "+gol+" "+beigol+" "+jin+" "+poi);

            }

            /*for(int i =2 ; i<tds.size();i+=15){
                Element td1= tds.get(i);
                String  name = td1.text();
                Log.i(TAG,"rank1 = "  + td1.text());
                retlist1.add(name);
            }*/

        }catch(IOException e){
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        //Message msg1 = handler.obtainMessage(7);
        msg.obj = retlist;
       // msg1.obj=retlist1;
        handler.sendMessage(msg);
        //handler.sendMessage(msg1);

    }
}
