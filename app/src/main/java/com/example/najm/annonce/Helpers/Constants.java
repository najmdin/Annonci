package com.example.najm.annonce.Helpers;

/**
 * Created by najm on 04/28/2016.
 */

import android.os.Environment;

import com.example.najm.annonce.R;

import java.io.File;

/**
 * Created by najm on 04/27/2016.
 */

/**
 * Created by Valentine on 4/10/2015.
 */
public class Constants {

    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_ANN_ID = "annonce_id";

    public static final String APP_NAME = "AdvancedSearch";
    public static final int DATA_vers = 1;
    public static final String DATABASE_NAME="stage4.db";
    /*user*/
    public static final String TABLE_C_NAME="contacts";
    public static final String COLUMN_C_ID="id";
    public static final String COLUMN_C_UNAME="uname";
    public static final String COLUMN_C_PASS="pass";
    public static final String COLUMN_C_NAME="name";
    public static final String COLUMN_C_ADR="address";
    public static final String COLUMN_C_PHONE = "phone";
    public static final String COLUMN_C_VILLE = "ville";
    /*annonce*/
    public static final String TABLE_NAME="annonces";
    public static final String COLUMN_Annonce_ID = "_id";
    public static final String COLUMN_IMAGE_PATH = "imagePath";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_CAT = "categorie";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ID_USER = "id_user";
    public static final String COLUMN_PRIX = "prix";
    /*sauvegardées*/
    public static final String TABLE_U_NAME="sauve";
    public static final String COLUMN_U_ID="id";

    public static final String KEY_IMAGE_URI = "image_uri";

    public static final String KEY_IMAGE_PATH = "key_image_path";
    public static int ACTION_REQUEST_IMAGE = 1000;
    public static int SELECT_IMAGE = 1001;
    public static final String TAKE_PHOTO = "Take Photo";
    public static final String CHOOSE_FROM_GALLERY = "Choose from gallery";
    public static final String CANCEL= "Cancel";

    public static final int DEFAULT_IMAGE_RESOURCE = R.drawable.default_annonce_picture;
    public static final String PICTURE_DIRECTORY = Environment.getExternalStorageDirectory()
            + File.separator + "DCIM" + File.separator + "ProfilePicture" + File.separator;

}
