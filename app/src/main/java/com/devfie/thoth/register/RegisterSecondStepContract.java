package com.devfie.thoth.register;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public interface RegisterSecondStepContract {

    interface View extends BaseView<RegisterSecondStepContract.Presenter> {
        void onRegisterSuccess();
        void onRegisterFailed(String message);
    }

    interface Presenter extends BasePresenter {
        void register(String email,String password);
    }
}
