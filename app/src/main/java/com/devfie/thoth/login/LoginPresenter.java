package com.devfie.thoth.login;

import android.content.Context;
import android.widget.Toast;

import com.devfie.thoth.R;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.model.response.LoginResponse;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;
    private final Context mContext;

    public LoginPresenter(Context context, LoginContract.View mLoginView) {
        this.mLoginView = mLoginView;
        this.mContext = context;
        mLoginView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void login(final String email, final String password) {
        new NetworkRequests(mContext).login(email, password, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                LoginResponse response = (LoginResponse) obj;
                LocalDataManager.getInstance().saveToken(response.getResponse().getToken());
                LocalDataManager.getInstance().login();
                LocalDataManager.getInstance().saveLoginData(email, password);
                LocalDataManager.getInstance().saveId(response.getResponse().getId());
                mLoginView.onLoginSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                mLoginView.onLoginFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                mLoginView.onLoginFailed(mContext.getString(R.string.error_login));
            }
        });
    }


}
