package com.devfie.thoth.base;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}