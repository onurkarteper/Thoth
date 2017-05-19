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
import com.devfie.thoth.databinding.FragmentRegisterUsernameStepBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterUsernameStepFragment extends BaseFragment implements RegisterUsernameStepContract.View {


    public static RegisterUsernameStepFragment newInstance(String email) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_EMAIL, email);
        RegisterUsernameStepFragment fragment = new RegisterUsernameStepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterUsernameStepFragment() {
        // Required empty public constructor
    }

    RegisterUsernameStepPresenter registerPresenter;
    RegisterUsernameStepContract.Presenter presenter;
    FragmentRegisterUsernameStepBinding binding;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_username_step, container, false);
        registerPresenter = new RegisterUsernameStepPresenter(this, getActivity());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        email = args.getString(Constants.KEY_EMAIL);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegisterUsernameStepBinding.bind(view);
        setListener();
    }

    @Override
    public void setPresenter(RegisterUsernameStepContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onUsernameCanBeRegister() {
        baseFragmentTransAction(RegisterSecondStepFragment.newInstance(email, binding.edtUsername.getText().toString()));
    }

    @Override
    public void onUsernameAlreadyRegistered() {
        binding.edtUsername.setError(getString(R.string.error_username_already_registered));
    }

    @Override
    public void checkUsernameConnectionError() {
        binding.edtUsername.setError(getString(R.string.error_username_check));

    }

    private void setListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUiValues())
                    presenter.checkUsername(binding.edtUsername.getText().toString());
            }
        });
    }

    private boolean checkUiValues() {
        if (binding.edtUsername.getText().toString().isEmpty()) {
            binding.edtUsername.setError(getString(R.string.error_username_empty));
            return false;
        }
        return true;
    }
}
