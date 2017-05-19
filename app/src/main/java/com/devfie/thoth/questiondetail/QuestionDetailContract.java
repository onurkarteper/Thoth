package com.devfie.thoth.questiondetail;

import android.content.Context;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;
import com.devfie.thoth.homepage.QuestionListingContract;
import com.devfie.thoth.model.Answer;
import com.devfie.thoth.model.Question;
import com.devfie.thoth.network.NetworkResponse;

/**
 * Created by mac-onur on 9.04.2017.
 */

public interface QuestionDetailContract {

    public interface View extends BaseView<Presenter> {
        void onQuestionLiked();

        void onQuestionLikeFailed(String errorMsg);

        void onVoteSended(Answer answer, Boolean type);

        void onVoteSendFail(String error);

        void onQuestionUpdated(Question question);

        void onQuestionUpdateFailed(String error);

        void onAnswerReported(Answer answer);

        void onAnswerReportFailed(String errorMessage);

        void onQuestionReported(Boolean block);

        void onQuestionReportFailed(String errorMessage);

        void onAnswerDeleted(Answer answer);

        void onAnswerDeleteFailed(String error);

        void onQuestionDeleted();

        void onQuestionDeleteFailed(String error);
    }

    public interface Presenter extends BasePresenter {
        void followQuestion(Context context, String questionId);

        void sendVote(Context context, Answer answer, Boolean vote);

        void getQuestion(Context context, Question question);

        void deleteMyAnswer(Context context, Answer answer);

        void reportAnswer(Context context, Answer answer, Boolean block, String cause);

        void deleteQuestion(Context context, Question question);

        void reportQuestion(Context context, Question question, String cause, Boolean Block);
    }
}
