package com.devfie.thoth.register;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;
import com.devfie.thoth.login.LoginContract;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public interface RegisterFirstStepContract {
    interface View extends BaseView<RegisterFirstStepContract.Presenter> {
        void onEmailCanBeRegister();
        void onEmailAlreadyRegistered();
        void checkEmailConnectionError();
    }

    interface Presenter extends BasePresenter {
        void checkEmail(String email);
    }
}
