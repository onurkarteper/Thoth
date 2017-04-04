package com.devfie.thoth.network;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public interface NetworkResponse <T> {

    public void onSuccess(T obj);
    public void onError(String errorMsg);
    public void onServerError();
}
