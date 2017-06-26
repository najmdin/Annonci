package com.example.najm.annonce.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.najm.annonce.Helpers.Constants;
import com.example.najm.annonce.MainActivity;
import com.example.najm.annonce.Model.Annonce;
import com.example.najm.annonce.Model.Contact;
import com.example.najm.annonce.Model.Sauvegarde;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by najm on 04/20/2016.
 */
public class Db extends SQLiteOpenHelper {

    // Context in which this database exists.
    private Context mContext;
   int f;

    // Table names.

    private final static String TAG = Db.class.getSimpleName();

    SQLiteDatabase db;

    // Command to create a table of clients.
    private static final String CREATE_ANNONCE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + " ("
            + Constants.COLUMN_Annonce_ID + " INTEGER PRIMARY KEY, "
            + Constants.COLUMN_IMAGE_PATH + " TEXT, "
            + Constants.COLUMN_TITRE + " TEXT NOT NULL, "
            + Constants.COLUMN_CAT + " TEXT NOT NULL, "
            + Constants.COLUMN_DESC + " TEXT, "
            + Constants.COLUMN_PRIX + " TEXT NOT NULL, "
            + Constants.COLUMN_TYPE + " TEXT NOT NULL,"
            + Constants.COLUMN_ID_USER + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + Constants.COLUMN_ID_USER + ") references " + Constants.TABLE_C_NAME + "(" + Constants.COLUMN_C_ID + "));";

    private static final String CREATE_USERS =
            "CREATE TABLE " + Constants.TABLE_C_NAME + " (" + Constants.COLUMN_C_ID + " INTEGER PRIMARY KEY NOT NULL  ," +
                    "" + Constants.COLUMN_C_NAME + " TEXT NOT NULL ," +
                    "" + Constants.COLUMN_C_ADR + " TEXT  ," +
                    "" + Constants.COLUMN_C_UNAME + " TEXT NOT NULL UNIQUE," +
                    "" + Constants.COLUMN_C_PASS + " TEXT NOT NULL," +
                    "" + Constants.COLUMN_C_PHONE + " TEXT NOT NULL," +
                    "" + Constants.COLUMN_C_VILLE + " TEXT  );";
    private static final String CREATE_USER_SAUV =
            "CREATE TABLE " + Constants.TABLE_U_NAME + " (" + Constants.COLUMN_U_ID + " INTEGER PRIMARY KEY NOT NULL  ," +
                   ""+ Constants.COLUMN_ID_USER + " INTEGER NOT NULL," +
                   "" + Constants.COLUMN_Annonce_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + Constants.COLUMN_ID_USER + ") references " + Constants.TABLE_C_NAME + "(" + Constants.COLUMN_C_ID + "),"+
                    "FOREIGN KEY (" + Constants.COLUMN_Annonce_ID + ") references " + Constants.TABLE_NAME + "(" + Constants.COLUMN_Annonce_ID + "));";

