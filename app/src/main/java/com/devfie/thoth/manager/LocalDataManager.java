package com.devfie.thoth.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.devfie.thoth.data.Constants;
import com.devfie.thoth.model.User;
import com.google.gson.Gson;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class LocalDataManager {
    Context context;
    private static LocalDataManager instance;
    SecurePrefManager prefManager;

    public synchronized static void initialize(Context context) {
        if (instance == null) {
            instance = new LocalDataManager(context);
        }
    }

    public LocalDataManager(Context context) {
        this.context = context;
        prefManager = SecurePrefManager.with(context);
    }

    public synchronized static LocalDataManager getInstance() {
        return instance;
    }

    public void saveLoginData(String email, String password) {
        prefManager.set(Constants.KEY_EMAIL).value(email).go();
        prefManager.set(Constants.KEY_PASSWORD).value(password).go();
    }

    public void login() {
        prefManager.set(Constants.KEY_IS_LOGIN).value(true).go();
    }

    public boolean isLogin() {
        return prefManager.get(Constants.KEY_IS_LOGIN).defaultValue(false).go();
    }

    public void logout() {
        prefManager.remove(Constants.KEY_EMAIL).confirm();
        prefManager.remove(Constants.KEY_PASSWORD).confirm();
        prefManager.remove(Constants.KEY_IS_LOGIN).confirm();
        prefManager.remove(Constants.KEY_TOKEN).confirm();
    }

    public void saveToken(String token) {
        prefManager.set(Constants.KEY_TOKEN).value(token).go();
    }

    public String getToken() {
        return prefManager.get(Constants.KEY_TOKEN).defaultValue("").go();
    }

    public String getEmail() {
        return prefManager.get(Constants.KEY_EMAIL).defaultValue("").go();
    }

    public String getPassword() {
        return prefManager.get(Constants.KEY_PASSWORD).defaultValue("").go();
    }

    public void saveUserInfo(User user) {
        prefManager.set(Constants.KEY_USER_INFO).value(user.toString()).go();
        prefManager.set(Constants.KEY_MY_ID).value(user.getId()).go();
    }

    public User getUserInfo() {

        String json = prefManager.get(Constants.KEY_USER_INFO).defaultValue("").go();
        User user = new Gson().fromJson(json, User.class);
        return user;
    }

    public String getId() {
        return prefManager.get(Constants.KEY_MY_ID).defaultValue("").go();
    }

    public void saveId(String id) {
        prefManager.set(Constants.KEY_MY_ID).value(id).go();
    }

    public boolean isMyId(String id) {
        return prefManager.get(Constants.KEY_MY_ID).defaultValue("").go().equals(id);
    }
}
