package com.devfie.thoth.selectcategories;

import android.content.Context;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 */

public interface CategoriesContract {

    interface View extends BaseView<Presenter> {
        void categoryListLoaded(List<Category> categories);

        void categoryListCantLoaded(String error);

        void questionUploaded(Question question);

        void questionCantUploaded(String error);

        void uploadImageProgressChange(long total, long uploaded);

        void imageUploaded(String url);

        void imageUploadFailed();

        void categoryUnselected();

    }

    interface Presenter extends BasePresenter {
        void getCategories(Context context);

        void uploadQuestion(Context context, String imageUrl, String title, String description, List<Category> categories, Float ratio);

        void uploadImage(String path);

    }
}
