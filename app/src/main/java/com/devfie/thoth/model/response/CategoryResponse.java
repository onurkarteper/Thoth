package com.devfie.thoth.model.response;

import com.devfie.thoth.model.Category;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 */

public class CategoryResponse extends BaseResponse{

    @SerializedName("response")
    @Expose
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