    public Db(Context ctx) {

        super(ctx, Constants.DATABASE_NAME, null, Constants.DATA_vers);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_ANNONCE_TABLE);
        db.execSQL(CREATE_USER_SAUV);
        this.db = db;
    }

    public void insertContact(Contact c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_C_UNAME, c.getUserna());
        values.put(Constants.COLUMN_C_NAME, c.getName());
        values.put(Constants.COLUMN_C_PASS, c.getPass());
        values.put(Constants.COLUMN_C_ADR, c.getAddress());
        values.put(Constants.COLUMN_C_PHONE, c.getPhone());
        values.put(Constants.COLUMN_C_VILLE, c.getVille());
        db.insert(Constants.TABLE_C_NAME, null, values);
        db.close();
    }

    // Database lock to prevent conflicts.
    public static final Object[] databaseLock = new Object[0];

    public List<Annonce> getAnnonceUSERById(long id) {
        //Initialize an empty list of Annonces
        List<Annonce> tempAnncList = new ArrayList<Annonce>();
        //Command to select all Annonces
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.COLUMN_ID_USER + " = " + id;

        //lock database for reading
        synchronized (databaseLock) {
            //Get a readable database
            SQLiteDatabase database = getReadableDatabase();

            //Make sure database is not empty
            if (database != null) {

                //Get a cursor for all Annonces in the database
                Cursor cursor = database.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Annonce Annc = getAnnonce(cursor);
                        tempAnncList.add(Annc);
                        cursor.moveToNext();
                    }
                }
                //Close the database connection
                database.close();
            }
            //Return the list of Annonces
            return tempAnncList;
        }
    }

    public List<Annonce> getAllAnnc() {
        //Initialize an empty list of Annonces
        List<Annonce> annoncesList = new ArrayList<Annonce>();
        int id_user = MainActivity.k;
        //Command to select all Annonces
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.COLUMN_ID_USER + " NOT LIKE " + id_user;

        //lock database for reading
        synchronized (databaseLock) {
            //Get a readable database
            SQLiteDatabase database = getReadableDatabase();

            //Make sure database is not empty
            if (database != null) {

                //Get a cursor for all Annonces in the database
                Cursor cursor = database.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Annonce Annc = getAnnonce(cursor);
                        annoncesList.add(Annc);
                        cursor.moveToNext();
                    }
                }
                //Close the database connection
                database.close();
            }
            //Return the list of Annonces
            return annoncesList;
        }

    }


    private static Annonce getAnnonce(Cursor cursor) {
        Annonce anc = new Annonce();
        anc.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_Annonce_ID)));
        anc.setmTitre(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TITRE)));
        anc.setmPrix(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PRIX)));
        anc.setmType(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TYPE)));
        anc.setmCat(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CAT)));
        anc.setmDesc(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_DESC)));
        anc.setmId_user(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID_USER)));
        anc.setImagePath(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_IMAGE_PATH)));
        return anc;
    }

    public List<Annonce> getSearch(String ttr, String cat, String tp, String vl) {
        /*,String vl*/
        List<Annonce> tempAnncList = new ArrayList<Annonce>();
        //Command to select all Annonces
        int id_user = MainActivity.k;
        String query = "SELECT * FROM " + Constants.TABLE_NAME + " " +
                "where " + Constants.COLUMN_TITRE + " LIKE '" + ttr + "%' " +
                "AND " + Constants.COLUMN_CAT + " LIKE '" + cat + "' " +
                "AND " + Constants.COLUMN_TYPE + " LIKE '" + tp + "' " +
                "AND " + Constants.COLUMN_ID_USER + " IN (select id" +
                " from " + Constants.TABLE_C_NAME + " " +
                "where " + Constants.COLUMN_C_VILLE + " LIKE '" + vl + "')" +
                "AND " + Constants.COLUMN_ID_USER + " NOT LIKE " + id_user;


        //lock database for reading
        synchronized (databaseLock) {
            //Get a readable database
            SQLiteDatabase database = getReadableDatabase();

            //Make sure database is not empty
            if (database != null) {

                //Get a cursor for all Annonces in the database
                Cursor cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Annonce Annc = getAnnonce(cursor);
                        tempAnncList.add(Annc);
                        cursor.moveToNext();
                    }
                }
                //Close the database connection
                database.close();
            }
            //Return the list of Annonces
            return tempAnncList;
        }
    }

    public String searchPhone(int id) {
        db = this.getReadableDatabase();
        String query = "select "+Constants.COLUMN_C_PHONE+" from " + Constants.TABLE_C_NAME + " where " + Constants.COLUMN_C_ID + " =" + id;
        Cursor cur = db.rawQuery(query, null);
        String a = null;
        if (cur.moveToFirst()) {
            do {
                a = cur.getString(0);
            } while (cur.moveToNext());
        }
        db.close();
        return a;
    }
    public String searchName(int id) {
        db = this.getReadableDatabase();
        String query = "select "+Constants.COLUMN_C_NAME+" from " + Constants.TABLE_C_NAME + " where " + Constants.COLUMN_C_ID + " =" + id;
        Cursor cur = db.rawQuery(query, null);
        String a = null;
        if (cur.moveToFirst()) {
            do {
                a = cur.getString(0);
            } while (cur.moveToNext());
        }
        db.close();
        return a;
    }

    public int searchVill(String vl, int id) {
        db = this.getReadableDatabase();
        String query = "select id from " + Constants.TABLE_C_NAME + " where " + Constants.COLUMN_C_VILLE + " LIKE '" + vl + "'AND " + Constants.COLUMN_C_ID + " = " + id;
        Cursor cur = db.rawQuery(query, null);
        int a = 0;
        if (cur.moveToFirst()) {
            do {
                a = cur.getInt(0);
                if (a == MainActivity.k) {
                    break;
                }
            } while (cur.moveToNext());
        }
        db.close();
        return a;
    }
    /*public String searchVille(int id) {
        db = this.getReadableDatabase();
        String query = "SELECT  "+Constants.COLUMN_C_ADR+" FROM "+Constants.TABLE_C_NAME+" WHERE "+Constants.COLUMN_C_ID+" = "+id ;
        Cursor cur = db.rawQuery(query,null);
        String b = null;
        if(cur.moveToFirst()){
            do{
                b = cur.getString(0);
                String vl ="Marrakech";
                if(b.equals(vl)){
                    break;
                }
            }while (cur.moveToNext());
        }
        return b;
    }*/


