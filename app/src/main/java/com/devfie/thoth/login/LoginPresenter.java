package com.devfie.thoth.login;

import android.content.Context;

import com.devfie.thoth.R;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;

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
    public void login(String email, String password) {
        new NetworkRequests(mContext).login(email, password, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
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
