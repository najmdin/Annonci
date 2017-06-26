package com.example.najm.annonce.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.R;

/**
 * Created by najm on 04/25/2016.
 */
public class Search extends Fragment {
    private Spinner Cati;
    private Spinner typo;
    private ArrayAdapter<CharSequence> adapter;
    SQLiteDatabase dbh;
    String cat,typ,ville;
    Db db;
    Cursor cur;
    private Spinner Ville;
    public Search() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.search, container, false);

        Button button = (Button) rootView.findViewById(R.id.btnSearch);
        final EditText etTitre = (EditText) rootView.findViewById(R.id.etTitre);
        Ville = (Spinner) rootView.findViewById(R.id.Ville);
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
        Cati = (Spinner) rootView.findViewById(R.id.etCat);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cati.setAdapter(adapter);
        Cati.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                cat = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        typo = (Spinner) rootView.findViewById(R.id.etType);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typo.setAdapter(adapter);
        typo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                typ = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ttr;
                ttr = etTitre.getText().toString();
                final Bundle bundle = new Bundle();
                bundle.putString("titre", ttr);
                bundle.putString("type", typ);
                bundle.putString("cat",cat);
                bundle.putString("ville", ville);
                Toast.makeText(getActivity(),ttr+":"+typ+":"+cat+":"+ville , Toast.LENGTH_LONG).show();


                Fragment mFragment = null;
                mFragment = new Search_view();
                mFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mFragment)
                        .addToBackStack(null)
                        .commit();

                    }
                }

        );
        return rootView;
    }
    public static Search newInstance(int sectionNumber) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, 2);
        fragment.setArguments(args);
        return fragment;
    }
}