package com.devfie.thoth.selectcategories;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devfie.thoth.R;
import com.devfie.thoth.adapter.CategoryAdapter;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.callback.CategoryCallback;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.databinding.FragmentSelectCategoriesBinding;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCategoriesFragment extends BaseFragment implements CategoriesContract.View, CategoryCallback {


    private CategoriesPresenter categoriesPresenter;
    private CategoriesContract.Presenter mPresenter;
    private FragmentSelectCategoriesBinding binding;
    private String title;
    private String description;
    private List<Category> categories;
    private Float ratio;

    public static SelectCategoriesFragment newInstance(String photoPath, String title, String description, Float ratio) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_PHOTO_PATH, photoPath);
        args.putString(Constants.KEY_TITLE, title);
        args.putString(Constants.KEY_DESCRIPTION, description);
        args.putFloat(Constants.KEY_RATIO, ratio);
        SelectCategoriesFragment fragment = new SelectCategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SelectCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        title = args.getString(Constants.KEY_TITLE);
        description = args.getString(Constants.KEY_DESCRIPTION);
        ratio = args.getFloat(Constants.KEY_RATIO);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_categories, container, false);
        categoriesPresenter = new CategoriesPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSelectCategoriesBinding.bind(view);
        mPresenter.getCategories(getContext());
    }

    @Override
    public void setPresenter(CategoriesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void categoryListLoaded(List<Category> categories) {
        this.categories = categories;
        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        binding.categoryRecycler.setAdapter(adapter);
    }

    @Override
    public void categoryListCantLoaded(String error) {
        showToast(error);
    }

    @Override
    public void questionUploaded(final Question question) {
        if (dialog != null)
            dialog.dismiss();

        getActivity().finish();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(question);
            }
        }, 300);

    }

    @Override
    public void questionCantUploaded(String error) {
        if (dialog != null)
            dialog.dismiss();
        showToast(error);
    }

    MaterialDialog dialog;

    @Override
    public void uploadImageProgressChange(long total, long uploaded) {

        if (dialog == null) {
            Long to = total;
            dialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.msg_image_uploading)
                    .content(R.string.msg_please_wait)
                    .progress(false, to.intValue(), true).build();
        }
        dialog.show();
        Long up = uploaded;
        dialog.setProgress(up.intValue());
    }


    @Override
    public void imageUploaded(String url) {
        //dialog.dismiss();
        mPresenter.uploadQuestion(getContext(), url, title, description, categories, ratio);
    }

    @Override
    public void imageUploadFailed() {
        if (dialog != null)
            dialog.dismiss();

    }

    @Override
    public void categoryUnselected() {
        showToast(R.string.error_unselected_category);
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbar();
    }

    private void setToolbar() {
        View view = initToolbar(R.layout.toolbar_select_category);
        ImageButton back = (ImageButton) view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Button upload = (Button) view.findViewById(R.id.btnUpload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.uploadImage(getArguments().getString(Constants.KEY_PHOTO_PATH));
            }
        });

    }

    @Override
    public void onCategorySelectChange(Category category, boolean selected) {
        category.setCheck(selected);
    }
}
