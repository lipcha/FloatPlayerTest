package com.testplayer.rx;

import java.util.concurrent.Executor;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxExecutorImpl  implements RxExecutor {

    private final Executor mThreadExecutor;
    private final Scheduler mShceduler;

    public RxExecutorImpl(Executor threadExecutor, Scheduler scheduler) {
        this.mThreadExecutor = threadExecutor;
        this.mShceduler = scheduler;
    }

    private CompositeDisposable mCompositeDisposable;

    @Override
    public <T> Disposable execute(Observable<T> request, Consumer<T> onSuccessCallback) {
        return execute(request, onSuccessCallback, null, null);
    }

    @Override
    public <T> Disposable execute(Observable<T> request, Consumer<T> onSuccessCallback, Consumer<Throwable> errorCallback, Action onCompleteCallback) {
        final Disposable subscription =
                request
                        .compose(applySchedulers())
                        .subscribe(
                                t -> onExecuteSuccess(t, onSuccessCallback),
                                throwable -> onExecuteError(throwable, errorCallback),
                                () -> onExecuteComplete(onCompleteCallback)
                        );
        addDisposable(subscription);
        return subscription;
    }

    private <T> void  onExecuteSuccess(final T t, final Consumer<T> onSuccess) throws Exception {
        if (onSuccess != null)
            onSuccess.accept(t);
    }

    private void onExecuteError(final Throwable throwable, final Consumer<Throwable> error) throws Exception {
        if (error != null)
            error.accept(throwable);
    }

    private void onExecuteComplete(final Action onComplete) throws Exception {
        if (onComplete != null)
            onComplete.run();
    }

    @Override
    public void onDestroy() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }

    private void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed())
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.remove(disposable);
        mCompositeDisposable.add(disposable);
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mShceduler);
    }
}