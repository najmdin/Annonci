
package com.example.najm.annonce.Fragment;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.MainActivity;
import com.example.najm.annonce.Model.Annonce;
import com.example.najm.annonce.Model.Sauvegarde;
import com.example.najm.annonce.R;

import java.io.IOException;
import java.util.List;


/**
 * Created by najm
 on 04/30/2016.
 */

public class View_annonce extends Fragment implements RatingBar.OnRatingBarChangeListener {

    private Db db;
    
TextView tv_titre,tv_desc,tv_prix,tv_cat,tv_name,tv_phone;
  Button map;
   private Sauvegarde sv;
  ImageView tv_img;
  RatingBar rb;
    private View mRootView;
    static double latitude,longitude;
    public View_annonce() {
   
     // Required empty public constructor
  
  }
 public static View_annonce newInstance(int sectionNumber) {
     
   View_annonce fragment = new View_annonce();
   
     Bundle args = new Bundle();
  
      args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
 
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
      
      mRootView = inflater.inflate(R.layout.annonce_view, container, false);

            int k = AnnonceListFragment.n;
  final Annonce annc = db.getAnnonceView(k);
            tv_titre = (TextView) mRootView.findViewById(R.id.tv_titre);
      rb = (RatingBar) mRootView.findViewById(R.id.ratingBar);
            rb.setRating(1f);
            rb.setOnRatingBarChangeListener(this);

      tv_prix = (TextView) mRootView.findViewById(R.id.tv_price);
      tv_name = (TextView) mRootView.findViewById(R.id.tv_nam);
      tv_desc = (TextView) mRootView.findViewById(R.id.tv_desc);
      tv_phone = (TextView) mRootView.findViewById(R.id.tv_phone);
      tv_cat = (TextView) mRootView.findViewById(R.id.tv_categorie);
  
      tv_img = (ImageView) mRootView.findViewById(R.id.Annonce_image_thumbnail);
 
       tv_titre.setText(annc.getmTitre());
       tv_name.setText(db.searchName(annc.getmId_user()));
       tv_prix.setText(annc.getmPrix());
       tv_phone.setText(db.searchPhone(annc.getmId_user()));
       tv_desc.setText(annc.getmDesc());
            tv_cat.setText(annc.getmCat());
            tv_img.setImageDrawable(annc.getThumbnail(getActivity()));
            map = (Button) mRootView.findViewById(R.id.gps);
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    List<Address> addresses = null;
                    String dd = db.searchAdrs(annc.getmId_user());
                    try {
                        addresses = geocoder.getFromLocationName(dd, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addresses.size() > 0) {
                        latitude= addresses.get(0).getLatitude();
                        longitude= addresses.get(0).getLongitude();

                    }
                   /* final Bundle bundle = new Bundle();
                    bundle.putDouble("lat", latitude);
                    bundle.putDouble("tong", longitude);*/
                    Fragment mFragment = null;
                    Toast.makeText(getActivity(), latitude + ":" + longitude, Toast.LENGTH_LONG).show();
                    mFragment =  new Maps();
                    /*mFragment.setArguments(bundle);*/
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, mFragment)
                            .addToBackStack(null)
                            .commit();
                }


            });

     return mRootView;
    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        int i = MainActivity.k;
        int k = AnnonceListFragment.n;
        final Annonce annc = db.getAnnonceView(k);
            if(rating == 0){
                Toast.makeText(getActivity(), i+"not added"+annc.getmId(), Toast.LENGTH_LONG).show();
            }
            if(rating == 1){
                /*sv.setId_annoncce(2);
                sv.setId_user(2);*/
                Toast.makeText(getActivity(), i+"added"+annc.getmId() , Toast.LENGTH_LONG).show();
                /*long cc = db.addSauve(sv);
                if (cc == -1 ){
                    Toast.makeText(getActivity(), "Unable to add annonce: ", Toast.LENGTH_LONG).show();
                }*/
            }
        }

    }

