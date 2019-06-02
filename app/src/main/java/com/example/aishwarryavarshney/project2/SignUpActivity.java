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


public class SignUpActivity extends AppCompatActivity implements  AsyncResponse{
    private EditText fname,lname,emailid,pw,cpw,mobilenum;
    private Button btnsubmit;
    Validation obj1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        emailid=(EditText)findViewById(R.id.emailid);
        pw= (EditText)findViewById(R.id.pw);
        cpw=(EditText)findViewById(R.id.cpw);
        mobilenum=(EditText)findViewById(R.id.mobilenum);
        btnsubmit=(Button)findViewById(R.id.btnsubmit);
        obj1=new Validation();
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            int flag=0;
            @Override
            public void onClick(View v) {
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String eid = emailid.getText().toString();
                String pwd = pw.getText().toString();
                String cpwd = cpw.getText().toString();
                String mobn = mobilenum.getText().toString();

                if (obj1.myisEmpty(eid)) {
                    flag = 1;
                    AlertDialog.Builder ab = new AlertDialog.Builder(SignUpActivity.this);
                    ab.setMessage("Email is Empty");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emailid.requestFocus();
                        }
                    });

                    AlertDialog alertDialog = ab.create();
                    ab.show();
                }
               else if (obj1.myisEmail(eid)) {
                    flag = 1;
                    AlertDialog.Builder ab = new AlertDialog.Builder(SignUpActivity.this);
                    ab.setMessage("Email is not valid");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emailid.requestFocus();
                        }
                    });

                    AlertDialog alertDialog = ab.create();
                    ab.show();
                }
                else
                {

                    fn(firstname,lastname,eid,pwd,mobn);

                }
                if (fname.getText().toString().equals(""))
                    fname.setError("Enter First Name");
                if (lname.getText().toString().equals(""))
                    lname.setError("Enter Last Name");
                if (pw.getText().toString().equals(""))
                    pw.setError("Enter Password");
                if (cpw.getText().toString().equals(""))
                    cpw.setError("Enter Confirm Password");
                  if (mobilenum.getText().toString().equals(""))
                    mobilenum.setError("Enter Mobile Number");
                  if(!pwd.equals(cpwd))
                  {
                     cpw.setError("Password does not match");
                  }
            }


        });
    }
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void fn(String firstname,String lastname,String eid,String pwd,String mobn) {
        HashMap pp=new HashMap();
        pp.put("fn",firstname);
        pp.put("ln",lastname);
        pp.put("em",eid);
        pp.put("pw",pwd);
        pp.put("mn",mobn);
        PostResponseAsyncTask task=new PostResponseAsyncTask((AsyncResponse) this,pp);
        task.execute("https://ruralroadinfo.000webhostapp.com/data.php");

    }
    @Override
    public void processFinish(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
