package com.devfie.thoth;

import android.app.Application;

import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.manager.LoginManager;
import com.devfie.thoth.network.ConnectionManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ConnectionManager.initialize(this);
        LocalDataManager.initialize(this);
        LoginManager.initialize(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
