package com.example.najm.annonce.Fragment;

/**
 * Created by najm on 04/28/2016.
 */

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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.najm.annonce.Data.Db;
import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.Helpers.FileUtils;
import com.example.najm.annonce.MainActivity;
import com.example.najm.annonce.Menu_App;
import com.example.najm.annonce.Model.Annonce;
import com.example.najm.annonce.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class AnnonceDetailsFragment extends Fragment {

    private boolean InEditMode;
    private Annonce mAnnonce;
    private View mRootView;
    private Db db;

    //Image properties
    private String mCurrentImagePath = null;
    String cat,typ;
    private Uri mCapturedImageURI = null;
    private ImageButton mProfileImageButton;

    private EditText mTitreEditText,  mPrixEditText, mDescEditText;
    private Spinner Cati;
    private Spinner typo;
    private ArrayAdapter<CharSequence> adapter;


    public static AnnonceDetailsFragment newInstance(int sectionNumber, long annonceId) {
        AnnonceDetailsFragment fragment = new AnnonceDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        if (annonceId > 0){
            args.putLong(Constants.ARG_ANN_ID, annonceId );
        }
        fragment.setArguments(args);
        return fragment;
    }

    private void GetPassedInAnnonce(){
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constants.ARG_ANN_ID)){
            int annonceId = args.getInt(Constants.ARG_ANN_ID, 0);
            if (annonceId > 0){
                mAnnonce = db.getAnnonceById(annonceId);
                InEditMode = true;
            }
        }else {
            mAnnonce = new Annonce();
            InEditMode = false;
        }

    }


    public AnnonceDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Db(getActivity());

        setHasOptionsMenu(true);
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        mRootView = inflater.inflate(R.layout.fragment_annonce_details, container, false);
        InitializeViews();
        Cati = (Spinner) mRootView.findViewById(R.id.edit_text_annonce_cat);
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
        typo = (Spinner) mRootView.findViewById(R.id.edit_text_annonce_type);
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
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetPassedInAnnonce();
        if (InEditMode)
        {
            PopulateFields();
        }
    }

    private void PopulateFields() {
        Menu_App myActivity = (Menu_App) getActivity();
        mTitreEditText.setText(mAnnonce.getmTitre());
        mPrixEditText.setText(mAnnonce.getmPrix());




        // Update profile's Image
        if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty()) {
            mProfileImageButton.setImageDrawable(new BitmapDrawable(getResources(),
                    FileUtils.getResizedBitmap(mCurrentImagePath, 512, 512)));
        } else {
            mProfileImageButton.setImageDrawable(mAnnonce.getImage(getActivity()));
        }

    }

    private void InitializeViews() {
        mTitreEditText = (EditText) mRootView.findViewById(R.id.edit_text_annonce_titre);
        mDescEditText = (EditText) mRootView.findViewById(R.id.edit_text_annonce_desc);
        mPrixEditText = (EditText) mRootView.findViewById(R.id.edit_text_annonce_prix);
        mProfileImageButton = (ImageButton)mRootView.findViewById(R.id.annonce_image_button);
        mProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Menu_App) activity).onSectionAttached(
                getArguments().getInt(Constants.ARG_SECTION_NUMBER));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.annonce_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
          /*  case android.R.id.home:
                getActivity().onBackPressed();
                break;*/
            case R.id.action_save_annonce:
                SaveAnnonce();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveAnnonce(){
        mAnnonce.setmTitre(mTitreEditText.getText().toString());
        mAnnonce.setmCat(cat);
        mAnnonce.setmPrix(mPrixEditText.getText().toString());
        mAnnonce.setmType(typ);
        mAnnonce.setmDesc(mDescEditText.getText().toString());
        int i = MainActivity.k;
        mAnnonce.setmId_user(i);
        //Check to see if there is valid image path temporarily in memory
        //Then save that image path to the database and that becomes the profile
        //Image for this user.
        if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty())
        {
            mAnnonce.setImagePath(mCurrentImagePath);
        }

        long result = db.addAnnonce(mAnnonce);
        if (result == -1 ){
            Toast.makeText(getActivity(), "Unable to add annonce: " + mAnnonce.getmTitre(), Toast.LENGTH_LONG).show();
        }
        getActivity().onBackPressed();
    }

    private void chooseImage(){

        //We need the annonce's name to to save the image file
        if (mTitreEditText.getText() != null && !mTitreEditText.getText().toString().isEmpty()) {
            // Determine Uri of camera image to save.
            final File rootDir = new File(Constants.PICTURE_DIRECTORY);

            //noinspection ResultOfMethodCallIgnored
            rootDir.mkdirs();

            // Create the temporary file and get it's URI.

            //Get the annonce name
            String annonceName = mTitreEditText.getText().toString();

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
            mTitreEditText.setError("Please enter annonce name");
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
                    mProfileImageButton.setImageDrawable(new BitmapDrawable(getResources(),
                            FileUtils.getResizedBitmap(mCurrentImagePath, 512, 512)));
                }
            }
        }

    }
}
