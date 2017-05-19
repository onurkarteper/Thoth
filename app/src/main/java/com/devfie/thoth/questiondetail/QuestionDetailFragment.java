package com.devfie.thoth.questiondetail;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.devfie.thoth.R;
import com.devfie.thoth.adapter.AnswerAdapter;
import com.devfie.thoth.adapter.CategoryAdapter;
import com.devfie.thoth.adapter.CategoryTagAdapter;
import com.devfie.thoth.adapter.QuestionDetailAdapter;
import com.devfie.thoth.answerquestion.AnswerQuestionActivity;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.callback.AnswerCallback;
import com.devfie.thoth.callback.CategoryCallback;
import com.devfie.thoth.callback.QuestionDetailCallback;
import com.devfie.thoth.callback.QuestionFollowStateChangeListener;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.databinding.FragmentQuestionDetailBinding;
import com.devfie.thoth.events.OnQuestionDeleted;
import com.devfie.thoth.events.OnQuestionFollowStateChange;
import com.devfie.thoth.events.QuestionDetailCantLoad;
import com.devfie.thoth.homepage.QuestionListingContract;
import com.devfie.thoth.homepage.QuestionListingFragment;
import com.devfie.thoth.model.Answer;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;
import com.devfie.thoth.report.ReportDialogFragment;
import com.google.gson.Gson;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDetailFragment extends BaseFragment implements QuestionDetailContract.View, QuestionDetailCallback, PopupMenu.OnMenuItemClickListener, AnswerCallback {

    Question question;
    QuestionDetailContract.Presenter presenter;
    QuestionDetailPresenter questionDetailPresenter;
    FragmentQuestionDetailBinding binding;
    QuestionFollowStateChangeListener listener;
    Boolean questionDeleted = false;

    public void setListener(QuestionFollowStateChangeListener listener) {
        this.listener = listener;
    }

    public static QuestionDetailFragment newInstance(String questionJson, QuestionFollowStateChangeListener listener) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_QUESTION_JSON, questionJson);
        QuestionDetailFragment fragment = new QuestionDetailFragment();
        fragment.setArguments(args);
        fragment.setListener(listener);
        return fragment;
    }

    public QuestionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_detail, container, false);
        questionDetailPresenter = new QuestionDetailPresenter(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String json = args.getString(Constants.KEY_QUESTION_JSON);
        question = new Gson().fromJson(json, Question.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentQuestionDetailBinding.bind(view);
        binding.setData(question);
        binding.setCallback(this);
        showAnswers();
    }

    private void showAnswers() {
        calculateAnswerDimens(question.getAnswers());
        QuestionDetailAdapter adapter = new QuestionDetailAdapter(question, this);
        binding.answerRecycler.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        setToolbar();
        presenter.getQuestion(getContext(), question);
    }

    private void setToolbar() {
        View view = initToolbar(R.layout.toolbar_question_detail);
        ImageButton back = (ImageButton) view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    @Override
    public void setPresenter(QuestionDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onProfileClick() {

    }

    @Override
    public void onFollowClick() {
        presenter.followQuestion(getContext(), question.getId());
    }

    @Override
    public void onMoreClick(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(localDataManager.isMyId(question.getOwner().getId()) ? R.menu.my_question_popup_detail : R.menu.question_popup_detail, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(QuestionDetailFragment.this);
        popupMenu.show();
    }

    @Override
    public void onAnswerQuestionClick() {
        Intent answer = new Intent(getContext(), AnswerQuestionActivity.class);
        answer.putExtra(Constants.KEY_QUESTION_ID, question.getId());
        startActivity(answer);
    }

    @Override
    public void onQuestionLiked() {
        question.setLiked(!question.getLiked());
        question.setLikeCount(question.getLiked() ? question.getLikeCount() + 1 : question.getLikeCount() - 1);

    }

    @Override
    public void onQuestionLikeFailed(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void onVoteSended(Answer answer, Boolean type) {
        showToast("Oyunuz kaydedildi");
    }

    @Override
    public void onVoteSendFail(String error) {
        showToast("Oyunuz kaydedilemedi!!");

    }

    @Override
    public void onQuestionUpdated(Question question) {
        binding.setData(question);
        this.question = question;
        showAnswers();
    }

    @Override
    public void onQuestionUpdateFailed(final String error) {
        getActivity().onBackPressed();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new QuestionDetailCantLoad(error));
            }
        }, 20);

    }

    @Override
    public void onAnswerReported(Answer answer) {
        int index = question.getAnswers().indexOf(answer);
        question.getAnswers().remove(index);
        binding.answerRecycler.getAdapter().notifyItemRemoved(index + 1);
    }

    @Override
    public void onAnswerReportFailed(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void onQuestionReported(Boolean block) {
        if (block) {
            getActivity().onBackPressed();
        } else {
            showToast(R.string.msg_report_sended);
        }

    }

    @Override
    public void onQuestionReportFailed(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void onAnswerDeleted(Answer answer) {
        int index = question.getAnswers().indexOf(answer);
        question.getAnswers().remove(index);
        QuestionDetailAdapter adapter = (QuestionDetailAdapter) binding.answerRecycler.getAdapter();
        adapter.removeItem(answer);
        binding.answerRecycler.getAdapter().notifyItemRemoved(index + 1);

    }

    @Override
    public void onAnswerDeleteFailed(String error) {
        showToast(error);
    }

    @Override
    public void onQuestionDeleted() {
        questionDeleted = true;
        getActivity().onBackPressed();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new OnQuestionDeleted(question.getId()));
            }
        }, 30);
    }

    @Override
    public void onQuestionDeleteFailed(String error) {
        showToast(error);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                presenter.deleteQuestion(getContext(), question);
                break;
            case R.id.menu_report:
                reportQuestion();
                break;
        }

        return false;
    }

    private void reportQuestion() {
        ReportDialogFragment fragment = new ReportDialogFragment();
        fragment.setCallback(new ReportDialogFragment.SendReportCallback() {
            @Override
            public void sendReportCallback(Boolean send, String desc, Boolean block) {
                presenter.reportQuestion(getContext(), question, desc, block);
            }
        });
        fragment.show(getActivity().getSupportFragmentManager(), ReportDialogFragment.TAG);

    }

    private void calculateAnswerDimens(List<Answer> answers) {
        int widht = getScreenWidht();
        for (Answer answer : answers) {
            Float f = widht * answer.getRatio();
            answer.setHeight(Math.round(f));
        }
    }

    @Override
    public void onAnswerUpVote(Answer answer) {
        presenter.sendVote(getContext(), answer, true);
    }

    @Override
    public void onAnswerDownVote(Answer answer) {
        presenter.sendVote(getContext(), answer, false);
    }

    @Override
    public void onAnswerMoreClick(View view, final Answer answer) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(localDataManager.isMyId(answer.getOwner().getId()) ? R.menu.my_answer_popup : R.menu.answer_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_delete_answer:
                        presenter.deleteMyAnswer(getContext(), answer);
                        break;

                    case R.id.menu_report:
                        showReportDialog(answer);
                        break;
                    case R.id.send_message:
                        showToast("Soruyu soran kisiye mesaj gonder // TODO");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showReportDialog(final Answer answer) {
        ReportDialogFragment reportDialogFragment = new ReportDialogFragment();
        reportDialogFragment.setCallback(new ReportDialogFragment.SendReportCallback() {
            @Override
            public void sendReportCallback(Boolean send, String desc, Boolean block) {
                if (send) presenter.reportAnswer(getContext(), answer, block, desc);
            }
        });

        reportDialogFragment.show(getActivity().getSupportFragmentManager(), ReportDialogFragment.TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!questionDeleted)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new OnQuestionFollowStateChange(question.getLiked(), question.getId(), question.getLikeCount()));
                }
            }, 50);

    }
}
