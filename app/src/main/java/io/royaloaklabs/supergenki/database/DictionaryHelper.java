package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
      } catch(IOException e) {
        // DB file does not exist in the assets directory
        // create an in-memory database to work with
        mDatabase = SQLiteDatabase.create(null);
        mDatabase.execSQL("CREATE VIRTUAL TABLE einihongo USING fts4(kanji,kana,gloss,romaji)");
        mDatabase.execSQL("INSERT INTO einihongo VALUES(?, ?, ?, ?)", new String[]{"此れ;是;是れ", "これ", "this (indicating an item near the speaker, the action of the speaker, or the current topic)", "kore"});
        mDatabase.execSQL("INSERT INTO einihongo VALUES(?, ?, ?, ?)", new String[]{"", "は", "topic marker particle(SG)indicates contrast with another option (stated or unstated)(SG)adds emphasis", "wa"});
        mDatabase.execSQL("INSERT INTO einihongo VALUES(?, ?, ?, ?)", new String[]{"", "テスト", "test", "tesuto"});
        mDatabase.execSQL("INSERT INTO einihongo VALUES(?, ?, ?, ?)", new String[]{"", "データ", "data(SG)datum" , "deita"});
      }
    }

    // set the main database as either the asset or the in-memory version
    mDatabase = (mDatabase == null)
        ? SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY)
        : mDatabase;
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
  public SQLiteDatabase getReadableDatabase() throws SQLException {
    return mDatabase;
  }

  private boolean hasNoDatabase() {
    SQLiteDatabase db = null;

    try {
      db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
      db.close();
    } catch(SQLException e) {
      // do nothing, db doesn't exists
    }

    return db == null;
  }

  private void copyInternalDatabase() throws IOException {
    InputStream is = mContext.getAssets().open(DATABASE_NAME);
    OutputStream os = new FileOutputStream(DATABASE_PATH);

    byte[] buffer = new byte[1028];
    int length;
    while((length = is.read(buffer)) > 0) {
      os.write(buffer, 0, length);
    }

    os.flush();

    is.close();
    os.close();
  }
}
