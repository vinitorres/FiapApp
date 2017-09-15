package app.vini.com.br.fiapapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Vini on 17/07/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MeAdota.db";

    // Table Names
    private static final String DB_TABLE_IMAGENS = "imagens";
    private static final String DB_TABLE_LOCATION = "localizacao";


    // column names imagens
    private static final String KEY_NAME = "image_name";
    private static final String KEY_IMAGE_URL = "image_name";
    private static final String KEY_IMAGE = "image_data";

    // column names localizacao

    private static final String KEY_LOCAL = "key";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE_IMAGENS + "(" +
            KEY_NAME + " TEXT," +
            KEY_IMAGE + " BLOB," +
            KEY_IMAGE_URL + "TEXT);";

    private static final String CREATE_TABLE_LOCALIZACAO = "CREATE TABLE " + DB_TABLE_LOCATION + "(" +
            KEY_LOCAL + " TEXT," +
            KEY_LATITUDE + " TEXT," +
            KEY_LONGITUDE + "TEXT);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
        db.execSQL(CREATE_TABLE_LOCALIZACAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_IMAGENS);

        // create new table
        onCreate(db);
    }

    public void addImage(String name, byte[] image, String url) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, name);
        cv.put(KEY_IMAGE, image);
        cv.put(KEY_IMAGE_URL, url);

        database.insert(DB_TABLE_IMAGENS, null, cv);
    }

    public void addLocalizacao(String latitude,String longitude) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_LOCAL, KEY_LOCAL);
        cv.put(KEY_LATITUDE, latitude);
        cv.put(KEY_LONGITUDE, longitude);

        database.insert(DB_TABLE_LOCATION, null, cv);
    }

    public void atualizarLocalizacao(String latitude, String longitude) {
        SQLiteDatabase database = this.getWritableDatabase();
        String strSQL = "UPDATE "+DB_TABLE_LOCATION+" SET "+KEY_LATITUDE+" = "+latitude+", "+KEY_LONGITUDE+" = "+longitude+" WHERE "+KEY_LOCAL+" = '"+ KEY_LOCAL +"'";
        database.execSQL(strSQL);
    }

    public ArrayList<String> getLocal(){

        ArrayList<String> coords = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DB_TABLE_LOCATION;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            String latitude = cursor.getString(cursor.getColumnIndex(KEY_LATITUDE));
            String longitude = cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE));

            coords.add(latitude);
            coords.add(longitude);
        }

        return coords;
    }

    public ArrayList<Bitmap> getImages() {

        ArrayList<Bitmap> imageList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DB_TABLE_IMAGENS;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                imageList.add(getImage(cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE))));
            } while (cursor.moveToNext());
        }
        return imageList;

    }

    public ArrayList<String> getUrls() {
        ArrayList<String> imageUrlList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DB_TABLE_IMAGENS;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                imageUrlList.add(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URL)));
            } while (cursor.moveToNext());
        }

        database.close();

        return imageUrlList;
    }

    public void removerImage(String url) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + DB_TABLE_IMAGENS + " WHERE " + KEY_IMAGE_URL + "= '" + url + "'");

        //Close the database
        database.close();
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}