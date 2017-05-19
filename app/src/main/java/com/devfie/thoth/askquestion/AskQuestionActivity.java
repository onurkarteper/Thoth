package com.devfie.thoth.askquestion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseActivity;

public class AskQuestionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        container = R.id.askQuestionContainer;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        baseFirstFragment(AskQuestionFragment.newInstance());
    }
}
