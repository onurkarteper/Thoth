package com.devfie.thoth.answerquestion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseActivity;
import com.devfie.thoth.data.Constants;

public class AnswerQuestionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        container = R.id.answerQuestionContainer;
        String questionId = getIntent().getExtras().getString(Constants.KEY_QUESTION_ID);
        baseFirstFragment(AnswerQuestionFragment.newInstance(questionId));

    }
}
