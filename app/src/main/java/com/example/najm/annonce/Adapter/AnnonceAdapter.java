package com.example.najm.annonce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.najm.annonce.Model.Annonce;
import com.example.najm.annonce.R;

import java.util.List;

/**
 * Created by najm on 04/27/2016.
 */

/**
 * Created by Valentine on 4/16/2015.
 */
public class AnnonceAdapter extends ArrayAdapter<Annonce> {
    private Context mContext;
    private List<Annonce> mAnnonces;


    public AnnonceAdapter(Context context, List<Annonce> annonces)
    {
        super(context, R.layout.row_annonce_list, annonces);
        context = mContext;
        mAnnonces = annonces;
    }



    @Override
    public int getCount() {
        return mAnnonces.size();
    }

    @Override
    public Annonce getItem(int position) {
        if (position < mAnnonces.size()) {
            return mAnnonces.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{

        private ImageView Thumbnail;
        public TextView Name;
        public TextView Price;
    }



    public void Add(Annonce annonce)
    {
        mAnnonces.add(annonce);
        this.notifyDataSetChanged();
    }

    public void Update()
    {
        mAnnonces.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder mHolder;

        Annonce annonce= getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.row_annonce_list, null);

            mHolder = new Holder();
            mHolder.Name = (TextView) view.findViewById(R.id.textAnnonceName);
            mHolder.Price = (TextView) view.findViewById(R.id.textAnnonceprice);
            mHolder.Thumbnail = (ImageView) view.findViewById(R.id.Annonce_image_thumbnail);

            view.setTag(mHolder);
        }else {
            mHolder = (Holder)view.getTag();
        }

        //Set the annonce name
        if (mHolder.Name != null) {
            mHolder.Name.setText(annonce.getmTitre());
        }
        //Set the annonce price
        if (mHolder.Price != null) {
            mHolder.Price.setText(annonce.getmPrix());
        }



        //set the annonce thumbnail
        if (mHolder.Thumbnail != null){
            mHolder.Thumbnail.setImageDrawable(annonce.getThumbnail(getContext()));
        }
        return view;
    }




}
