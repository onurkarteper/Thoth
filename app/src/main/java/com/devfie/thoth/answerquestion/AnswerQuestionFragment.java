package com.devfie.thoth.answerquestion;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.databinding.FragmentAnswerQuestionBinding;
import com.devfie.thoth.utils.ImageUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerQuestionFragment extends BaseFragment implements AnswerQuestionContract.View {


    private String questionId;
    private FragmentAnswerQuestionBinding binding;
    private AnswerQuestionPresenter answerQuestionPresenter;
    private AnswerQuestionContract.Presenter presenter;
    private Uri picturePath;

    public AnswerQuestionFragment() {
        // Required empty public constructor
    }

    public static AnswerQuestionFragment newInstance(String questionId) {

        Bundle args = new Bundle();
        args.putString(Constants.KEY_QUESTION_ID, questionId);
        AnswerQuestionFragment fragment = new AnswerQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionId = getArguments().getString(Constants.KEY_QUESTION_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer_question, container, false);
        answerQuestionPresenter = new AnswerQuestionPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAnswerQuestionBinding.bind(view);
        setListener();
    }

    private void setListener() {
        binding.questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkPremissions(getContext());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbar();
    }

    ProgressDialog uploadDialog;

    private void setToolbar() {

        View view = initToolbar(R.layout.toolbar_answer_question);
        view.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.btnSendAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picturePath != null) {
                    uploadDialog = new ProgressDialog(getContext());
                    uploadDialog.setMessage(getString(R.string.answer_loading));
                    uploadDialog.show();
                    presenter.uploadPhoto(picturePath);
                } else if (!binding.edtQuestionDescription.getText().toString().isEmpty()) {
                    uploadDialog = new ProgressDialog(getContext());
                    uploadDialog.setMessage(getString(R.string.answer_loading));
                    uploadDialog.show();
                    presenter.sendAnswer(questionId, "", binding.edtQuestionDescription.getText().toString(), 1.0f, getContext());
                } else showToast(R.string.error_answer_null_check);
            }
        });
    }

    @Override
    public void permissionsChecked() {
        presenter.pickPicture(getContext(), getActivity().getSupportFragmentManager());
    }

    @Override
    public void photoUploaded(String url) {
        Float ratio = Float.valueOf(String.valueOf(binding.imgAnswerPhoto.getHeight())) / Float.valueOf(String.valueOf(binding.imgAnswerPhoto.getWidth()));
        presenter.sendAnswer(questionId, url, binding.edtQuestionDescription.getText().toString(), ratio, getContext());
    }

    @Override
    public void photoCantUploaded(String errorMsg) {
        if (errorMsg != null) showToast(errorMsg);
    }

    @Override
    public void answerSended() {
        uploadDialog.dismiss();
        getActivity().onBackPressed();
    }

    @Override
    public void answerSendFailed(String error) {
        showToast(error);
    }

    @Override
    public void photoSelected(Uri uri) {
        AspectRatio ratio = new AspectRatio("3:2", 3f, 2f);
        AspectRatio ratio1 = new AspectRatio("4:5", 4f, 4.8f);
        AspectRatio ratio2 = new AspectRatio("4:3", 4f, 3f);
        AspectRatio ratio3 = new AspectRatio("1:1", 1f, 1f);
        AspectRatio ratio4 = new AspectRatio("5:3", 5f, 3f);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(getResources().getColor(R.color.dark_text_color));
        options.setToolbarTitle(getString(R.string.edit_photo));
        options.setStatusBarColor(getResources().getColor(R.color.dark_text_color));
        options.setAspectRatioOptions(0, ratio, ratio1, ratio2, ratio3, ratio4);
        options.setFreeStyleCropEnabled(true);

        UCrop.of(uri, ImageUtils.getCropUri(getContext()))
                .withOptions(options)
                .withMaxResultSize(1600, 1600)
                .start(getContext(), this);
    }

    @Override
    public void setPresenter(AnswerQuestionContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            picturePath = resultUri;
            showPhotoPreview();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private void showPhotoPreview() {
        Glide.with(this).load(picturePath.getPath()).into(binding.imgAnswerPhoto);
    }
}
