package com.devfie.thoth.register;

import android.content.Context;

import com.devfie.thoth.R;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.model.response.LoginResponse;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class RegisterSecondStepPresenter implements RegisterSecondStepContract.Presenter {

    private final RegisterSecondStepContract.View mRegisterView;
    private final Context mContext;

    public RegisterSecondStepPresenter(RegisterSecondStepContract.View mRegisterView, Context mContext) {
        this.mRegisterView = mRegisterView;
        this.mContext = mContext;

        mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void register(final String email, String username, final String password) {
        new NetworkRequests(mContext).register(email, username, password, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                LoginResponse loginResponse = (LoginResponse) obj;
                mRegisterView.onRegisterSuccess();
                LocalDataManager.getInstance().login();
                LocalDataManager.getInstance().saveLoginData(email, password);
                LocalDataManager.getInstance().saveToken(loginResponse.getResponse().getToken());
                LocalDataManager.getInstance().saveId(loginResponse.getResponse().getId());
            }

            @Override
            public void onError(String errorMsg) {
                mRegisterView.onRegisterFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                mRegisterView.onRegisterFailed(mContext.getString(R.string.error_register_network));
            }
        });
    }
}
