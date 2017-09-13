package com.patrickwallin.projects.moviedatabase.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.patrickwallin.projects.moviedatabase.R;

/**
 * Created by piwal on 9/11/2017.
 */

public class NetworkUtils {
    private Context mContext;

    public NetworkUtils(Context context) {
        mContext = context;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void showAlertMessageAboutNoInternetConnection(final boolean goBackToPreviousActivity) {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.no_internet_connection_title))
                .setMessage(mContext.getString(R.string.no_internet_connection_message))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //if(goBackToPreviousActivity) {
                        //OnGoBackChangeListener listener = (OnGoBackChangeListener) mContext;
                        //listener.OnGoBackChanged();
                        //}
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
