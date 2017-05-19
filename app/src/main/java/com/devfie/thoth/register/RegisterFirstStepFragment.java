package com.devfie.thoth.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.databinding.FragmentRegisterFirstStepBinding;
import com.devfie.thoth.login.LoginContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFirstStepFragment extends BaseFragment implements RegisterFirstStepContract.View {


    private RegisterFirstStepContract.Presenter mPresenter;
    RegisterFirstStepPresenter registerFirstStepPresenter;
    private FragmentRegisterFirstStepBinding binding;

    public static RegisterFirstStepFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFirstStepFragment fragment = new RegisterFirstStepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFirstStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_first_step, container, false);
        registerFirstStepPresenter = new RegisterFirstStepPresenter(this, getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegisterFirstStepBinding.bind(view);
        setListener();
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
                    mPresenter.checkEmail(binding.edtEmail.getText().toString());
            }
        });
    }

    private boolean checkUiValues() {
        if (binding.edtEmail.getText().toString().isEmpty()) {
            binding.edtEmail.setError(getString(R.string.error_email_empty));
            return false;
        }
        return true;
    }


    @Override
    public void onEmailCanBeRegister() {
        showNextStep();
    }

    private void showNextStep() {
      baseFragmentTransAction(RegisterUsernameStepFragment.newInstance(binding.edtEmail.getText().toString()));
    }

    @Override
    public void onEmailAlreadyRegistered() {

        binding.edtEmail.setError(getString(R.string.error_email_already_registered));

    }

    @Override
    public void checkEmailConnectionError() {
        binding.edtEmail.setError(getString(R.string.error_email_check));
    }

    @Override
    public void setPresenter(RegisterFirstStepContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
