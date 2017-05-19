package com.devfie.thoth.register;

import android.content.Context;

import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class RegisterUsernameStepPresenter implements RegisterUsernameStepContract.Presenter {

    private final RegisterUsernameStepContract.View mRegisterView;
    private final Context mContext;

    public RegisterUsernameStepPresenter(RegisterUsernameStepContract.View mRegisterView, Context mContext) {
        this.mRegisterView = mRegisterView;
        this.mContext = mContext;

        mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void checkUsername(String username) {
        new NetworkRequests(mContext).checkUsername(username, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mRegisterView.onUsernameCanBeRegister();
            }

            @Override
            public void onError(String errorMsg) {
                mRegisterView.onUsernameAlreadyRegistered();
            }

            @Override
            public void onServerError() {
                mRegisterView.checkUsernameConnectionError();
            }
        });
    }
}
