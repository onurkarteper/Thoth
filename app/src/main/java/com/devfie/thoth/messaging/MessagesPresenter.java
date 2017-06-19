package com.devfie.thoth.messaging;

import android.content.Context;

import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.model.response.MessageResponse;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;

import java.util.List;

/**
 * Created by on 11.06.2017.
 */

public class MessagesPresenter implements MessagesContract.Presenter {

    private final MessagesContract.View mView;

    public MessagesPresenter(MessagesContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMessages(Context context, List<String> participants,Integer page) {
        NetworkRequests.with(context).getMessasgeList(LocalDataManager.getInstance().getToken(), participants, null,page, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                MessageResponse response = (MessageResponse) obj;
                mView.onMessagesLoad(response.getMessages());
            }

            @Override
            public void onError(String errorMsg) {

            }

            @Override
            public void onServerError() {

            }
        });
    }
}
