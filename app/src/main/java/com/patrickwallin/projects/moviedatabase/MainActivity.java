package com.patrickwallin.projects.moviedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.patrickwallin.projects.moviedatabase.asynctask.OnTaskCompleted;
import com.patrickwallin.projects.moviedatabase.asynctask.UpdateGenresDataTask;
import com.patrickwallin.projects.moviedatabase.data.GenresContract;
import com.patrickwallin.projects.moviedatabase.data.GenresData;
import com.patrickwallin.projects.moviedatabase.gsondata.Genres;
import com.patrickwallin.projects.moviedatabase.utilities.NetworkUtils;

import java.util.List;

import okhttp3.Credentials;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        NetworkUtils networkUtils = new NetworkUtils(MainActivity.this);
        if (networkUtils.isNetworkConnected()) {
            StringBuilder sbURLAddress = new StringBuilder();
            sbURLAddress.append(getString(R.string.themoviedb_api_version));
            sbURLAddress.append(getString(R.string.themoviedb_api_genres));

            AndroidNetworking.get(sbURLAddress.toString())
                    .addQueryParameter(getString(R.string.query_api_key), getString(R.string.themoviedb_api_key))
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            try {
                                Genres genres = gson.fromJson(response, Genres.class);
                                new UpdateGenresDataTask(MainActivity.this, genres, MainActivity.this).execute();
                            } catch (JsonSyntaxException e) {
                                Log.i("JSONSYNTAXException", e.getMessage());
                            } catch (JsonParseException e) {
                                Log.i("JsonParseException", e.getMessage());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.i("tag-error", anError.getMessage());
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);

        MenuItem menuItem = menu.findItem(R.id.action_filtering_genres);
        SubMenu subMenu = menuItem.getSubMenu();
        if(subMenu.size() == 0) {
            List<GenresData> genresDataList = GenresContract.getGenresDataInSortByName(MainActivity.this);
            if(genresDataList != null && !genresDataList.isEmpty()) {
                for(int i = 0; i < genresDataList.size(); i++) {
                    subMenu.add(1,genresDataList.get(i).getId(),i,genresDataList.get(i).getName());
                }
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTaskCompleted() {
        Timber.d("onTaskCompleted");

        this.invalidateOptionsMenu();
    }
/*
    @Override
    public void onTaskCompleted() {
        Timber.d("onTaskCompleted");

        this.invalidateOptionsMenu();
    }
    */
}
