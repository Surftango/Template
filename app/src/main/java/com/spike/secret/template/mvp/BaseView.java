package com.spike.secret.template.mvp;

import android.support.annotation.NonNull;

/**
 * Base view for MVP
 *
 * Created by Shyam on 2/4/17.
 */

public interface BaseView<T> {

    void setPresenter(@NonNull T presenter);

}
