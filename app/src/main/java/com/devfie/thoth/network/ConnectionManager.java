package com.devfie.thoth.network;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devfie.thoth.R;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class ConnectionManager {

    private Context context;
    private static ConnectionManager mInstance;

    public static synchronized ConnectionManager getInstance() {
        return mInstance;
    }

    public static void initialize(Context context){
        if(mInstance == null)
            mInstance = new ConnectionManager(context);
    }

    public ConnectionManager(Context context) {
        this.context = context;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void showTryAgainDialog(Context mContext,final TryAgainCallback tryAgainCallback){
        new MaterialDialog.Builder(mContext).positiveText(R.string.try_again)
                .content(R.string.error_network_connection)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tryAgainCallback.tryAgain(true);
                    }
                })
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        tryAgainCallback.tryAgain(false);
                    }
                }).show();
    }

    public interface TryAgainCallback{
        public void tryAgain(Boolean tryAgain);
    }
}
