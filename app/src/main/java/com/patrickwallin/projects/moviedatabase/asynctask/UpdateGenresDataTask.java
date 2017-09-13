package com.patrickwallin.projects.moviedatabase.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.patrickwallin.projects.moviedatabase.data.GenresContract;
import com.patrickwallin.projects.moviedatabase.data.GenresData;
import com.patrickwallin.projects.moviedatabase.gsondata.Genre;
import com.patrickwallin.projects.moviedatabase.gsondata.Genres;

import java.util.List;

import timber.log.Timber;

/**
 * Created by piwal on 9/11/2017.
 */

public class UpdateGenresDataTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    private Genres mGenres;
    private OnTaskCompleted mOnTaskCompleted;

    public UpdateGenresDataTask(Context context, Genres genres, OnTaskCompleted onTaskCompleted) {
        mContext = context;
        mGenres = genres;
        this.mOnTaskCompleted = onTaskCompleted;
        Timber.d("UpdateGenresDataTask");
    }

    @Override
    protected Void doInBackground(Void... params) {
        Timber.d("UpdateGenresDataTask-doInBackground");
        GenresContract.deleteAll(mContext);

        if(mGenres != null) {
            List<Genre> genreList = mGenres.getGenres();
            if(genreList != null && genreList.size() > 0) {
                for(int i = 0; i < genreList.size(); i++) {
                    GenresData genresData = new GenresData(genreList.get(i).getId(), genreList.get(i).getName());
                    GenresContract.insert(mContext,genresData);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Timber.d("UpdateGenresDataTask-onPostExecute");
        if(mOnTaskCompleted != null) {
            Timber.d("fireOnTaskCompleted");
            mOnTaskCompleted.onTaskCompleted();
        }
    }
}
