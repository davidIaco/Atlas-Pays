package com.example.administrateur.webservicedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtCode;
    private TextView textView, txtName;
    private ListView txtList;

    private  final String URL_WS1 = "http://demo@services.groupkt.com/country/get/iso2code/";
    private final String URL_WS2 = "http://demo@services.groupkt.com/country/get/all";

    ArrayList<String> pays = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCode = (EditText) findViewById(R.id.txtCode);
        txtName = (TextView) findViewById(R.id.txtName);
        textView = (TextView) findViewById(R.id.textView);
        txtList = (ListView) findViewById(R.id.txtList);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, pays);
    }

    public void code (View v) throws InterruptedException, JSONException {

        ThreadUrl th = new ThreadUrl();

        String code = txtCode.getText().toString().toUpperCase();
        String url = URL_WS1 + code;
        th.setAdr(url);

        th.start();
        th.join();

        //Toast.makeText(this, th.getResponse(), Toast.LENGTH_LONG).show();

        JSONObject jo = new JSONObject(th.getResponse());
        JSONObject jo2 = jo.getJSONObject("RestResponse");
        JSONObject jo3 = jo2.getJSONObject("result");

        txtName.setText(jo3.getString("name"));

    }

    public void all (View v) throws JSONException, InterruptedException {

        String url = URL_WS2;
        ThreadUrl th = new ThreadUrl();
        th.setAdr(url);

        th.start();
        th.join();

        JSONObject ja = new JSONObject(th.getResponse());
        JSONObject ja2 = ja.getJSONObject("RestResponse");
        JSONArray ja3 = ja2.getJSONArray("result");


        for (int i = 0; i < ja3.length(); i++) {

            pays.add(ja3.getJSONObject(i).getString("name")
                    + " " + ja3.getJSONObject(i).getString("alpha2_code"));
        }
        txtList.setAdapter(adapter);
    }
}
