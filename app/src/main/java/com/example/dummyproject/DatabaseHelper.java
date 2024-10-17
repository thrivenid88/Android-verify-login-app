package com.example.dummyproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "db_dummy";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_INFO = "info";
//    private static final String TABLE_LOGIN = "login";

    // Column Names for 'info' table
    private static final String COLUMN_CERTIFICATE_ID = "certificate_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ISSUE_DATE = "issue_date";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMG_URL = "img_url";

    // Column Names for 'login' table
//    public static final String COLUMN_ID = "id";
//    public static final String COLUMN_ACCESS_ID = "access_id";
//    public static final String COLUMN_ACCESS_CODE = "access_code";
//    public static final String COLUMN_AUTH_TOKEN = "auth_token";

    // SQL Queries to create tables
    public static final String CREATE_TABLE_INFO = "CREATE TABLE " + TABLE_INFO + "("
            + COLUMN_CERTIFICATE_ID + " TEXT NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_ISSUE_DATE + " DATE NOT NULL, "
            + COLUMN_EXPIRY_DATE + " DATE, "
            + COLUMN_STATUS + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " LONGTEXT, "
            + COLUMN_IMG_URL + " TEXT NOT NULL" + ")";

//    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
//            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + COLUMN_ACCESS_ID + " INTEGER NOT NULL, "
//            + COLUMN_ACCESS_CODE + " INTEGER NOT NULL, "
//            + COLUMN_AUTH_TOKEN + " LONGTEXT NOT NULL CHECK (json_valid(" + COLUMN_AUTH_TOKEN + "))"
//            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_INFO);
//        db.execSQL(CREATE_TABLE_LOGIN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_INFO + " ADD COLUMN new_column_name TEXT");
        }
        // Add more upgrade logic for future versions as needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        onCreate(db);
    }






//    private void insertSampleData(SQLiteDatabase db) {
//        ContentValues values = new ContentValues();
//
//        // Sample data 1
//        values.put(COLUMN_CERTIFICATE_ID, "CERT001");
//        values.put(COLUMN_NAME, "John Doe");
//        values.put(COLUMN_TITLE, "Certified Expert");
//        values.put(COLUMN_ISSUE_DATE, "2024-01-01");
//        values.put(COLUMN_EXPIRY_DATE, "2025-01-01");
//        values.put(COLUMN_STATUS, "Active");
//        values.put(COLUMN_DESCRIPTION, "This is a sample certificate.");
//        values.put(COLUMN_IMG_URL, "http://example.com/image1.png"); // Add sample image URL if necessary
//        db.insert(TABLE_INFO, null, values);
//
//        // Sample data 2 (if you want to add more)
//        values.clear(); // Clear the ContentValues to reuse
//        values.put(COLUMN_CERTIFICATE_ID, "CERT002");
//        values.put(COLUMN_NAME, "Jane Smith");
//        values.put(COLUMN_TITLE, "Certified Professional");
//        values.put(COLUMN_ISSUE_DATE, "2024-02-01");
//        values.put(COLUMN_EXPIRY_DATE, "2026-02-01");
//        values.put(COLUMN_STATUS, "Active");
//        values.put(COLUMN_DESCRIPTION, "This is another sample certificate.");
//        values.put(COLUMN_IMG_URL, "http://example.com/image2.png"); // Add sample image URL if necessary
//        db.insert(TABLE_INFO, null, values);
//    }


    // Method to verify login credentials
//    public boolean verifyLogin(String accessId, String accessCode) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_ACCESS_ID + "=? AND " + COLUMN_ACCESS_CODE + "=?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(accessId), String.valueOf(accessCode)});
//        boolean result = cursor.getCount() > 0;
//        cursor.close();
//        db.close();
//        return result;
//    }

//    // Method to retrieve certificate information
//    public Cursor getCertificateData(String certificateId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_INFO + " WHERE " + COLUMN_CERTIFICATE_ID + "=?";
//        return db.rawQuery(query, new String[]{certificateId});
//    }

    // Method to verify if a user exists based on access ID and access code
//        public boolean checkUser(String accessId, String accessCode) {
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            // Columns to return (assuming you're checking if the user exists based on accessId and accessCode)
////            String[] columns = {COLUMN_ID};  // Assuming you return the ID if the user exists
//
//            // Selection criteria: verifying the access ID and access code
////            String selection = COLUMN_ACCESS_ID + " = ? AND " + COLUMN_ACCESS_CODE + " = ?";
//            String[] selectionArgs = {String.valueOf(accessId), String.valueOf(accessCode)};
//
//            // Query the login table to check if the user exists
//            Cursor cursor = db.query(selectionArgs, null, null, null);
//            int count = cursor.getCount();
//            cursor.close();
//            db.close();
//
//            return count > 0;  // Return true if user exists, false otherwise
//        }

    public Cursor getCertificateById(String certificateId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_INFO + " WHERE " + COLUMN_CERTIFICATE_ID + "=?";
        return db.rawQuery(query, new String[]{certificateId});
    }
    public boolean insertInfo(String certificateId, String name, String title,
                              String issueDate, String expiryDate, String status,
                              String description, String imgUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CERTIFICATE_ID, certificateId);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_ISSUE_DATE, issueDate);
        contentValues.put(COLUMN_EXPIRY_DATE, expiryDate);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_IMG_URL, imgUrl);

        long result = db.insert(TABLE_INFO, null, contentValues);
        db.close();
        return result != -1; // Returns true if the insertion was successful
    }

}