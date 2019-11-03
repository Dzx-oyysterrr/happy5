package com.example.happy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Test extends AppCompatActivity implements Runnable  {

    private static final String TAG = "ALL";
    Handler handler;
    String rank;
    private  String datas="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



        //kaiqi
        Thread t =new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 5){
                    Bundle str = (Bundle) msg.obj;
                    rank = str.getString("rank");
                    //使用
                    Log.i(TAG,"FINLA .. " + rank );
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test,menu);
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         if(item.getItemId()==R.id.rank_set){
            Intent list = new Intent(this,com.example.happy.List2Activity.class);
            startActivity(list);
        }else if(item.getItemId()==R.id.rank_set1) {
            Intent list = new Intent(this, com.example.happy.List3Activity.class);
            startActivity(list);
        }
        else if(item.getItemId()==R.id.rank_set3) {
            Intent list = new Intent(this, com.example.happy.List4Activity.class);
            startActivity(list);
        }
        else if(item.getItemId()==R.id.rank_set4) {
            Intent list = new Intent(this, com.example.happy.List5Activity.class);
            startActivity(list);
        }
        else if(item.getItemId()==R.id.rank_set5) {
            Intent list = new Intent(this, com.example.happy.List6Activity.class);
            startActivity(list);
        }
        return true;
    }

    public void openeng(View view){
        Intent intent = new Intent(this,List2Activity.class);
        startActivity(intent);
    }
    public void openspa(View view){
        Intent intent = new Intent(this, List3Activity.class);
        startActivity(intent);
    }
    public void openiti(View view){
        Intent intent = new Intent(this,List4Activity.class);
        startActivity(intent);
    }
    public void opengem(View view){
        Intent intent = new Intent(this,List5Activity.class);
        startActivity(intent);
    }
    public void openfan(View view){
        Intent intent = new Intent(this,List6Activity.class);
        startActivity(intent);
    }

    @Override
    public void run() {
        Log.i(TAG,"run.........");
        for (int i=1;i<6;i++){
            Log.i(TAG,"run = "+ i);
            try {
                Thread.sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Bundle bundle =new Bundle();
       Document doc = null;
        try {
            doc = Jsoup.connect("https://soccer.hupu.com/table/England.html").get();
            Log.i(TAG, "TUN + ..." + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            Elements trs = table6.getElementsByTag("tr");
            Elements ths = table6.getElementsByTag("th");
            Elements tds = table6.getElementsByTag("td");

            /*for (int j = 0; j < trs.size(); j++) {

                Element tr1 = trs.get(j);

                Log.i(TAG, "rank = " + tr1.text());
            }*/

            /*for(int i =2 ; i<tds.size();i+=15){
                Element td1= tds.get(i);
                Element td2= tds.get(i-2);
                Element td3= tds.get(i+1);
                Element td4= tds.get(i+2);
                Element td5= tds.get(i+3);
                Element td6= tds.get(i+4);
                Element td7= tds.get(i+5);
                Element td8= tds.get(i+6);
                Element td9= tds.get(i+7);
                Element td10= tds.get(i+12);
                Log.i(TAG,"rank = " + td1.text());
                Log.i(TAG,"排名 = " + td2.text());
                Log.i(TAG,"场次 = " + td3.text());
                Log.i(TAG,"胜 = " + td4.text());
                Log.i(TAG,"平 = " + td5.text());
                Log.i(TAG,"负 = " + td6.text());
                Log.i(TAG,"进球 = " + td7.text());
                Log.i(TAG,"失球 = " + td8.text());
                Log.i(TAG,"净胜球 = " + td9.text());
                Log.i(TAG,"积分 = " + td10.text());
                bundle.putString("rank", String.valueOf(td1));
            }*/
        }catch(IOException e){
            e.printStackTrace();
        }

            Message msg = handler.obtainMessage(5);
            msg.obj = bundle;
            handler.sendMessage(msg);

    }
    private String inputString2String(InputStream inputStream) throws IOException {

        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"UTF-8");
        for (; ; ){
            int rsz = in.read(buffer, 0 ,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer, 0,rsz);
        }
        return out.toString();
    }

}
