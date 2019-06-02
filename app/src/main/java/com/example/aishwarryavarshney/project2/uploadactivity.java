package com.example.aishwarryavarshney.project2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class uploadactivity extends AppCompatActivity  {
    private AutoCompleteTextView state;
    private AutoCompleteTextView city;
    private EditText locality;
    private Button btnsubmitt;
    TextView Latitude;
    TextView Longitude;
    LocationManager lm;
    LocationListener ll;
    Validation obj3;
    // for listing all states
    ArrayList<String> listState=new ArrayList<String>();
    // for listing all cities
    ArrayList<String> listCity=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadactivity);
        locality = (EditText) findViewById(R.id.locality);
        obj3=new Validation();
        btnsubmitt = (Button) findViewById(R.id.btnsubmitt);
        Latitude = (TextView) findViewById(R.id.Latitude);
        Longitude = (TextView) findViewById(R.id.Longitude);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        callAll();
        ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Latitude.setText(String.valueOf(location.getLatitude()));
                Longitude.setText(String.valueOf(location.getLongitude()));
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        btnsubmitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=1;
                if (state.getText().toString().equals("") || (!obj3.isValid(state.getText().toString())))
                {
                    state.setError("Entered State is Empty or is not valid");a=2;
                }
                if (city.getText().toString().equals("") || (!obj3.isValid(city.getText().toString())))
                {
                    city.setError("Entered City is Empty or is not valid");a=2;
                }
                if (locality.getText().toString().equals("") || (!obj3.isValid(locality.getText().toString())))
                {
                    locality.setError("Entered Locality is Empty or is not valid");a=2;
                }
                   if(a==1)
                   {
                       upload.setState(state.getText().toString());
                       upload.setCity(city.getText().toString());
                       upload.setLocality(locality.getText().toString());
                       upload.setLati(Latitude.getText().toString());
                       upload.setLongi(Longitude.getText().toString());
                       Intent intent=new Intent(uploadactivity.this, filechooser.class);
                       startActivity(intent);
                   }
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates("gps", 0, 0, ll);
    }
    public void callAll()
    {
        obj_list();
        addCity();
        addState();
    }
    // Get the content of cities.json from assets directory and store it as string
    public String getJson()
    {
        String json=null;
        try
        {
            // Opening cities.json file
            InputStream is = getAssets().open("cities.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return json;
        }
        return json;
    }
    // This add all JSON object's data to the respective lists
    void obj_list()
    {
        // Exceptions are returned by JSONObject when the object cannot be created
        try
        {
            // Convert the string returned to a JSON object
            JSONObject jsonObject=new JSONObject(getJson());
            // Get Json array
            JSONArray array=jsonObject.getJSONArray("array");
            // Navigate through an array item one by one
            for(int i=0;i<array.length();i++)
            {
                // select the particular JSON data
                JSONObject object=array.getJSONObject(i);
                String city=object.getString("name");
                String state=object.getString("state");
                // add to the lists in the specified format
                listCity.add(city);
                listState.add(state);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    // The first auto complete text view
    void addCity()
    {
        city = (AutoCompleteTextView) findViewById(R.id.city);
        adapterSetting(listCity,2);
    }

    // The second auto complete text view
    void addState()
    {
        Set<String> set = new HashSet<String>(listState);
        state = (AutoCompleteTextView) findViewById(R.id.state);
        adapterSetting(new ArrayList(set),1);
    }
    // setting adapter for auto complete text views
    void adapterSetting(ArrayList arrayList,int choice)
    {
        if(choice==1)
        {
            ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
            state.setAdapter(adapter1);
        }
        else if(choice==2)
        {
            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
            city.setAdapter(adapter2);
        }

        hideKeyBoard(choice);
    }
    // hide keyboard on selecting a suggestion
    public void hideKeyBoard(int c)
    {
        if(c==1)
        {
            state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            });
        }
        else if(c==2)
        {
            city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            });
        }
    }
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(uploadactivity.this, AfterLogin.class);
        startActivity(intent);
    }

}
