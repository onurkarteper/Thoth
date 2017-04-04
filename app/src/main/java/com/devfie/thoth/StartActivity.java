package com.devfie.thoth;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devfie.thoth.base.BaseActivity;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.databinding.ActivityStartBinding;
import com.devfie.thoth.login.LoginFragment;
import com.devfie.thoth.model.response.LoginResponse;
import com.devfie.thoth.network.NetworkManager;
import com.devfie.thoth.network.NetworkResponse;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStartBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        container = R.id.start_activity_container;
        baseFirstFragment(LoginFragment.newInstance());


    }
}
