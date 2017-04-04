package com.devfie.thoth.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.devfie.thoth.model.User;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class LocalDataManager {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static LocalDataManager instance;
    private User currentUserInfo;

    public synchronized static void initialize(Context context) {
        if (instance == null) {
            instance = new LocalDataManager(context);
        }
    }

    public LocalDataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(this.getClass().getSimpleName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public synchronized static LocalDataManager getInstance() {
        return instance;
    }

}