//,

    public void Update_annonce(int id_annc, String titre, String Cat, String prx, String desc, String img) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + Constants.TABLE_NAME + " SET " + Constants.COLUMN_TITRE + "='" + titre + "'," + Constants.COLUMN_CAT + "='" + Cat + "'," + Constants.COLUMN_PRIX + "='" + prx + "'," + Constants.COLUMN_DESC + "='" + desc + "'," + Constants.COLUMN_IMAGE_PATH + "='" + img + "' WHERE " + Constants.COLUMN_Annonce_ID + "=" + id_annc;
        db.execSQL(query);
        db.close();

    }

    public Long addAnnonce(Annonce anc) {
        Long ret = null;

        //Lock database for writing
        synchronized (databaseLock) {
            //Get a writable database
            SQLiteDatabase database = getWritableDatabase();

            //Ensure the database exists
            if (database != null) {
                //Prepare the annonce information that will be saved to the database
                ContentValues values = new ContentValues();
                values.put(Constants.COLUMN_TITRE, anc.getmTitre());
                values.put(Constants.COLUMN_TYPE, anc.getmType());
                values.put(Constants.COLUMN_PRIX, anc.getmPrix());
                values.put(Constants.COLUMN_IMAGE_PATH, anc.getImagePath());
                values.put(Constants.COLUMN_DESC, anc.getmDesc());
                values.put(Constants.COLUMN_CAT, anc.getmCat());
                values.put(Constants.COLUMN_ID_USER, anc.getmId_user());
                //Attempt to insert the client information into the transaction table
                try {
                    ret = database.insert(Constants.TABLE_NAME, null, values);
                } catch (Exception e) {
                    Log.e(TAG, "Unable to add annonce to database " + e.getMessage());
                }
                //Close database connection
                database.close();
            }
        }
        return ret;
    }

    public void deleteById(int id_annc) {
        db = this.getReadableDatabase();
        String query = "DELETE FROM " + Constants.TABLE_NAME + " WHERE " + Constants.COLUMN_Annonce_ID + " = " + id_annc;
        db.execSQL(query);
        db.close();
    }

    public Annonce getAnnonceById(int id) {
        int k = MainActivity.k;
        List<Annonce> tempAnncList = getAnnonceUSERById(k);
        for (Annonce annonce : tempAnncList) {
            if (annonce.getId() == id) {
                return annonce;
            }
        }
        return null;
    }
    public Annonce getAnnonceView(int id) {

        List<Annonce> tempAnncList = getAllAnnc();
        for (Annonce annonce : tempAnncList) {
            if (annonce.getId() == id) {
                return annonce;
            }
        }
        return null;
    }


    public boolean annonceExists(long id) {
        //Check if there is an existing annonce
        List<Annonce> tempAnncList = getAllAnnc();
        for (Annonce annonce : tempAnncList) {
            if (annonce.getId() == id) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS " + Constants.TABLE_C_NAME;
        String query3 = "DROP TABLE IF EXISTS " + Constants.TABLE_U_NAME;
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        this.onCreate(db);
    }

    public String searchPass(String str) {
        db = this.getReadableDatabase();
        String query = "select uname,pass from " + Constants.TABLE_C_NAME;
        Cursor cur = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cur.moveToFirst()) {
            do {
                a = cur.getString(0);
                if (a.equals(str)) {
                    b = cur.getString(1);
                    break;
                }
            } while (cur.moveToNext());
        }
        db.close();
        return b;
    }

    public String searchUname(String str) {
        db = this.getReadableDatabase();
        String query = "select uname from " + Constants.TABLE_C_NAME;
        Cursor cur = db.rawQuery(query, null);
        String b;
        b = "not found";
        if (cur.moveToFirst()) {
            do {
                b = cur.getString(0);
                if (b.equals(str)) {
                    break;
                }
            } while (cur.moveToNext());
        }
        db.close();
        return b;
    }

    public int searchID(String str, String str2) {
        db = this.getReadableDatabase();
        String query = "select id from " + Constants.TABLE_C_NAME + " where " + Constants.COLUMN_C_UNAME + " LIKE '" + str + "' AND " + Constants.COLUMN_C_PASS + " LIKE '" + str2 + "' ";
        Cursor cur = db.rawQuery(query, null);
        int a = 0;
        if (cur.moveToFirst()) {
            do {
                a = cur.getInt(0);
                break;
            } while (cur.moveToNext());
        }
        db.close();
        return a;
    }

    public String searchAdrs(int str) {
        db = this.getReadableDatabase();
        String query = "select " + Constants.COLUMN_C_VILLE + "," + Constants.COLUMN_C_ADR + " from " + Constants.TABLE_C_NAME + " WHERE " + Constants.COLUMN_C_ID + "=" + str;
        Cursor cur = db.rawQuery(query, null);
        String b = null;
        if (cur.moveToFirst()) {
        do {
            b = cur.getString(0) + "," + cur.getString(1);
            break;
        } while (cur.moveToNext());
            db.close();
        }
        return b;
    }
    public Contact getProfilView(int id) {
        int id_user = MainActivity.k;
        List<Contact> tempList = getAllCompt();
        for (Contact comp : tempList) {
            if (comp.getId() == id_user) {
                return comp;
            }
        }
        return null;
    }

    public List<Contact> getAllCompt() {
        //Initialize an empty list of Annonces
        List<Contact> comptList = new ArrayList<Contact>();

        //Command to select all Annonces
        String selectQuery = "SELECT * FROM " + Constants.TABLE_C_NAME ;

        //lock database for reading
        synchronized (databaseLock) {
            //Get a readable database
            SQLiteDatabase database = getReadableDatabase();

            //Make sure database is not empty
            if (database != null) {

                //Get a cursor for all Annonces in the database
                Cursor cursor = database.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Contact Annc = getContact(cursor);
                        comptList.add(Annc);
                        cursor.moveToNext();
                    }
                }
                //Close the database connection
                database.close();
            }
            //Return the list of Annonces
            return comptList;
        }

    }
    private static Contact getContact(Cursor cursor) {
        Contact cmp = new Contact();
        cmp.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_C_ID)));
        cmp.setName(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_C_NAME)));
        cmp.setUserna(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_C_UNAME)));
        cmp.setPass(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_C_PASS)));
        cmp.setPhone(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_C_PHONE)));
        cmp.setAddress(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_C_ADR)));
        cmp.setVille(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_C_VILLE)));
        return cmp;
    }
    public void Update_Profil( String nam, String pas, String fon, String vil, String adr) {
        int id_user = MainActivity.k;
        db = this.getWritableDatabase();
        String query = "UPDATE " + Constants.TABLE_C_NAME + " SET " + Constants.COLUMN_C_NAME + "='" + nam + "'," + Constants.COLUMN_C_PASS + "='" + pas + "'," + Constants.COLUMN_C_PHONE + "='" + fon + "'," + Constants.COLUMN_C_VILLE + "='" + vil + "'," + Constants.COLUMN_C_ADR + "='" + adr + "' WHERE " + Constants.COLUMN_C_ID + "=" + id_user;
        db.execSQL(query);
        db.close();

    }
    /*public List<Annonce> getAnnonceSauveById(long id) {
        //Initialize an empty list of Annonces
        List<Annonce> tempAnncList = new ArrayList<Annonce>();
        //Command to select all Annonces
        String selectQuery = "SELECT "+Constants.COLUMN_Annonce_ID+" FROM " + Constants.TABLE_U_NAME + " WHERE " + Constants.COLUMN_ID_USER + " = " + id;

        //lock database for reading
        synchronized (databaseLock) {
            //Get a readable database
            SQLiteDatabase database = getReadableDatabase();

            //Make sure database is not empty
            if (database != null) {

                //Get a cursor for all Annonces in the database
                Cursor cursor = database.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        f= Integer.parseInt(cursor.toString());
                        Annonce Annc = getAnnonceById(f);
                        tempAnncList.add(Annc);
                        cursor.moveToNext();
                    }
                }
                //Close the database connection
                database.close();
            }
            //Return the list of Annonces
            return tempAnncList;
        }
    }
    public Long addSauve (Sauvegarde anc) {
        Long ret = null;

        //Lock database for writing
        synchronized (databaseLock) {
            //Get a writable database
            SQLiteDatabase database = getWritableDatabase();

            //Ensure the database exists
            if (database != null) {
                //Prepare the annonce information that will be saved to the database
                ContentValues values = new ContentValues();
                values.put(Constants.COLUMN_Annonce_ID,anc.getId_annoncce());
                values.put(Constants.COLUMN_ID_USER, anc.getId_user());
                //Attempt to insert the client information into the transaction table
                try {
                    ret = database.insert(Constants.TABLE_U_NAME, null, values);
                } catch (Exception e) {
                    Log.e(TAG, "Unable to add annonce to database " + e.getMessage());
                }
                //Close database connection
                database.close();
            }
        }
        return ret;
    }
    private static Sauvegarde getSauve(Cursor cursor) {
        Sauvegarde anc = new Sauvegarde();
        anc.setId_annoncce(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_Annonce_ID)));
        anc.setId_user(cursor.getInt((cursor.getColumnIndex(Constants.COLUMN_ID_USER))));
        return anc;
    }*/
}