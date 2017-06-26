package com.example.najm.annonce.Model;

/**
 * Created by najm on 04/28/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.Helpers.FileUtils;

/**
 * Created by najm on 04/27/2016.
 */

/**
 * Created by Valentine on 4/15/2015.
 */
public class Annonce {
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    private int mId;
    private String mTitre;
    private String mDesc;
    private String mCat;
    private String mPrix;
    private String mType;
    private int mId_user;
    private String mImagePath;
    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
    public int getmId_user() {
        return mId_user;
    }

    public void setmId_user(int mId_user)
    { /*mId_user = MainActivity.k;*/
        this.mId_user = mId_user;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmPrix() {
        return mPrix;
    }

    public void setmPrix(String mPrix) {
        this.mPrix = mPrix;
    }

    public String getmCat() {
        return mCat;
    }

    public void setmCat(String mCat) {
        this.mCat = mCat;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmTitre() {
        return mTitre;
    }

    public void setmTitre(String mTitre) {
        this.mTitre = mTitre;
    }



    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }


    public boolean hasImage() {

        return getImagePath() != null && !getImagePath().isEmpty();
    }


    /**
     * Get a thumbnail of this profile's picture, or a default image if the profile doesn't have a
     * Image.
     *
     * @return Thumbnail of the profile.
     */
    public Drawable getThumbnail(Context context) {

        return getScaledImage(context, 128, 128);
    }

    /**
     * Get this profile's picture, or a default image if the profile doesn't have a Image.
     *
     * @return Image of the profile.
     */
    public Drawable getImage(Context context) {

        return getScaledImage(context, 512, 512);
    }

    /**
     * Get a scaled version of this profile's Image, or a default image if the profile doesn't have
     * a Image.
     *
     * @return Image of the profile.
     */
    private Drawable getScaledImage(Context context, int reqWidth, int reqHeight) {

        // If profile has a Image.
        if (hasImage()) {

            // Decode the input stream into a bitmap.
            Bitmap bitmap = FileUtils.getResizedBitmap(getImagePath(), reqWidth, reqHeight);

            // If was successfully created.
            if (bitmap != null) {

                // Return a drawable representation of the bitmap.
                return new BitmapDrawable(context.getResources(), bitmap);
            }
        }

        // Return the default image drawable.
        return context.getResources().getDrawable(Constants.DEFAULT_IMAGE_RESOURCE);
    }
}

