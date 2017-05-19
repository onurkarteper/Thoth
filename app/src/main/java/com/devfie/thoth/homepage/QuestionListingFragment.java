package com.devfie.thoth.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.devfie.thoth.R;
import com.devfie.thoth.adapter.QuestionAdapter;
import com.devfie.thoth.askquestion.AskQuestionActivity;
import com.devfie.thoth.base.BaseFragment;
import com.devfie.thoth.callback.QuestionCallback;
import com.devfie.thoth.callback.QuestionFindedCallback;
import com.devfie.thoth.callback.QuestionFollowStateChangeListener;
import com.devfie.thoth.data.Constants;
import com.devfie.thoth.databinding.FragmentQuestionListingBinding;
import com.devfie.thoth.events.OnQuestionDeleted;
import com.devfie.thoth.events.OnQuestionFollowStateChange;
import com.devfie.thoth.events.QuestionDetailCantLoad;
import com.devfie.thoth.messaging.MessagingActivity;
import com.devfie.thoth.model.Question;
import com.devfie.thoth.questiondetail.QuestionDetailActivity;
import com.devfie.thoth.report.ReportDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListingFragment extends BaseFragment implements PopupMenu.OnMenuItemClickListener, QuestionListingContract.View, QuestionCallback {


    public QuestionListingFragment() {
        // Required empty public constructor
    }

    QuestionListingPresenter questionListingPresenter;
    QuestionListingContract.Presenter presenter;
    FragmentQuestionListingBinding binding;
    List<Question> questions = new ArrayList<>();
    private int page = 0;

    public static QuestionListingFragment newInstance() {

        Bundle args = new Bundle();

        QuestionListingFragment fragment = new QuestionListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_listing, container, false);
        questionListingPresenter = new QuestionListingPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentQuestionListingBinding.bind(view);
        setToolbar();
        setRecycler();
        listenRecycler();
        setListeners();
        presenter.getQuestionList(getContext(), page);
    }

    private void setListeners() {
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                presenter.getQuestionList(getContext(), page);
            }
        });
        binding.tvAskAquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askQuestion();
            }
        });
    }

    private void setRecycler() {
        QuestionAdapter adapter = new QuestionAdapter(this, questions);
        binding.questionRecycler.setAdapter(adapter);
    }

    private void setToolbar() {
        View view = initToolbar(R.layout.toolbar_home_page);
        final ImageButton more = (ImageButton) view.findViewById(R.id.btnMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), more);
                popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(QuestionListingFragment.this);
                popupMenu.show();
            }
        });
    }

    private void askQuestion() {
        Intent askquestion = new Intent(getContext(), AskQuestionActivity.class);
        startActivity(askquestion);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ask_question:
                askQuestion();
                break;
            case R.id.menu_settings:
                showToast(R.string.settings);
                break;


        }
        return false;
    }

    @Override
    public void setPresenter(QuestionListingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void questionListLoaded(List<Question> questions) {
        if (questions.size() > 0)
            loading = true;
        if (page == 0) this.questions.clear();
        this.questions.addAll(questions);
        binding.questionRecycler.getAdapter().notifyDataSetChanged();
        binding.questionRecycler.setDrawingCacheEnabled(true);
        binding.questionRecycler.setItemViewCacheSize(10);
        binding.swipe.setRefreshing(false);


    }

    @Override
    public void questionListCantLoaded(String error) {
        showToast(error);
        loading = true;
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void onQuestionReported(Question question) {
        int index = questions.indexOf(question);
        questions.remove(index);
        binding.questionRecycler.getAdapter().notifyItemRemoved(index);
        showToast(R.string.msg_report_sended);
    }

    @Override
    public void onQuestionReportFailed(String error) {
        showToast(error);
    }

    @Override
    public void onQuestionDeleted(Question question) {
        int index = questions.indexOf(question);
        questions.remove(index);
        binding.questionRecycler.getAdapter().notifyItemRemoved(index);
        showToast(R.string.msg_question_deleted);

    }

    @Override
    public int takeScreenWidht() {
        return getScreenWidht();
    }


    @Override
    public void onQuestionClick(final Question question) {
        Intent questionDetail = new Intent(getContext(), QuestionDetailActivity.class);
        questionDetail.putExtra(Constants.KEY_QUESTION_JSON, question.toString());
        startActivity(questionDetail);
    }

    @Override
    public void onFollowClick(Question question) {
        question.setLiked(!question.getLiked());
        question.setLikeCount(question.getLiked() ? question.getLikeCount() + 1 : question.getLikeCount() - 1);
        presenter.followQuestion(getContext(), question.getId());
    }

    @Override
    public void onMoreClick(final Question question, View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(localDataManager.isMyId(question.getOwner().getId()) ? R.menu.my_question_popup : R.menu.question_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_report:
                        showReportDialog(question);
                        break;
                    case R.id.menu_answer:
                        onQuestionClick(question);
                        break;

                    case R.id.menu_delete:
                        presenter.deleteMyQuestion(getContext(), question);
                        break;
                    case R.id.menu_send_message:
                        Intent message = new Intent(getActivity(),MessagingActivity.class);
                        message.putExtra(Constants.KEY_USER_JSON, question.getOwner().toString());
                        startActivity(message);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onOwnerClick(Question question) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private void listenRecycler() {
        final LinearLayoutManager manager = (LinearLayoutManager) binding.questionRecycler.getLayoutManager();
        binding.questionRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisiblesItems = manager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            page++;
                            presenter.getQuestionList(getContext(), page);
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });
    }

    private void showReportDialog(final Question question) {
        ReportDialogFragment fragment = new ReportDialogFragment();
        fragment.setCallback(new ReportDialogFragment.SendReportCallback() {
            @Override
            public void sendReportCallback(Boolean send, String desc, Boolean block) {
                if (send)
                    presenter.reportQuestion(getContext(), question, desc, block);
            }
        });
        fragment.show(getActivity().getSupportFragmentManager(), ReportDialogFragment.TAG);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onQuestionAdd(final Question question) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager manager = (LinearLayoutManager) binding.questionRecycler.getLayoutManager();
                manager.scrollToPositionWithOffset(0, 0);
            }
        }, 350);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                questions.add(0, question);
                questionListingPresenter.calculateQuestionDimens(questions);
                binding.questionRecycler.getAdapter().notifyItemInserted(0);
                LinearLayoutManager manager = (LinearLayoutManager) binding.questionRecycler.getLayoutManager();
                manager.scrollToPositionWithOffset(0, 0);
            }
        }, 700);
    }

    @Subscribe
    public void onQuestionFollowStateChange(OnQuestionFollowStateChange event) {
        questionListingPresenter.findQuestionByIdAndSetChecked(questions, event.getQuestionId(), event.getFollow(), event.getFollowCount());
    }

    @Subscribe
    public void onQuestionInfoCantLoad(QuestionDetailCantLoad event) {
        showToast(event.getError());
    }

    @Subscribe
    public void onQuestionDelete(OnQuestionDeleted event) {
        questionListingPresenter.findQuestionById(questions, event.getQuestionId(), new QuestionFindedCallback() {
            @Override
            public void onQuestionFind(Question question) {
                int index = questions.indexOf(question);
                questions.remove(index);
                binding.questionRecycler.getAdapter().notifyItemRemoved(index);
            }
        });
    }


}
