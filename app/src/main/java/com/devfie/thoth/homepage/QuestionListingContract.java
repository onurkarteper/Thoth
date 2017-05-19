package com.devfie.thoth.homepage;

import android.content.Context;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;
import com.devfie.thoth.model.Question;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 */

public interface QuestionListingContract {

    interface View extends BaseView<Presenter> {
        void questionListLoaded(List<Question> questions);

        void questionListCantLoaded(String error);

        void onQuestionReported(Question question);

        void onQuestionReportFailed(String error);

        void onQuestionDeleted(Question question);

        int takeScreenWidht();
    }

    interface Presenter extends BasePresenter {
        void getQuestionList(Context context, int page);

        void followQuestion(Context context, String id);

        void reportQuestion(Context context, Question question, String cause, Boolean block);

        void deleteMyQuestion(Context context, Question question);

    }
}
