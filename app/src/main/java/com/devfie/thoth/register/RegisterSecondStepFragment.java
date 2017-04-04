package com.devfie.thoth.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.databinding.FragmentRegisterFirstStepBinding;
import com.devfie.thoth.databinding.FragmentRegisterSecondStepBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterSecondStepFragment extends BaseFragment implements RegisterSecondStepContract.View {

    private RegisterSecondStepContract.Presenter mPresenter;
    RegisterSecondStepPresenter registerSecondStepPresenter;
    private FragmentRegisterSecondStepBinding binding;
    private String email;

    public RegisterSecondStepFragment() {
        // Required empty public constructor
    }


    public static RegisterSecondStepFragment newInstance(String email) {

        Bundle args = new Bundle();
        args.putString(Constants.KEY_EMAIL, email);
        RegisterSecondStepFragment fragment = new RegisterSecondStepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            email = args.getString(Constants.KEY_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_second_step, container, false);
        registerSecondStepPresenter = new RegisterSecondStepPresenter(this, getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegisterSecondStepBinding.bind(view);
        setListeners();
    }

    private void setListeners() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUiValues())
                    mPresenter.register(email, binding.edtPassword.getText().toString());
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private boolean checkUiValues() {
        if (binding.edtPassword.getText().toString().isEmpty()) {
            binding.edtPassword.setError(getString(R.string.error_password_empty));
            return false;
        }
        return true;
    }

    @Override
    public void setPresenter(RegisterSecondStepContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRegisterSuccess() {
        showToast("Register is success thank you!!");
    }

    @Override
    public void onRegisterFailed(String message) {
        showToast(message);
    }
}
