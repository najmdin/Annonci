package com.example.najm.annonce.Fragment;

/**
 * Created by najm on 04/27/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.najm.annonce.Adapter.AnnonceAdapter;
import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.Helpers.Enums;
import com.example.najm.annonce.MainActivity;
import com.example.najm.annonce.Menu_App;
import com.example.najm.annonce.Model.Annonce;
import com.example.najm.annonce.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class Annonce_Sauve extends Fragment {
    android.support.v4.app.FragmentTransaction fragmt;
    private List<Annonce> mCustomers = null ;

    private ListView mCustomerListView;
    private AnnonceAdapter mAdapter;
    private View mRootView;
    private Db db;
    //static
    static int n;

    public static Annonce_Sauve newInstance(int sectionNumber) {
        Annonce_Sauve fragment = new Annonce_Sauve();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public Annonce_Sauve() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = new Db(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_annonce_sauve, container, false);

        InitializeViews();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadListData();
    }

    private void LoadListData()
    {
        //First clear the adapter of any Customers it has
        mAdapter.Update();

        //Get the list of customers from the database
        int k = MainActivity.k;
        mCustomers = db.getAnnonceSauveById(k);

        if (mCustomers != null){
            for (Annonce customer: mCustomers){
                mAdapter.add(customer);
            }
        }

    }

    private void InitializeViews() {

        mCustomerListView = (ListView) mRootView.findViewById(R.id.annonce_sauve);
        mCustomers = new ArrayList<Annonce>();

        mAdapter = new AnnonceAdapter(getActivity(), mCustomers);
        mCustomerListView.setAdapter(mAdapter);
        TextView emptyText = (TextView) mRootView.findViewById(R.id.client_list_empty);
        mCustomerListView.setEmptyView(emptyText);

        mCustomerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the selected client
                Annonce tempCustomer = mCustomers.get(position);
                Fragment mFragment = null;
                n = tempCustomer.getId();
               /* if(db.annonceExists(n)) {
                    Toast.makeText(getActivity(), n + "success contact insert" + n, Toast.LENGTH_LONG).show();
                }
                mFragment =  User_view.newInstance(position);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mFragment)
                        .addToBackStack(null)
                        .commit();*/
            }




        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.annonce_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_add_annonce:
                Menu_App myActivity = (Menu_App)getActivity();
                myActivity.ReplaceFragment(Enums.FragmentEnums.AnnonceDetailsFragment, 3, 0);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Menu_App) activity).onSectionAttached(
                getArguments().getInt(Constants.ARG_SECTION_NUMBER));
    }



}
