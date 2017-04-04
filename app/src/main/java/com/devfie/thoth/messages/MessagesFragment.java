package com.devfie.thoth.messages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfie.thoth.R;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {



    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }



}
