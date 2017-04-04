package com.devfie.thoth.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseActivity;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.databinding.FragmentLoginBinding;
import com.devfie.thoth.main.MainActivity;
import com.devfie.thoth.register.RegisterFirstStepFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private FragmentLoginBinding binding;
    LoginPresenter loginPresenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginPresenter = new LoginPresenter(getContext(), this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        setListeners();
        binding.edtPassword.setText("123456");
        binding.edtEmail.setText("onurkarteper@gmail.com");
    }

    private void setListeners() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUiValues())
                    mPresenter.login(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString());
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterScreen();
            }
        });
    }

    private boolean checkUiValues() {
        if (binding.edtEmail.getText().toString().isEmpty()) {
            binding.edtEmail.setError(getString(R.string.error_email_empty));
            return false;
        } else if (binding.edtPassword.getText().toString().isEmpty()) {
            binding.edtPassword.setError(getString(R.string.error_password_empty));
            return false;
        }
        ((BaseActivity) getActivity()).closeKeyboard();
        return true;
    }

    private void openRegisterScreen() {
        baseFragmentTransAction(RegisterFirstStepFragment.newInstance());
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = (presenter);
    }

    @Override
    public void onLoginFailed(String result) {
        showToast(result);
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
