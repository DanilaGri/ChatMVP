package od.chat.presenter;

import rx.Subscription;

/**
 * Created by danila on 15.08.16.
 */
public abstract class BasePresenter<T> {

    public T view;
    protected Subscription subscription;

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public boolean isViewAttached() {
        return view != null;
    }

}
