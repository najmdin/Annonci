package com.example.najm.annonce;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Model.Contact;

import java.io.IOException;

/**
 * Created by najm on 04/20/2016.
 */
public class Sign extends Activity {
    Db dbh = new Db(this);
    EditText name,pass,copass,usernam,adr,fone;
    Spinner Ville;
    long k ;
    String ville;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Ville = (Spinner) findViewById(R.id.Ville);
        adapter = ArrayAdapter.createFromResource(this,R.array.ville,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Ville.setAdapter(adapter);
        Ville.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                ville = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }
    public void userReg (View view) throws IOException {
        name = (EditText) findViewById(R.id.user_name);
        pass = (EditText) findViewById(R.id.user_pass);
        copass = (EditText) findViewById(R.id.conf_user_pass);
        usernam = (EditText) findViewById(R.id.Name);
        adr = (EditText) findViewById(R.id.address);
        fone = (EditText) findViewById(R.id.phone);
        String Nam = name.getText().toString();
        String passw = pass.getText().toString();
        String copasw = copass.getText().toString();
        String usern = usernam.getText().toString();
        String adrs = adr.getText().toString();
        String fon = fone.getText().toString();




        String unam = dbh.searchUname(usern);

                if (usern.equals(unam)) {
                    Toast.makeText(getApplicationContext(), "username exist", Toast.LENGTH_LONG).show();
                } else if ( Nam.isEmpty() || fon.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Address or phone is empty ", Toast.LENGTH_LONG).show();
                } else if (!passw.equals(copasw)){
                    Toast.makeText(getApplicationContext(), "Password must equals 0y", Toast.LENGTH_LONG).show();
                                    }
                else{
                        Contact c = new Contact();
                        c.setName(Nam);
                        c.setAddress(adrs);
                        c.setUserna(usern);
                        c.setPass(passw);
                        c.setPhone(fon);
                        c.setVille(ville);
                        dbh.insertContact(c);
                        Toast.makeText(getApplicationContext(), "success contact insert", Toast.LENGTH_LONG).show();
                    }
                }

    }



