package com.devfie.thoth.homepage;

import android.content.Context;
import android.os.AsyncTask;

import com.devfie.thoth.R;
import com.devfie.thoth.callback.QuestionFindedCallback;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.model.Question;
import com.devfie.thoth.model.response.QuestionResponse;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;


/**
 * Created by mac-onur on 8.04.2017.
 */

public class QuestionListingPresenter implements QuestionListingContract.Presenter {

    private final QuestionListingContract.View questionListingView;

    public QuestionListingPresenter(QuestionListingContract.View questionListingView) {
        this.questionListingView = questionListingView;
        questionListingView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getQuestionList(Context context, int page) {
        NetworkRequests.with(context).getQuestions(LocalDataManager.getInstance().getToken(), page, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                QuestionResponse response = (QuestionResponse) obj;
                calculateQuestionDimens(response.getQuestionList());

            }

            @Override
            public void onError(String errorMsg) {
                questionListingView.questionListCantLoaded(errorMsg);
            }

            @Override
            public void onServerError() {
                questionListingView.questionListCantLoaded("error");
            }
        });
    }

    @Override
    public void followQuestion(Context context, String id) {
        NetworkRequests.with(context).followQuestion(LocalDataManager.getInstance().getToken(), id, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {

            }

            @Override
            public void onError(String errorMsg) {

            }

            @Override
            public void onServerError() {

            }
        });
    }

    @Override
    public void reportQuestion(final Context context, final Question question, String cause, Boolean block) {
        NetworkRequests.with(context).reportQuestion(LocalDataManager.getInstance().getToken(), question.getId(), cause, question.getOwner().getId(), block, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                questionListingView.onQuestionReported(question);
            }

            @Override
            public void onError(String errorMsg) {
                questionListingView.onQuestionReportFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                questionListingView.onQuestionReportFailed(context.getString(R.string.error_question_report));

            }
        });
    }

    @Override
    public void deleteMyQuestion(final Context context, final Question question) {
        NetworkRequests.with(context).deleteQuestion(LocalDataManager.getInstance().getToken(), question.getId(), new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                questionListingView.onQuestionDeleted(question);
            }

            @Override
            public void onError(String errorMsg) {
                questionListingView.onQuestionReportFailed(errorMsg);
            }

            @Override
            public void onServerError() {
                questionListingView.onQuestionReportFailed(context.getString(R.string.error_question_delete));

            }
        });
    }

    @SuppressWarnings("unchecked")
    public void calculateQuestionDimens(final List<Question> questionList) {
        final int widht = questionListingView.takeScreenWidht();
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                for (Question question : questionList) {
                    Float f = widht * question.getRatio();
                    question.setHeight(Math.round(f));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                questionListingView.questionListLoaded(questionList);
            }
        }.execute();
    }

    @SuppressWarnings("unchecked")
    public void findQuestionByIdAndSetChecked(final List<Question> questionList, final String id, final Boolean checked, final int followCount) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                for (Question question : questionList) {
                    if (question.getId().equals(id)) {
                        return question;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (o instanceof Question) {
                    Question q = (Question) o;
                    q.setLiked(checked);
                    q.setLikeCount(followCount);

                }
                //questionListingView.questionListLoaded(questionList);
            }
        }.execute();
    }

    @SuppressWarnings("unchecked")
    public void findQuestionById(final List<Question> questionList,final String id ,final QuestionFindedCallback callback) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                for (Question question : questionList) {
                    if (question.getId().equals(id)) {
                        return question;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (o instanceof Question) {
                    Question q = (Question) o;
                    callback.onQuestionFind(q);
                }
                //questionListingView.questionListLoaded(questionList);
            }
        }.execute();
    }
}
