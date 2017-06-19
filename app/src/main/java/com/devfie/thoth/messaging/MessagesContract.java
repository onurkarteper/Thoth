package com.devfie.thoth.messaging;

import android.content.Context;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;
import com.devfie.thoth.model.Message;

import java.util.List;

/**
 * Created by on 11.06.2017.
 */

public class MessagesContract {

    interface Presenter extends BasePresenter {
        void loadMessages(Context context, List<String> participants,Integer page);
    }

    interface View extends BaseView<Presenter> {
        void onMessagesLoad(List<Message> messages);
    }
}
