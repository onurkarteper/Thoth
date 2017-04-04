package com.devfie.thoth.manager;

import android.content.Context;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class LoginManager {

    Context context;
    private static LoginManager instance;
    LocalDataManager localDataManager;
    private static final String TAG = LoginManager.class.getSimpleName();
    public String mobileToken = "";

    public synchronized static void initialize(Context context) {
        if (instance == null) {
            instance = new LoginManager(context);
        }
     //   instance.mobileToken = FirebaseInstanceId.getInstance().getToken();
    }

    public LoginManager(Context context) {
        this.context = context;
        localDataManager = LocalDataManager.getInstance();

    }

    public synchronized static LoginManager getInstance() {
        return instance;
    }

}
