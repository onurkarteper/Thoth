package com.devfie.thoth.register;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public interface RegisterUsernameStepContract {
    interface View extends BaseView<RegisterUsernameStepContract.Presenter> {
        void onUsernameCanBeRegister();
        void onUsernameAlreadyRegistered();
        void checkUsernameConnectionError();
    }

    interface Presenter extends BasePresenter {
        void checkUsername(String username);
    }
}
