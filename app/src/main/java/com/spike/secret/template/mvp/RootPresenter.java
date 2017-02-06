package com.spike.secret.template.mvp;


import java.lang.ref.WeakReference;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Shyam on 2/5/17.
 */

public abstract class RootPresenter implements BasePresenter {
    private WeakReference<BaseView> viewRef;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void attachView(BaseView view) {
        viewRef = new WeakReference<BaseView>(view);
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()} to check if the view
     * is
     * attached to avoid NullPointerExceptions.
     *
     * @return <code>null</code>, if view is not attached, otherwise the concrete view instance
     */
    public BaseView getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * Checks if a view is attached to this presenter. You should always call this method before
     * calling {@link #getView()} to get the view instance.
     */
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    protected final <O> Observable.Transformer<O, O> applySchedulers() {
        return new Observable.Transformer<O, O>() {
            @Override
            public Observable<O> call(Observable<O> o) {
                return o.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Override
    public void onDestroy() {
        compositeSubscription.unsubscribe();
        compositeSubscription = null;
    }
}
