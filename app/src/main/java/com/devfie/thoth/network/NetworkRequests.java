package com.devfie.thoth.network;

import android.content.Context;

import com.devfie.thoth.data.Constants;
import com.devfie.thoth.data.Md5Helper;
import com.devfie.thoth.model.response.BaseResponse;
import com.devfie.thoth.model.response.LoginResponse;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class NetworkRequests {
    Context mContext;

    public NetworkRequests(Context mContext) {
        this.mContext = mContext;
    }

    public void login(String email, String password, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        networkManager.setTypeToken(LoginResponse.class);
        networkManager.setNetworkMethod(Constants.API_LOGIN);
        networkManager.addJsonBodyParam(Constants.KEY_EMAIL, email);
        networkManager.addJsonBodyParam(Constants.KEY_PASSWORD, Md5Helper.md5(password));
        networkManager.makeRequest(response);
    }

    public void checkEmail(String email, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        networkManager.setTypeToken(BaseResponse.class);
        networkManager.setNetworkMethod(Constants.API_CHECK_EMAIL);
        networkManager.addJsonBodyParam(Constants.KEY_EMAIL, email);
        networkManager.makeRequest(response);
    }

    public void register(String email, String password, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        networkManager.setTypeToken(LoginResponse.class);
        networkManager.setNetworkMethod(Constants.API_REGISTER);
        networkManager.addJsonBodyParam(Constants.KEY_EMAIL, email);
        networkManager.addJsonBodyParam(Constants.KEY_PASSWORD, Md5Helper.md5(password));
        networkManager.makeRequest(response);
    }
}
