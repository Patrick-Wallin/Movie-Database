package com.patrickwallin.projects.moviedatabase.data;

import android.content.ContentValues;
import android.database.Cursor;

import org.parceler.Parcel;

/**
 * Created by piwal on 9/11/2017.
 */

@Parcel
public class GenresData {
    int mId;
    String mName;

    public GenresData() {}

    public GenresData(Cursor cursor) {
        if(cursor != null) {
            setId(cursor.getInt(GenresContract.GenresEntry.COL_GENRES_ID));
            setName(cursor.getString(GenresContract.GenresEntry.COL_GENRES_NAME));
        }
    }

    public GenresData(int id, String name) {
        setId(id);
        setName(name);
    }
    public int getId() { return mId; }
    public void setId(int id) { mId = id; }

    public String getName() { return mName; }
    public void setName(String name) { mName = name; }

    public ContentValues getGenresContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(GenresContract.GenresEntry.COLUMN_GENRES_ID, getId());
        contentValues.put(GenresContract.GenresEntry.COLUMN_GENRES_NAME, getName());

        return contentValues;
    }

}
