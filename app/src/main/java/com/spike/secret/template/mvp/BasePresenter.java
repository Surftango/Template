package com.spike.secret.template.mvp;

/**
 * Created by Shyam on 2/4/17.
 */

public interface BasePresenter {

    void attachView(BaseView view);

    void detachView();

    void onDestroy();

}
