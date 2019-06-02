package com.example.aishwarryavarshney.project2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    Button btnlogin, btnforgetpass, btnsignup;
    EditText edemail, password;
    Validation obj2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edemail = (EditText) findViewById(R.id.edemail);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnforgetpass = (Button) findViewById(R.id.btnforgetpass);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        obj2 = new Validation();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            int flag = 0;

            @Override
            public void onClick(View v) {
                String email = edemail.getText().toString();
                String pass = password.getText().toString();
                if (obj2.myisEmpty(email)) {
                    flag = 1;
                    AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                    ab.setMessage("Email is Empty");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edemail.requestFocus();
                        }
                    });

                    AlertDialog alertDialog = ab.create();
                    ab.show();
                } else if (obj2.myisEmail(email)) {
                    flag = 1;
                    AlertDialog.Builder ab1 = new AlertDialog.Builder(MainActivity.this);
                    ab1.setMessage("Email is not valid");
                    ab1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edemail.requestFocus();
                        }
                    });

                    AlertDialog alertDialog1 = ab1.create();
                    ab1.show();
                }
                if (password.getText().toString().equals("")) {
                    password.setError("Enter Password");
                }
                if (obj2.myisEmpty(email) == false && obj2.myisEmail(email) == false && password.getText().toString().equals("") != true) {

                    fn(email, pass);


                }
            }
        });
        btnforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(MainActivity.this, ForgetPassActivity.class);
                startActivity(int1);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2 = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(int2);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application");
        alertDialogBuilder
                .setMessage("Click Yes to exit the app")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void fn(String eid, String pwd) {
        HashMap pp = new HashMap();

        pp.put("em", eid);
        pp.put("pw", pwd);

        PostResponseAsyncTask task = new PostResponseAsyncTask((AsyncResponse) this, pp);
        task.execute("https://ruralroadinfo.000webhostapp.com/log.php");
    }

    @Override
    public void processFinish(String s) {

        String d[] = {};
        if (s != "") {
            d = s.split(",");
            abc.setId(Integer.parseInt(d[0]));
            abc.setFname(d[1]);
            abc.setLname(d[2]);
            abc.setEmail(d[3]);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            Intent int3 = new Intent(MainActivity.this, AfterLogin.class);
            startActivity(int3);
        } else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }
    }
}
