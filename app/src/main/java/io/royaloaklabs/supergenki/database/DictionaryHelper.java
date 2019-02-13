package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DictionaryHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "jisho-main.db";
    private static final int DATABASE_VERSION = 1;
    private final String DATABASE_PATH;

    private SQLiteDatabase mDatabase;

    private final Context mContext;

    public DictionaryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        DATABASE_PATH = String.format("%s/%s", mContext.getFilesDir().getAbsolutePath(), DATABASE_NAME);

        if(this.hasNoDatabase()) {
            try {
                this.copyInternalDatabase();
            } catch (IOException e) {
                // handle exception
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Autogenerated onCreate method
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Autogenerated onUpgrade method
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        if(mDatabase.isOpen()) {
            return mDatabase;
        }

        try {
            connect();
        } catch(SQLException e) {
            // handle exception
        }

        return mDatabase;
    }

    public void connect() throws SQLException{
        mDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    public boolean hasNoDatabase() {
        SQLiteDatabase db = null;

        try {
            db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
        } catch(SQLException e) {
            // do nothing, db doesn't exists
        } catch(Exception e) {
            // do nothing, db doesn't exists
        }

        return db == null;
    }

    private void copyInternalDatabase() throws IOException {
        InputStream is = mContext.getAssets().open(DATABASE_NAME);
        OutputStream os = new FileOutputStream(DATABASE_PATH);

        byte[] buffer = new byte[1028];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        os.flush();

        is.close();
        os.close();
    }
}