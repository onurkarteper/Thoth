package com.devfie.thoth.register;

import android.content.Context;

import com.devfie.thoth.R;
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
    public void register(String email, String password) {
        new NetworkRequests(mContext).register(email, password, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mRegisterView.onRegisterSuccess();
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
