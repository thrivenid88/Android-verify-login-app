package com.example.dummyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_dummy.db";  // Database file name
    private static final int DATABASE_VERSION = 2;  // Version to handle upgrades
    private static String DB_PATH = "";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DB_PATH = context.getDatabasePath(DATABASE_NAME).getPath();  // Database location
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create 'data_cert' table
        String createDataCertTable = "CREATE TABLE data_cert (" +
                "cert_id TEXT PRIMARY KEY, " +
                "name TEXT, " +
                "title TEXT, " +
                "issue_date DATE, " +
                "expiry_date DATE, " +
                "status TEXT, " +
                "description TEXT, " +
                "img_url TEXT, " +
                "updated_on TIMESTAMP" +
                ");";
        db.execSQL(createDataCertTable);

        // Insert data into 'data_cert' table
        String insertDataCert = "INSERT INTO data_cert (cert_id, name, title, issue_date, expiry_date, status, description, img_url, updated_on) " +
                "VALUES " +
                "('DUMMY01', 'Anonymous', 'Test_User', '2024-10-16', '0000-00-00', 'active', 'The above document is issued from aaa centre and is valid throughout the above mentioned date', 'https://4.imimg.com/data4/JE/XJ/MY-6362665/certificate.jpg', '2024-10-16 00:00:00')," +
                "('DUMMY02', 'user-2', 'unknown', '2024-09-01', '2024-10-31', 'reviewing', 'The above certificate is under review and please wait for review process to complete', 'https://piktochart.com/.../large-271-600x424.jpg', '2024-10-01 00:00:00');";
        db.execSQL(insertDataCert);

        // Create 'login' table
        String createLoginTable = "CREATE TABLE login (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "access_id TEXT, " +
                "access_code TEXT, " +
                "access_token TEXT" +
                ");";
        db.execSQL(createLoginTable);

        // Insert data into 'login' table
        String insertLoginData = "INSERT INTO login (access_id, access_code, access_token) " +
                "VALUES ('123456', '@12#1', ''), ('101010', 'test', '');";
        db.execSQL(insertLoginData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS data_cert");
            db.execSQL("DROP TABLE IF EXISTS login");
            onCreate(db);  // Recreate tables on upgrade
        }
    }

    // Check if the database exists in the device's data directory
    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Database does not exist yet.");
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;  // Return true if the database exists
    }

    // Copy the pre-populated database from assets (if using pre-built db)
    private void copyDatabase() throws IOException {
        InputStream input = context.getAssets().open(DATABASE_NAME);  // Open .db file from assets
        String outFileName = DB_PATH;
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();
    }

    // Initialize the database by checking and copying
    public void initializeDatabase() {
        if (!checkDatabase()) {
            this.getReadableDatabase();  // This triggers onCreate() method
            try {
                copyDatabase();  // Copy the pre-populated database
                Log.d("DatabaseHelper", "Database copied successfully");
            } catch (IOException e) {
                Log.e("DatabaseHelper", "Error copying database", e);
                throw new Error("Error copying database");
            }
        }
    }

    // Retrieve certificate by ID
    public Cursor getCertificateById(String certificateId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM data_cert WHERE cert_id = ?";
        return db.rawQuery(query, new String[]{certificateId});
    }
}
