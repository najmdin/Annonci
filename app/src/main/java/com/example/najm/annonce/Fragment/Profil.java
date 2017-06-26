
package com.example.najm.annonce.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.Menu_App;
import com.example.najm.annonce.Model.Contact;
import com.example.najm.annonce.R;


/**
 * Created by najm
 on 04/30/2016.
 */

public class Profil extends Fragment {

    private Db db;

    EditText tv_name,tv_pass,tv_fone,adrs,tv_conf;
    TextView tv_user,vil;
    Spinner Ville;
    String ville;
    ArrayAdapter<CharSequence> adapter;
    Button map;

    private View mRootView;
    public Profil() {

        // Required empty public constructor

    }
    public static Profil newInstance(int sectionNumber) {

        Profil fragment = new Profil();

        Bundle args = new Bundle();

        args.putInt(Constants.ARG_SECTION_NUMBER, 3);

        fragment.setArguments(args);

        return fragment;
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        db = new Db(getActivity());

    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.user_profil, container, false);
        int k =0;
         Contact annc = db.getProfilView(k);
        tv_name = (EditText) mRootView.findViewById(R.id.tv_name);
        tv_user = (TextView) mRootView.findViewById(R.id.tv_user);
        tv_pass = (EditText) mRootView.findViewById(R.id.tv_pass);
        tv_conf = (EditText) mRootView.findViewById(R.id.conf_pass);
        tv_fone = (EditText) mRootView.findViewById(R.id.tv_fone);
        adrs = (EditText) mRootView.findViewById(R.id.tv_adrs);
        Ville = (Spinner) mRootView.findViewById(R.id.Ville);
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.ville,android.R.layout.simple_spinner_item);
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

        tv_name.setText(annc.getName());
        adrs.setText(annc.getAddress());
        tv_user.setText(annc.getUserna());
        tv_pass.setText(annc.getPass());
        tv_fone.setText(annc.getPhone());

        map = (Button) mRootView.findViewById(R.id.save_);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        String nm,ps,fon,adr,cf;
                nm = tv_name.getText().toString();
                adr = adrs.getText().toString();
                ps = tv_pass.getText().toString();
                fon = tv_fone.getText().toString();
                cf = tv_conf.getText().toString();
                if ( nm.isEmpty() || fon.length() < 10) {
                    Toast.makeText(getActivity(), nm+":"+fon+":"+adr, Toast.LENGTH_LONG).show();
                }
                else if(!cf.equals(ps)||ps.isEmpty()){
                    Toast.makeText(getActivity(), cf+"=!"+ps, Toast.LENGTH_LONG).show();
                }
                else {
                    db.Update_Profil(nm,ps,fon,ville,adr);
                    startActivity(new Intent(getActivity(), Menu_App.class));
                }
            }


        });

        return mRootView;
    }





}
