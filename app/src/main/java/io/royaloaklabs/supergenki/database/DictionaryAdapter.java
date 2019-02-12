package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DictionaryAdapter {
    protected DictionaryHelper mDictHelper;

    public DictionaryAdapter(Context context) {
        this.mDictHelper = new DictionaryHelper(context);
    }

}
