package com.example.happy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class List3Activity extends List  implements Runnable{

    private static final String TAG = "ALL";
    Handler handler;
    private ArrayList<HashMap<String, String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    String rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list2);
        initListView();

        adapter myAdapter = new adapter(this,R.layout.activity_list2,listItems);
        this.setListAdapter(myAdapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what==7){
                    ArrayList<HashMap<String, String>> list2 = (ArrayList<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(List3Activity.this, list2, // listItems数据源
                            R.layout.activity_list3, // ListItem的XML布局实现
                            new String[] { "ItemTitle","ItemDetail", "ItemDetail2" ,"ItemDetail3","ItemDetail4","ItemDetail5","ItemDetail6","ItemDetail7","ItemDetail8","ItemDetail9"},
                            new int[] { R.id.itemTitle, R.id.itemDetail,R.id.itemDetail2, R.id.itemDetail3,R.id.itemDetail4,
                                    R.id.itemDetail5,R.id.itemDetail6,R.id.itemDetail7,R.id.itemDetail8,R.id.itemDetail9}
                    );

                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);;
            }
        };

    }

    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        // 生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems, // listItems数据源
                R.layout.activity_list2, // ListItem的XML布局实现
                new String[] { "ItemTitle","ItemDetail","ItemDetail2","ItemDetail3","ItemDetail4","ItemDetail5","ItemDetail6","ItemDetail7","ItemDetail8","ItemDetail9"},
                new int[] { R.id.itemTitle, R.id.itemDetail,R.id.itemDetail2, R.id.itemDetail3,R.id.itemDetail4,
                        R.id.itemDetail5,R.id.itemDetail6,R.id.itemDetail7,R.id.itemDetail8,R.id.itemDetail9}
        );
    }
    public void run() {
        //get data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<HashMap<String, String>> retlist = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try{
            doc = Jsoup.connect("https://soccer.hupu.com/table/Spain.html").get();
            Log.i(TAG,"TUN + ..." + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            Elements tds = table6.getElementsByTag("td");

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

                Log.i(TAG, "rank = " + td1.text()+ td2.text()+ td3.text()+ td4.text()+ td5.text()+ td6.text()+ td7.text()+ td8.text()+ td9.text()+ td10.text());
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",name);
                map.put("ItemDetail",rank);
                map.put("ItemDetail2",num);
                map.put("ItemDetail3",win);
                map.put("ItemDetail4",draw);
                map.put("ItemDetail5",lost);
                map.put("ItemDetail6",gol);
                map.put("ItemDetail7",beigol);
                map.put("ItemDetail8",jin);
                map.put("ItemDetail9",poi);

                retlist.add(map);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = retlist;
        handler.sendMessage(msg);
    }

}