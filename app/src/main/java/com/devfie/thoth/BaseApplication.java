package com.devfie.thoth;

import android.app.Application;

import com.devfie.thoth.data.Constants;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.manager.LoginManager;
import com.devfie.thoth.network.ConnectionManager;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Onur Karteper on 4/2/2017.
 */




public class BaseApplication extends Application {
    public static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        new SecurePrefManagerInit.Initializer(getApplicationContext())
                .useEncryption(true)
                .initialize();
        ConnectionManager.initialize(this);
        LocalDataManager.initialize(this);
        LoginManager.initialize(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
