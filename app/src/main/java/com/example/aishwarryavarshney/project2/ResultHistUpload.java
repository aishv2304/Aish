package com.example.aishwarryavarshney.project2;

import android.content.Context;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ResultHistUpload extends AppCompatActivity implements AsyncResponse{
    Spinner s1, s2, s3, s4;
    Button resolve,buttonseeLocation;
    List l = new ArrayList<data>();
    PostResponseAsyncTask task = new PostResponseAsyncTask((AsyncResponse) this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_hist_upload);
        s1 = (Spinner) findViewById(R.id.states);
        s2 = (Spinner) findViewById(R.id.cities);
        s3 = (Spinner) findViewById(R.id.localities);
        s4 = (Spinner) findViewById(R.id.latlongi);
        resolve=(Button)findViewById(R.id.buttonResolved);
        buttonseeLocation=(Button)findViewById(R.id.buttonseeLocation);

        task.execute("https://ruralroadinfo.000webhostapp.com/map.php");
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
                finaldata.setState(stat);
                finaldata.setCity(text);
                finaldata.setLocality(text2);
                finaldata.setLocation(text3);
                //imgurl(stat, text, text2, text3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent int1=new Intent(ResultHistUpload.this,ResolvedIssuesUpdate.class);

                startActivity(int1);*/
                if(s1.getSelectedItem().toString()!="" || s2.getSelectedItem().toString()!="" || s3.getSelectedItem().toString()!="" ||s4.getSelectedItem().toString()!="")
                {
                    String stat_res = s1.getSelectedItem().toString();
                    String city_res = s2.getSelectedItem().toString();
                    String locality_res = s3.getSelectedItem().toString();
                    String loc[] = s4.getSelectedItem().toString().split(",");
                    String emailid=abc.getEmail();
                    resulthistupl(emailid,stat_res,city_res,locality_res,loc[0],loc[1]);
                }
            }
        });
        buttonseeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2=new Intent(ResultHistUpload.this,MapsActivity.class);
                startActivity(int2);
            }
        });
    }
    public void resulthistupl(String emailiduse, String res_state, String res_city, String res_locality, String latitu, String longitu) {
        HashMap reshis=new HashMap();
        reshis.put("emailuser",emailiduse);
        reshis.put("resostate",res_state);
        reshis.put("resocity",res_city);
        reshis.put("resoloc",res_locality);
        reshis.put("latitud",latitu);
        reshis.put("longitud",longitu);
        task.cancel(true);
             PostResponseAsyncTask task1=new PostResponseAsyncTask((AsyncResponse)this,reshis);
             task1.execute("https://ruralroadinfo.000webhostapp.com/resolvhist.php");
        Intent int1=new Intent(ResultHistUpload.this,AfterLogin.class);

        startActivity(int1);

        }

    String d[] = {};

    public void setTask(PostResponseAsyncTask task) {
        this.task = task;
    }

    @Override
    public void processFinish (String s){
        if(task.isCancelled()!=true) {


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


           // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

        }
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
