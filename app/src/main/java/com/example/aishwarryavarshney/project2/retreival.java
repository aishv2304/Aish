package com.example.aishwarryavarshney.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class retreival extends AppCompatActivity implements AsyncResponse,View.OnClickListener{
    Spinner s1, s2, s3, s4;

    @Override
    public void onClick(View v) {
        if (location != null && url != null && url1 != null) {
            String urlll;
            switch (v.getId()) {
                case R.id.button2:
                    Intent int1=new Intent(retreival.this,Main2Activity.class);

                    startActivity(int1);
                    //urlll = "?url1=" + url + "&url2=" + url1;

                    break;


                case R.id.button3: Intent int2=new Intent(retreival.this,MapsActivity.class);

                    startActivity(int2);


            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"plz select",Toast.LENGTH_LONG).show();
        }
    }

    List l = new ArrayList<data>();
    Button b3, b2;
    String location = null, url = null, url1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreival);
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        s4 = (Spinner) findViewById(R.id.spinner4);


        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        PostResponseAsyncTask task = new PostResponseAsyncTask((AsyncResponse) this);
        task.execute("https://ruralroadinfo.000webhostapp.com/map.php");


        b2.setOnClickListener(this);
        b3.setOnClickListener(this);


        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String stat = s1.getSelectedItem().toString();
                String text = s2.getSelectedItem().toString();
                locality(stat, text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String stat = s1.getSelectedItem().toString();
                String text = s2.getSelectedItem().toString();
                String text2 = s3.getSelectedItem().toString();
                location(stat, text, text2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String stat = s1.getSelectedItem().toString();
                String text = s2.getSelectedItem().toString();
                String text2 = s3.getSelectedItem().toString();
                String text3 = s4.getSelectedItem().toString();
                imgurl(stat, text, text2, text3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void imgurl(String stat, String text, String text2, String text3) {

        Iterator<data> it = l.iterator();
        while (it.hasNext()) {
            data of = it.next();
            if ((of.getState()).equals(stat) && (of.getCity()).equals(text) && (of.getLocality()).equals(text2) && (of.getLocation()).equals(text3)) {
                {
                    finaldata.setState(stat);
                    finaldata.setCity(text);
                    finaldata.setLocality(text2);
                    finaldata.setLocation(text3);
                    finaldata.setUrl("?url1="+of.getUrl1()+"&url2="+of.getUrl2());
                    location = of.getLocation();

                    url = of.getUrl1();
                    url1 = of.getUrl2();
                }

            }


        }
    }
    String d[] = {};

    @Override
    public void processFinish (String s){
        d = s.split(";");


        for (int i = 0; i < d.length; i++) {
            String ss[] = d[i].split(",");
            data obj = new data();
            obj.setState(ss[0]);
            obj.setCity(ss[1]);
            obj.setLocality(ss[2]);
            obj.setLocation(ss[3] + "," + ss[4]);
            obj.setUrl1(ss[5]);
            obj.setUrl2(ss[6]);
            l.add(obj);

        }
        Set<String> stt = new HashSet<>();
        Iterator<data> it = l.iterator();
        while (it.hasNext()) {
            stt.add(it.next().getState());

        }
        List p = new ArrayList(stt);
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, p);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(a);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = s1.getSelectedItem().toString();
                city(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();


    }

    private void city (String text){
        HashSet<String> stt = new HashSet<String>();
        Iterator<data> it = l.iterator();
        while (it.hasNext()) {
            data of = it.next();
            if ((of.getState()).equals(text)) {
                stt.add(of.getCity());
            }

        }
        List p = new ArrayList(stt);
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, p);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(a);

    }

    private void locality(String text, String text1){
        HashSet<String> stt = new HashSet<String>();
        Iterator<data> it = l.iterator();
        while (it.hasNext()) {
            data of = it.next();
            if ((of.getState()).equals(text) && (of.getCity()).equals(text1)) {
                stt.add(of.getLocality());
            }

        }
        List p = new ArrayList(stt);
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, p);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setAdapter(a);
    }
    private void location (String text, String text1, String text3){
        HashSet<String> stt = new HashSet<String>();
        Iterator<data> it = l.iterator();
        while (it.hasNext()) {
            data of = it.next();
            if ((of.getState()).equals(text) && (of.getCity()).equals(text1) && (of.getLocality()).equals(text3)) {
                stt.add(of.getLocation());
            }

        }
        List p = new ArrayList(stt);
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, p);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s4.setAdapter(a);
    }


}
