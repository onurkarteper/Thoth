package com.devfie.thoth.questiondetail;

import android.content.Context;

import com.devfie.thoth.R;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.model.Answer;
import com.devfie.thoth.model.Question;
import com.devfie.thoth.model.response.AnswerVoteResponse;
import com.devfie.thoth.model.response.QuestionDetailResponse;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;

/**
 * Created by mac-onur on 9.04.2017.
 */

public class QuestionDetailPresenter implements QuestionDetailContract.Presenter {

    private final QuestionDetailContract.View mView;

    public QuestionDetailPresenter(QuestionDetailContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void followQuestion(final Context context, String questionId) {
        NetworkRequests.with(context).followQuestion(LocalDataManager.getInstance().getToken(), questionId, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mView.onQuestionLiked();
            }

            @Override
            public void onError(String errorMsg) {
                mView.onQuestionLikeFailed(errorMsg);

            }

            @Override
            public void onServerError() {
                mView.onQuestionLikeFailed(context.getString(R.string.error_question_follow));

            }
        });
    }

    @Override
    public void sendVote(Context context, final Answer answerId, final Boolean vote) {
        NetworkRequests.with(context).sendVote(LocalDataManager.getInstance().getToken(), answerId.getId(), vote, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                AnswerVoteResponse answerVoteResponse = (AnswerVoteResponse) obj;
                answerId.setMyVote(answerVoteResponse.getAnswer().getMyVote());
                answerId.setVote(answerVoteResponse.getAnswer().getVote());
                mView.onVoteSended(answerId, vote);
            }

            @Override
            public void onError(String errorMsg) {
                mView.onVoteSendFail(errorMsg);

            }

            @Override
            public void onServerError() {
                mView.onVoteSendFail("err");

            }
        });
    }

    @Override
    public void getQuestion(final Context context, final Question question) {
        NetworkRequests.with(context).getQuestion(LocalDataManager.getInstance().getToken(), question.getId(), new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                QuestionDetailResponse res = (QuestionDetailResponse) obj;
                res.getQuestion().setHeight(question.getHeight());
                mView.onQuestionUpdated(res.getQuestion());
            }

            @Override
            public void onError(String errorMsg) {
                mView.onQuestionUpdateFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                mView.onVoteSendFail(context.getString(R.string.error_question_detail_update));

            }
        });
    }

    @Override
    public void deleteMyAnswer(final Context context, final Answer answer) {
        NetworkRequests.with(context).deleteAnswer(LocalDataManager.getInstance().getToken(), answer.getId(), new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mView.onAnswerDeleted(answer);
            }

            @Override
            public void onError(String errorMsg) {
                mView.onAnswerDeleteFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                mView.onAnswerDeleteFailed(context.getString(R.string.error_delete_answer));
            }
        });
    }

    @Override
    public void reportAnswer(final Context context, final Answer answer, Boolean block, String cause) {
        NetworkRequests.with(context).reportAnswer(LocalDataManager.getInstance().getToken(), cause, answer.getId(), answer.getOwner().getId(), block, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mView.onAnswerReported(answer);
            }

            @Override
            public void onError(String errorMsg) {
                mView.onAnswerReportFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                mView.onAnswerReportFailed(context.getString(R.string.error_answer_report));

            }
        });

    }

    @Override
    public void deleteQuestion(final Context context, Question question) {
        NetworkRequests.with(context).deleteQuestion(LocalDataManager.getInstance().getToken(), question.getId(), new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mView.onQuestionDeleted();
            }

            @Override
            public void onError(String errorMsg) {
                mView.onQuestionDeleteFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                mView.onQuestionDeleteFailed(context.getString(R.string.error_question_delete));

            }
        });
    }

    @Override
    public void reportQuestion(final Context context, Question question, String cause, final Boolean block) {
        NetworkRequests.with(context).reportQuestion(LocalDataManager.getInstance().getToken(), question.getId(), cause, question.getOwner().getId(), block, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mView.onQuestionReported(block);
            }

            @Override
            public void onError(String errorMsg) {
                mView.onQuestionReportFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                mView.onQuestionReportFailed(context.getString(R.string.error_question_report));

            }
        });
    }

    @Override
    public void start() {

    }


}
