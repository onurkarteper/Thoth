package com.devfie.thoth.questiondetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseActivity;
import com.devfie.thoth.callback.QuestionFollowStateChangeListener;
import com.devfie.thoth.data.Constants;

public class QuestionDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        container = R.id.questionDetailContainer;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        String json = getIntent().getExtras().getString(Constants.KEY_QUESTION_JSON);
        if (json == null || json.isEmpty()) finish();

        QuestionFollowStateChangeListener listener = (QuestionFollowStateChangeListener) getIntent().getSerializableExtra(Constants.KEY_FOLLOW_STATE_LISTENER);
        baseFirstFragment(QuestionDetailFragment.newInstance(json,listener));
    }
}
