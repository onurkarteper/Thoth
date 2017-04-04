package com.devfie.thoth.register;

import android.content.Context;

import com.devfie.thoth.login.LoginContract;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class RegisterFirstStepPresenter implements RegisterFirstStepContract.Presenter {

    private final RegisterFirstStepContract.View mRegisterView;
    private final Context mContext;

    public RegisterFirstStepPresenter(RegisterFirstStepContract.View mRegisterView, Context mContext) {
        this.mRegisterView = mRegisterView;
        this.mContext = mContext;

        mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void checkEmail(String email) {
        new NetworkRequests(mContext).checkEmail(email, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mRegisterView.onEmailCanBeRegister();
            }

            @Override
            public void onError(String errorMsg) {
                mRegisterView.onEmailAlreadyRegistered();
            }

            @Override
            public void onServerError() {
                mRegisterView.checkEmailConnectionError();
            }
        });
    }
}
