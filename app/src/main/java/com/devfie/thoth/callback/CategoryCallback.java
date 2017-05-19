package com.devfie.thoth.callback;

import com.devfie.thoth.model.Category;

/**
 * Created by mac-onur on 8.04.2017.
 */

public interface CategoryCallback {
    void onCategorySelectChange(Category category, boolean selected);
}
