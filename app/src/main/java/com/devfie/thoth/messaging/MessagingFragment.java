package com.devfie.thoth.messaging;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfie.thoth.BaseApplication;
import com.devfie.thoth.R;
import com.devfie.thoth.adapter.MessagingAdapter;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.callback.MessagingCallback;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.databinding.FragmentMessagingBinding;
import com.devfie.thoth.model.Message;
import com.devfie.thoth.model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagingFragment extends BaseFragment implements MessagingCallback {

    private static final String TAG = MessagingFragment.class.getSimpleName();

    public static MessagingFragment newInstance(String userJson) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_USER_JSON, userJson);
        MessagingFragment fragment = new MessagingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private User user;
    private FragmentMessagingBinding binding;
    private Socket mSocket;
    private List<Message> messages = new ArrayList<>();
    private MessagingAdapter adapter;

    public MessagingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userJson = getArguments().getString(Constants.KEY_USER_JSON);
        if (userJson != null && !userJson.isEmpty()) {
            try {
                Gson gson = new Gson();
                user = gson.fromJson(userJson, User.class);
            } catch (Exception e) {
                Log.e(TAG, "user json parse error");
                e.printStackTrace();
                getActivity().finish();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messaging, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMessagingBinding.bind(view);
        BaseApplication app = (BaseApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on("new message", onNewMessage);
        setAdapter();
        setListeners();
    }

    private void setAdapter() {
        adapter = new MessagingAdapter(this, messages.toArray());
        LinearLayoutManager manager = (LinearLayoutManager) binding.recyclerMessaging.getLayoutManager();
        manager.setStackFromEnd(true);
        binding.recyclerMessaging.setAdapter(adapter);
    }

    private void setListeners() {
        binding.btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtMsgContent.getText().toString().isEmpty()) return;
                try {
                    sendMessage(binding.edtMsgContent.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMessage(String content) throws JSONException {
        Message newMessage = new Message();
        newMessage.setContent(content);
        newMessage.setTo(user.getId());
        newMessage.setOwner(localDataManager.getUserInfo());
        JSONObject obj = new JSONObject(new Gson().toJson(newMessage));
        mSocket.emit("new message", obj);
        adapter.addItem(newMessage);
        binding.edtMsgContent.getText().clear();
        binding.recyclerMessaging.scrollToPosition(adapter.getItemSize()-1);

    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Message message;
                    try {
                        message = new Gson().fromJson(data.getString("message"), Message.class);
                        adapter.addItem(message);
                        binding.recyclerMessaging.scrollToPosition(adapter.getItemSize()-1);
                    } catch (Exception e) {

                        return;
                    }


                }
            });
        }
    };
}
