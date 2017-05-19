package com.devfie.thoth.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.devfie.thoth.R;
import com.devfie.thoth.callback.CategoryCallback;
import com.devfie.thoth.callback.QuestionCallback;
import com.devfie.thoth.databinding.DataBoundAdapter;
import com.devfie.thoth.databinding.DataBoundViewHolder;
import com.devfie.thoth.databinding.ItemCategorySelectBinding;
import com.devfie.thoth.databinding.ItemQuestionBinding;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;
import com.devfie.thoth.view.SquaredImageView;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 * Kategori listesi icin adapter.
 * Soru yaratirken kategorilerin secilmesi icin kullaniyor.
 */

public class QuestionAdapter extends DataBoundAdapter<ItemQuestionBinding> {

    private List<Question> list;
    private QuestionCallback callback;
    private Integer viewWidht;

    public QuestionAdapter(QuestionCallback callback, List<Question> list) {
        super(R.layout.item_question);
        this.list = list;
        this.callback = callback;
    }


    @Override
    protected void bindItem(DataBoundViewHolder<ItemQuestionBinding> holder, int position, List<Object> payloads) {
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
    public static void photoHeight(ImageView view, Question question) {
        view.getLayoutParams().height = question.getHeight();
    }


}
