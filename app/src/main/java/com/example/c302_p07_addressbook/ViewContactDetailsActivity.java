package com.example.c302_p07_addressbook;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.*;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewContactDetailsActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;
    private int contactId;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_details);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        client = new AsyncHttpClient();

        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id", -1);

        //TODO: call getContactDetails.php with the id as a parameter
		//TODO: set the text fields with the data retrieved

        client.get("http://10.0.2.2/C302_P07/getContactsById.php?id=" + contactId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());

                    etFirstName.setText(response.getString("firstname"));
                    etLastName.setText(response.getString("lastname"));
                    etMobile.setText(response.getString("mobile"));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }


            }//end onSuccess
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateOnClick(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeleteOnClick(v);
            }
        });
    }//end onCreate

    
    private void btnUpdateOnClick(View v) {
		//TODO: retrieve the updated text fields and set as parameters to be passed to updateContact.php

        RequestParams params = new RequestParams();
        params.add("id", "" + contactId);
        params.add("firstname", etFirstName.getText().toString());
        params.add("lastname", etLastName.getText().toString());
        params.add("mobile", etMobile.getText().toString());

        client.post("http://10.0.2.2/C302_P07/createContact.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());

                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }//end onSuccess
        });

    }//end btnUpdateOnClick

    private void btnDeleteOnClick(View v) {
		//TODO: retrieve the id and set as parameters to be passed to deleteContact.php
        RequestParams params = new RequestParams();
        params.add("id", "" + contactId);

        client.post("http://10.0.2.2/C302_P07/deleteContact.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());

                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }//end onSuccess
        });

    }//end btnDeleteOnClick

}//end class