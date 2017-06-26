package com.example.najm.annonce.Fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.Helpers.FileUtils;
import com.example.najm.annonce.Menu_App;
import com.example.najm.annonce.Model.Annonce;
import com.example.najm.annonce.R;
import android.widget.AdapterView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by najm on 05/01/2016.
 */
public class User_view extends Fragment {


    private Db db;
    EditText tv_titre, tv_desc, tv_prix;
    ImageButton tv_img;
    private Spinner Cati;
    private View mRootView;
    private String cat;
    private ArrayAdapter<CharSequence> adapter;
    Button save,delete;
    private String mCurrentImagePath = null;
    private Uri mCapturedImageURI = null;

    public User_view() {
        // Required empty public constructor
    }

    public static User_view newInstance(int sectionNumber) {
        User_view fragment = new User_view();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Db(getActivity());
        // Ensure there is a saved instance state.
        if (savedInstanceState != null) {

            // Get the saved Image uri string.
            String ImageUriString = savedInstanceState.getString(Constants.KEY_IMAGE_URI);

            // Restore the Image uri from the Image uri string.
            if (ImageUriString != null) {
                mCapturedImageURI = Uri.parse(ImageUriString);
            }
            mCurrentImagePath = savedInstanceState.getString(Constants.KEY_IMAGE_URI);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCapturedImageURI != null) {
            outState.putString(Constants.KEY_IMAGE_URI, mCapturedImageURI.toString());
        }
        outState.putString(Constants.KEY_IMAGE_PATH, mCurrentImagePath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.annonce_user_view, container, false);
        Cati = (Spinner) mRootView.findViewById(R.id.etCat);
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



        /*int k = Integer.parseInt(getIntent().getStringExtra("Id_Annc"));
        db =  new Db(this);*/
        int k = User_Annonce.n;
       final Annonce annc = db.getAnnonceById(k);
        tv_titre = (EditText) mRootView.findViewById(R.id.tv_titre);
        tv_prix = (EditText) mRootView.findViewById(R.id.tv_price);
        tv_desc = (EditText) mRootView.findViewById(R.id.tv_desc);
        tv_img = (ImageButton) mRootView.findViewById(R.id.Annonce_image_thumbnail);
        tv_titre.setText(annc.getmTitre());
        tv_prix.setText(annc.getmPrix());
        tv_desc.setText(annc.getmDesc());
        tv_img.setImageDrawable(annc.getThumbnail(getActivity()));
        tv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        save = (Button) mRootView.findViewById(R.id.UpdateAnnonce);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ttr,prx,dsc,img ;
               ttr = tv_titre.getText().toString();
                prx =  tv_prix.getText().toString();
                dsc = tv_desc.getText().toString();

                // Update profile's Image
                if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty()) {
                    tv_img.setImageDrawable(new BitmapDrawable(getResources(),
                            FileUtils.getResizedBitmap(mCurrentImagePath, 512, 512)));
                    img =   mCurrentImagePath;
                } else {
                    tv_img.setImageDrawable(annc.getImage(getActivity()));
                    img = annc.getImagePath();
                }
//update sqlite
                int k = User_Annonce.n;
                /*ttr,*/
                db.Update_annonce(k,ttr,cat,prx,dsc,img);
              /* Fragment mFragment = null;
                mFragment = new User_Annonce();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mFragment)
                        .addToBackStack(null)
                        .commit();*/
                startActivity(new Intent(getActivity(), Menu_App.class));
            }
        });
        delete = (Button) mRootView.findViewById(R.id.DeleteAnnonce);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int k = User_Annonce.n;
                db.deleteById(k);
                startActivity(new Intent(getActivity(), Menu_App.class));
                /*Fragment mFragment = null;
                mFragment = new User_Annonce() ;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mFragment)
                        .addToBackStack(null)
                        .commit();*/
            }


        });

        return mRootView;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        return super.onOptionsItemSelected(item);

    }


    private void chooseImage(){

        //We need the annonce's name to to save the image file
        if (tv_titre.getText() != null && !tv_titre.getText().toString().isEmpty()) {
            // Determine Uri of camera image to save.
            final File rootDir = new File(Constants.PICTURE_DIRECTORY);

            //noinspection ResultOfMethodCallIgnored
            rootDir.mkdirs();

            // Create the temporary file and get it's URI.

            //Get the annonce name
            String annonceName = tv_titre.getText().toString();

            //Remove all white space in the annonce name
            annonceName.replaceAll("\\s+", "");

            //Use the annonce name to create the file name of the image that will be captured
            File file = new File(rootDir, FileUtils.generateImageName(annonceName));
            mCapturedImageURI = Uri.fromFile(file);

            // Initialize a list to hold any camera application intents.
            final List<Intent> cameraIntents = new ArrayList<Intent>();

            // Get the default camera capture intent.
            final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Get the package manager.
            final PackageManager packageManager = getActivity().getPackageManager();

            // Ensure the package manager exists.
            if (packageManager != null) {

                // Get all available image capture app activities.
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

                // Create camera intents for all image capture app activities.
                for(ResolveInfo res : listCam) {

                    // Ensure the activity info exists.
                    if (res.activityInfo != null) {

                        // Get the activity's package name.
                        final String packageName = res.activityInfo.packageName;

                        // Create a new camera intent based on android's default capture intent.
                        final Intent intent = new Intent(captureIntent);

                        // Set the intent data for the current image capture app.
                        intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                        intent.setPackage(packageName);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

                        // Add the intent to available camera intents.
                        cameraIntents.add(intent);
                    }
                }
            }

            // Create an intent to get pictures from the filesystem.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // Chooser of filesystem options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

            // Start activity to choose or take a picture.
            startActivityForResult(chooserIntent, Constants.ACTION_REQUEST_IMAGE);
        } else {
            tv_titre.setError("Please enter annonce name");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            // Get the resultant image's URI.
            final Uri selectedImageUri = (data == null) ? mCapturedImageURI : data.getData();

            // Ensure the image exists.
            if (selectedImageUri != null) {

                // Add image to gallery if this is an image captured with the camera
                //Otherwise no need to re-add to the gallery if the image already exists
                if (requestCode == Constants.ACTION_REQUEST_IMAGE) {
                    final Intent mediaScanIntent =
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(selectedImageUri);
                    getActivity().sendBroadcast(mediaScanIntent);
                }

                mCurrentImagePath = FileUtils.getPath(getActivity(), selectedImageUri);

                // Update client's picture
                if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty()) {
                    tv_img.setImageDrawable(new BitmapDrawable(getResources(),
                            FileUtils.getResizedBitmap(mCurrentImagePath, 512, 512)));
                }
            }
        }

    }

}


