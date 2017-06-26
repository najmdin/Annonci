
package com.example.najm.annonce.Fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.Model.Annonce;
import com.example.najm.annonce.R;


/**
 * Created by najm
 on 04/30/2016.
 */

public class Search_detail extends Fragment {

    private Db db;
    Double latitude,longitude;
    TextView tv_titre,tv_desc,tv_prix,tv_cat,tv_name,tv_phone;

    ImageView tv_img;

    private View mRootView;



    public Search_detail() {

        // Required empty public constructor

    }

    public static Search_detail newInstance(int sectionNumber) {

        Search_detail fragment = new Search_detail();

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

        mRootView = inflater.inflate(R.layout.search_view, container, false);









        int k = Integer.parseInt(Search_view.n);
        Annonce annc = db.getAnnonceView(k);
        tv_titre = (TextView) mRootView.findViewById(R.id.tv_titre);

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

        return mRootView;
    }





}
