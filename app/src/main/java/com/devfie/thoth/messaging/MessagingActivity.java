package com.devfie.thoth.messaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseActivity;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.model.User;
import com.google.gson.Gson;

public class MessagingActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        container = R.id.messaging_cont;
        String json = getIntent().getExtras().getString(Constants.KEY_USER_JSON);
        if(json !=null && !json.isEmpty()){
            baseFirstFragment(MessagingFragment.newInstance(json));
        }

    }
}
