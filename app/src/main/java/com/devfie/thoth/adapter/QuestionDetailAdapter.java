package com.devfie.thoth.adapter;

import android.app.Notification;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.devfie.thoth.BR;
import com.devfie.thoth.R;
import com.devfie.thoth.callback.CategoryCallback;
import com.devfie.thoth.callback.QuestionDetailCallback;
import com.devfie.thoth.databinding.DataBoundViewHolder;
import com.devfie.thoth.databinding.MultiTypeDataBoundAdapter;
import com.devfie.thoth.model.Answer;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.List;

/**
 * Created by mac-onur on 15.04.2017.
 */

public class QuestionDetailAdapter extends MultiTypeDataBoundAdapter {

    Question question;
    QuestionDetailCallback callback;

    public QuestionDetailAdapter(Question question, QuestionDetailCallback callback, Object... items) {
        super(items);
        this.question = question;
        this.callback = callback;
        addItems(question);
        addItems(question.getAnswers().toArray());
    }

    @Override
    public int getItemLayoutId(int position) {
        Object item = getItem(position);
        if (item instanceof Question) return R.layout.item_question_detail;
        else if (item instanceof Answer) return R.layout.item_answer;
        else return -1;
    }

    @Override
    protected void bindItem(DataBoundViewHolder holder, int position, List payloads) {
        super.bindItem(holder, position, payloads);
        // this will work even if the layout does not have a callback parameter
        holder.binding.setVariable(BR.callback, callback);
    }

    public void addItems(Object... mItems) {
        for (Object o : mItems) {
            addItem(o);
        }
    }

    public void removeItem(Object object) {
        int index = mItems.indexOf(object);

        mItems.remove(index);

        notifyItemRemoved(index);
    }

    public void clearItems() {
        mItems.clear();
    }


}
