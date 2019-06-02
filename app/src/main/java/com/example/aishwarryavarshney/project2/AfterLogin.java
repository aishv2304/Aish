package com.example.aishwarryavarshney.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AfterLogin extends AppCompatActivity {
    private Button btnupload1,btnretreive1,btnresolve,btnresolvesee;
    TextView fname1,lname1,email1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        btnupload1=(Button)findViewById(R.id.btnupload1);
        btnretreive1=(Button)findViewById(R.id.btnretreive1);
        btnresolve=(Button) findViewById(R.id.btnresolve) ;
        btnresolvesee=(Button) findViewById(R.id.btnresolvesee);
        fname1=(TextView)findViewById(R.id.fname1);
        lname1=(TextView) findViewById(R.id.lname1);
        email1=(TextView) findViewById(R.id.email1);
        fname1.setText(abc.getFname());
        lname1.setText(abc.getLname());
        email1.setText(abc.getEmail());

        btnupload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(AfterLogin.this,uploadactivity.class);
                startActivity(int1);
            }
        });
        btnretreive1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(AfterLogin.this,retreival.class);
                startActivity(int1);
            }
        });
        btnresolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(AfterLogin.this,ResultHistUpload.class);
                startActivity(int1);
            }
        });
        btnresolvesee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(AfterLogin.this,SeeResolvedHistory.class);
                startActivity(int1);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(AfterLogin.this, MainActivity.class);
        startActivity(intent);
    }
}
