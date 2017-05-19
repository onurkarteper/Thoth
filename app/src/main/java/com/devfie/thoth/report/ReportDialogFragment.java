package com.devfie.thoth.report;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfie.thoth.R;
import com.devfie.thoth.databinding.FragmentReportDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportDialogFragment extends DialogFragment {


    public interface SendReportCallback {
        void sendReportCallback(Boolean send, String desc, Boolean block);
    }

    SendReportCallback callback;

    public static final String TAG = ReportDialogFragment.class.getSimpleName();

    public ReportDialogFragment() {
        // Required empty public constructor
    }

    FragmentReportDialogBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentReportDialogBinding.bind(view);
        setListeners();
    }

    private void setListeners() {
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.sendReportCallback(false, "", false);

                dismiss();
            }
        });

        binding.btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.sendReportCallback(true, binding.edtReportDesc.getText().toString(), binding.cbReport.isChecked());
                dismiss();
            }
        });
    }

    public SendReportCallback getCallback() {
        return callback;
    }

    public void setCallback(SendReportCallback callback) {
        this.callback = callback;
    }
}
