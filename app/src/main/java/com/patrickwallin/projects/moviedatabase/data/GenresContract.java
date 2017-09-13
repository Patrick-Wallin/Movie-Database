package com.patrickwallin.projects.moviedatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piwal on 9/11/2017.
 */

public class GenresContract {
    public static final String SCHEME = "content://";
    public static final String CONTENT_AUTHORITY = "com.patrickwallin.projects.moviedatabase";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + CONTENT_AUTHORITY);
    public static final String PATH_GENRES = "genres";

    public static final class GenresEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRES).build();

        public static final String TABLE_NAME = PATH_GENRES;

        public static final String COLUMN_GENRES_ID = "genres_id";
        public static final String COLUMN_GENRES_NAME = "genres_name";

        public static final int COL_GENRES_ID = 1;
        public static final int COL_GENRES_NAME = 2;
    }

    public static String getCreateTableStatement() {
        StringBuilder createTableTableStatement = new StringBuilder();

        createTableTableStatement.append("CREATE TABLE IF NOT EXISTS ");
        createTableTableStatement.append(PATH_GENRES);
        createTableTableStatement.append(" (");
        createTableTableStatement.append(GenresEntry._ID);
        createTableTableStatement.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        createTableTableStatement.append(GenresEntry.COLUMN_GENRES_ID);
        createTableTableStatement.append(" INTEGER NOT NULL, ");
        createTableTableStatement.append(GenresEntry.COLUMN_GENRES_NAME);
        createTableTableStatement.append(" TEXT NOT NULL ");
        createTableTableStatement.append(")");

        return createTableTableStatement.toString();
    }

    public static List<GenresData> getGenresDataInSortByName(Context context) {
        List<GenresData> genresDataList = new ArrayList<GenresData>();

        Cursor cursor = context.getContentResolver().query(
                GenresEntry.CONTENT_URI,
                null, null, null, GenresEntry.COLUMN_GENRES_NAME + " ASC");
        if(cursor != null) {
            while (cursor.moveToNext()) {
                GenresData genresData = new GenresData(cursor);
                genresDataList.add(genresData);
            }
            cursor.close();
        }

        return genresDataList;
    }

    public static void deleteAll(Context context) {
        context.getContentResolver().delete(GenresEntry.CONTENT_URI, null, null);
    }

    public static void insert(Context context, GenresData genresData) {
        context.getContentResolver().insert(GenresEntry.CONTENT_URI, genresData.getGenresContentValues());
    }
}
