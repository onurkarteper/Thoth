package com.devfie.thoth.askquestion;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.databinding.FragmentAskQuestionBinding;
import com.devfie.thoth.selectcategories.SelectCategoriesFragment;
import com.devfie.thoth.utils.ImageUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskQuestionFragment extends BaseFragment implements AskQuestionContract.View {

    FragmentAskQuestionBinding binding;
    private AskQuestionContract.Presenter mPresenter;
    AskQuestionPresenter askQuestionPresenter;
    private String picturePath;

    public static AskQuestionFragment newInstance() {

        Bundle args = new Bundle();

        AskQuestionFragment fragment = new AskQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AskQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_question, container, false);
        askQuestionPresenter = new AskQuestionPresenter(this);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAskQuestionBinding.bind(view);
        setListeners();
        showPhotoPreview();
    }

    private void setListeners() {
        binding.questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkPermissions(getContext());
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        setToolbar();
    }

    private void setToolbar() {
        View view = initToolbar(R.layout.toolbar_ask_question);
        ImageButton back = (ImageButton) view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Button next = (Button) view.findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkValues(picturePath);
            }
        });
    }

    @Override
    public void setPresenter(AskQuestionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void pictureSelected(final Uri path) {
        Glide.with(this).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                resource.getWidth();
                cropPicture(path);
            }
        });

    }

    private void cropPicture(Uri source) {
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


        UCrop.of(source, ImageUtils.getCropUri(getContext()))
                .withOptions(options)
                .withMaxResultSize(1600, 1600)
                .start(getContext(), this);
    }

    private void showPhotoPreview() {
        if (picturePath == null) return;
        Glide.with(this).load(picturePath).into(binding.imgQuestionPhoto);
    }

    @Override
    public void questionCanShare() {

    }

    @Override
    public void permissionsCheckedSuccess() {
        mPresenter.pickPicture(getContext(), getActivity().getSupportFragmentManager());
    }

    @Override
    public void permissionsDenied() {

    }

    @Override
    public void nextStep() {
        Float ratio = Float.valueOf(String.valueOf(binding.imgQuestionPhoto.getHeight())) / Float.valueOf(String.valueOf(binding.imgQuestionPhoto.getWidth()));
        baseFragmentTransAction(SelectCategoriesFragment.newInstance(picturePath, binding.edtQuestionTitle.getText().toString(), binding.edtQuestionDescription.getText().toString(), ratio));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            picturePath = resultUri.getPath();
            showPhotoPreview();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }


}
