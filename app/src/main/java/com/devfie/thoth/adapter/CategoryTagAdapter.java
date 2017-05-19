package com.devfie.thoth.adapter;

import com.devfie.thoth.R;
import com.devfie.thoth.callback.CategoryCallback;
import com.devfie.thoth.databinding.DataBoundAdapter;
import com.devfie.thoth.databinding.DataBoundViewHolder;
import com.devfie.thoth.databinding.ItemCategorySelectBinding;
import com.devfie.thoth.databinding.ItemCategoryTagBinding;
import com.devfie.thoth.model.Category;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 * Kategori listesi icin adapter.
 * Soru yaratirken kategorilerin secilmesi icin kullaniyor.
 */

public class CategoryTagAdapter extends DataBoundAdapter<ItemCategoryTagBinding> {

    private List<Category> list;
    private CategoryCallback callback;

    public CategoryTagAdapter(CategoryCallback callback, List<Category> list) {
        super(R.layout.item_category_tag);
        this.list = list;
        this.callback = callback;
    }


    @Override
    protected void bindItem(DataBoundViewHolder<ItemCategoryTagBinding> holder, int position, List<Object> payloads) {
        holder.binding.setData(list.get(position));
        holder.binding.setCallback(callback);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
