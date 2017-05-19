package com.devfie.thoth.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.devfie.thoth.R;
import com.devfie.thoth.callback.AnswerCallback;
import com.devfie.thoth.callback.CategoryCallback;
import com.devfie.thoth.callback.QuestionCallback;
import com.devfie.thoth.databinding.DataBoundAdapter;
import com.devfie.thoth.databinding.DataBoundViewHolder;
import com.devfie.thoth.databinding.ItemAnswerBinding;
import com.devfie.thoth.databinding.ItemQuestionBinding;
import com.devfie.thoth.model.Answer;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 * Kategori listesi icin adapter.
 * Soru yaratirken kategorilerin secilmesi icin kullaniyor.
 */

public class AnswerAdapter extends DataBoundAdapter<ItemAnswerBinding> {

    private List<Answer> list;
    private AnswerCallback callback;
    private Integer viewWidht;

    public AnswerAdapter(AnswerCallback callback, List<Answer> list) {
        super(R.layout.item_answer);
        this.list = list;
        this.callback = callback;
    }


    @Override
    protected void bindItem(DataBoundViewHolder<ItemAnswerBinding> holder, int position, List<Object> payloads) {
        holder.binding.setData(list.get(position));
        holder.binding.setCallback(callback);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @BindingAdapter({"bind:glideAvatar"})
    public static void profileImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(view) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @BindingAdapter({"bind:glide"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);

    }

    @BindingAdapter({"bind:photoHeight"})
    public static void photoHeight(ImageView view, Answer answer) {
        if (answer.getImage().isEmpty()) view.getLayoutParams().height = 0;
        else
            view.getLayoutParams().height = answer.getHeight();
    }
    @BindingAdapter({"bind:category"})
    public static void showCats(RecyclerView view, Question question) {
        CategoryTagAdapter categoryAdapter = new CategoryTagAdapter(new CategoryCallback() {
            @Override
            public void onCategorySelectChange(Category category, boolean selected) {

            }
        }, question.getCategories());
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        view.setLayoutManager(flowLayoutManager.removeItemPerLineLimit().setAlignment(Alignment.LEFT));
        view.setAdapter(categoryAdapter);
    }

}
