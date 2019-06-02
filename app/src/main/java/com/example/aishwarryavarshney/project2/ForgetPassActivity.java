package com.example.aishwarryavarshney.project2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class ForgetPassActivity extends AppCompatActivity implements AsyncResponse{
    EditText fgemail;
    Button btnsubmit1;
    Validation obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
       fgemail = (EditText) findViewById(R.id.fgemail);
        btnsubmit1=(Button) findViewById(R.id.btnsubmit1);
        obj= new Validation();
        btnsubmit1.setOnClickListener(new View.OnClickListener() {
            int flag=0;
            @Override
            public void onClick(View v) {
            String e=fgemail.getText().toString();
                if (obj.myisEmpty(e)) {
                    flag = 1;
                    AlertDialog.Builder ab = new AlertDialog.Builder(ForgetPassActivity.this);
                    ab.setMessage("Email is Empty");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fgemail.requestFocus();
                        }
                    });

                    AlertDialog alertDialog = ab.create();
                    ab.show();
                }
                else if (obj.myisEmail(e)) {
                    flag = 1;
                    AlertDialog.Builder ab1 = new AlertDialog.Builder(ForgetPassActivity.this);
                    ab1.setMessage("Email is not valid");
                    ab1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fgemail.requestFocus();
                        }
                    });

                    AlertDialog alertDialog1 = ab1.create();
                    ab1.show();
                }
                else
                {
                  fn(e);
                }

            }


        });
    }
    private void fn(String eid) {
        HashMap pp=new HashMap();

        pp.put("em",eid);


        PostResponseAsyncTask task=new PostResponseAsyncTask((AsyncResponse) this,pp);
        task.execute("https://ruralroadinfo.000webhostapp.com/fog.php");
    }
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(ForgetPassActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void processFinish(String s) {

        String d[]={};
        if(s!="")
        {
           /* d=s.split(",");
            abc.setId(Integer.parseInt(d[0]));
            abc.setFname(d[1]);
            abc.setLname(d[2]);
            abc.setEmail(d[3]);*/
            Intent int1=new Intent(ForgetPassActivity.this,MainActivity.class);
            startActivity(int1);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }
    }
}
