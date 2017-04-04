package com.devfie.thoth.network;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.devfie.thoth.R;
import com.devfie.thoth.model.response.BaseResponse;
import com.google.gson.JsonObject;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.koushikdutta.ion.builder.Builders;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class NetworkManager<T> {

    private String baseUrl = "http://192.168.0.29:8080/api/";
    private Context mContext;
    private String method;
    private JsonObject body = new JsonObject();
    private Class<T> tClass;
    private Map<String, Object> params = new HashMap<>();
    private Map<String, String> files = new HashMap<>();
    private ProgressCallback progressCallback;

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    private NetworkManager(Context context) {
        this.mContext = context;
    }

    public static NetworkManager with(Fragment fragment) {
        return new NetworkManager(fragment.getContext());
    }

    public static NetworkManager with(Activity activity) {
        return new NetworkManager(activity);
    }

    public static NetworkManager with(Context context) {
        return new NetworkManager(context);
    }

    public void setNetworkMethod(String method) {
        this.method = method;
    }

    public void setJsonBody(JsonObject jsonObject) {
        this.body = jsonObject;
    }

    public void addJsonBodyParam(String key, String value) {
        params.put(key, value);
    }

    public void setTypeToken(Class<T> tClass) {
        this.tClass = tClass;
    }

    @SuppressWarnings("unchecked")
    public void makeRequest(final NetworkResponse networkResponse) {
        if (ConnectionManager.getInstance().isOnline() && mContext != null) {
            Builders.Any.B ionIns = Ion.with(mContext).load(baseUrl + method);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = (String) entry.getValue();
                ionIns.setBodyParameter(key, value);
            }
            ionIns.setTimeout(10000000);

            ionIns.as(tClass).setCallback(new FutureCallback() {
                @Override
                public void onCompleted(Exception e, Object result) {
                    if (e != null) {
                        networkResponse.onServerError();
                        return;
                    }
                    try {
                        BaseResponse res = (BaseResponse) result;
                        if (res.getError()) {
                            networkResponse.onError(res.getMessage());
                            return;
                        }
                    } catch (Exception castEx) {
                        castEx.printStackTrace();
                        networkResponse.onError("Cast Exception");
                        return;
                    }

                    networkResponse.onSuccess(result);
                }
            });

        } else networkResponse.onError(mContext.getString(R.string.error_network_connection ));
    }

    @SuppressWarnings("unchecked")
    public void makeMultiPartRequest(final NetworkResponse networkResponse) {
        Builders.Any.B ionIns = Ion.with(mContext).load(baseUrl + method);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            ionIns.setMultipartParameter(key, value);
        }
        for (Map.Entry<String, String> entry : files.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ionIns.setMultipartFile(key, new File(value));
        }

        ionIns.setTimeout(10000000);
        if (progressCallback != null)
            ionIns.uploadProgress(progressCallback);
        ionIns.as(tClass)
                .setCallback(new FutureCallback() {
                    @Override
                    public void onCompleted(Exception e, Object result) {
                        if (e != null) {
                            networkResponse.onServerError();
                            return;
                        }
                        try {
                            BaseResponse res = (BaseResponse) result;
                            if (res.getError()) {
                                networkResponse.onError(res.getMessage());
                                return;
                            }
                        } catch (Exception castEx) {
                            castEx.printStackTrace();
                            networkResponse.onError("Cast Exception");
                            return;
                        }

                        networkResponse.onSuccess(result);
                    }


                });
    }


    public void addFilePart(String keyUserAvatar, String photoPath) {
        files.put(keyUserAvatar, photoPath);
    }
}
