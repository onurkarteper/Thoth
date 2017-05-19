package com.devfie.thoth.basedialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfie.thoth.R;
import com.devfie.thoth.databinding.FragmentBaseDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseDialog extends DialogFragment {


    public BaseDialog() {
        // Required empty public constructor
    }

    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static BaseDialog newInstance(String content) {

        Bundle args = new Bundle();

        BaseDialog fragment = new BaseDialog();
        fragment.setArguments(args);
        fragment.setContent(content);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentBaseDialogBinding binding = FragmentBaseDialogBinding.bind(view);
        binding.content.setText(content);
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
