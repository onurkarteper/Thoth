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
import com.devfie.thoth.BR;
import com.devfie.thoth.R;
import com.devfie.thoth.callback.AnswerCallback;
import com.devfie.thoth.callback.CategoryCallback;
import com.devfie.thoth.callback.MessagingCallback;
import com.devfie.thoth.databinding.DataBoundAdapter;
import com.devfie.thoth.databinding.DataBoundViewHolder;
import com.devfie.thoth.databinding.ItemAnswerBinding;
import com.devfie.thoth.databinding.ItemMessageFromMeBinding;
import com.devfie.thoth.databinding.MultiTypeDataBoundAdapter;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.model.Answer;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Message;
import com.devfie.thoth.model.Question;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 * Kategori listesi icin adapter.
 * Soru yaratirken kategorilerin secilmesi icin kullaniyor.
 */

public class MessagingAdapter extends MultiTypeDataBoundAdapter {


    private MessagingCallback callback;


    public MessagingAdapter(MessagingCallback callback, Object... items) {
        super(items);
        this.callback = callback;
    }

    @Override
    protected void bindItem(DataBoundViewHolder holder, int position, List payloads) {
        super.bindItem(holder, position, payloads);
        // this will work even if the layout does not have a callback parameter
        holder.binding.setVariable(BR.callback, callback);
    }

    @Override
    public int getItemLayoutId(int position) {
        // use layout ids as types
        Object item = getItem(position);
        Message message = (Message) item;
        if (message.getOwner().getId().equals(LocalDataManager.getInstance().getId())) {
            return R.layout.item_message_from_me;
        } else return R.layout.item_message_to_me;
    }


    public void removeItem(Object object) {
        int index = mItems.indexOf(object);

        mItems.remove(index);

        notifyItemRemoved(index);
    }


    public void clearItems() {
        mItems.clear();
    }

    public void addItems(Object... objects) {
        for (Object obj : objects) mItems.add(obj);
    }


    public int getItemSize() {
        return mItems.size();
    }
}
