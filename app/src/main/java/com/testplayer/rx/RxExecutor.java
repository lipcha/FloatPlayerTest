package com.testplayer.rx;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public interface RxExecutor {

    <T> Disposable execute(Observable<T> request, Consumer<T> onSuccessCallback);

    <T> Disposable execute(Observable<T> request, Consumer<T> onSuccessCallback, Consumer<Throwable> errorCallback, Action onCompleteCallback);

    void onDestroy();
}
