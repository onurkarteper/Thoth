package com.devfie.thoth.manager;

import android.content.Context;
import android.widget.Toast;

import com.devfie.thoth.R;
import com.devfie.thoth.model.response.LoginResponse;
import com.devfie.thoth.model.response.UserInfoResponse;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class LoginManager {

    Context context;
    private static LoginManager instance;
    LocalDataManager localDataManager;
    private static final String TAG = LoginManager.class.getSimpleName();
    public String mobileToken = "";

    public synchronized static void initialize(Context context) {
        if (instance == null) {
            instance = new LoginManager(context);
        }
        //   instance.mobileToken = FirebaseInstanceId.getInstance().getToken();
    }

    public LoginManager(Context context) {
        this.context = context;
        localDataManager = LocalDataManager.getInstance();

    }

    public synchronized static LoginManager getInstance() {
        return instance;
    }


    public boolean isLogin() {
        return localDataManager.isLogin();
    }

    public void updateToken(final TokenUpdateCallback callback) {
        NetworkRequests.with(context).login(localDataManager.getEmail(), localDataManager.getPassword(), new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                LoginResponse loginResponse = (LoginResponse) obj;
                callback.onTokenUpdate(true, loginResponse.getResponse().getToken(), "Succeess");
                localDataManager.saveToken(loginResponse.getResponse().getToken());
                localDataManager.saveId(loginResponse.getResponse().getId());
            }

            @Override
            public void onError(String errorMsg) {
                callback.onTokenUpdate(true, "", errorMsg);

            }

            @Override
            public void onServerError() {
                callback.onTokenUpdate(true, "", context.getString(R.string.error_login));

            }
        });

    }

    public interface TokenUpdateCallback {
        void onTokenUpdate(Boolean success, String token, String message);
    }

    public void updateUserInfo() {
        NetworkRequests.with(context).getMyInfo(localDataManager.getToken(), new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                UserInfoResponse userInfoResponse = (UserInfoResponse) obj;
                localDataManager.saveUserInfo(userInfoResponse.getUser());
                Toast.makeText(context, "Info Update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMsg) {
            //    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServerError() {
       //         Toast.makeText(context, "Server EERror", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
