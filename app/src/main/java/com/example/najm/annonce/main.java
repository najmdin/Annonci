package com.example.najm.annonce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;

public class main extends Fragment {


    Db helper = new Db(getActivity());
    EditText ET_name, ET_pass;
    Button log, sig;
    public static int k;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.activity_main, container, false);
        ET_name = (EditText) rootView.findViewById(R.id.user_name);
        ET_pass = (EditText) rootView.findViewById(R.id.user_pass);
        log = (Button) rootView.findViewById(R.id.button2);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = ET_name.getText().toString();
                String str2 = ET_pass.getText().toString();
                String password = helper.searchPass(str);
                if (str2.equals(password)) {
                    k = helper.searchID(str, str2);
                    Intent i = new Intent(getActivity(), Menu_App.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "User and Password must equals 0",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        sig = (Button) rootView.findViewById(R.id.button);
        sig.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       startActivity(new Intent(getActivity(), Sign.class));
                                   }
                               }


        );
        return rootView;}

    public static main newInstance(int sectionNumber) {
        main fragment = new main();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}

