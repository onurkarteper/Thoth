package com.devfie.thoth.login;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void onLoginFailed(String result);
        void onLoginSuccess();
    }

    interface Presenter extends BasePresenter {
        void login(String email, String password);
    }

}
