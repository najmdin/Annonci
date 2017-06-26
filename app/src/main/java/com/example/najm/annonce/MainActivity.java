package com.example.najm.annonce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.najm.annonce.Data.Db;

public class MainActivity extends ActionBarActivity {


    Db helper = new Db(this);
    EditText ET_name, ET_pass;
    public static int k;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void userLogin(View view) {
        ET_name = (EditText) findViewById(R.id.user_name);
        ET_pass = (EditText) findViewById(R.id.user_pass);
        String str = ET_name.getText().toString();
        if(str.isEmpty()){
            ET_name.setError("Enter name");
            return;
        }
        String str2 = ET_pass.getText().toString();
        if(str2.isEmpty()){
            ET_pass.setError("Enter pass");
            return;
        }
        String password = helper.searchPass(str);
        if(str2.equals(password)){
            k = helper.searchID(str,str2);
            Intent i = new Intent(MainActivity.this, Menu_App.class);
            startActivity(i);}
        else{
            Toast.makeText(getApplicationContext(), "User and Password must equals 0",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void userReg(View v) {
        startActivity(new Intent(this, Sign.class));
    }

}
